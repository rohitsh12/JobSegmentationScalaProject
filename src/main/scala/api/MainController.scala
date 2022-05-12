package api

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.server
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives.{as, complete, concat, delete, entity, get, onComplete, parameter, path, pathPrefix, post, put,Segment}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
//import akka.http.scaladsl.server.PathMatchers._
//import akka.http.scaladsl.server.directives.{PathDirectives, RouteDirectives}
//import akka.pattern.Patterns
//import akka.stream.ActorMaterializer
//import akka.stream.scaladsl.Source
//import akka.util.ByteString
import services.{ClientService, PublisherService}
import utils.requests.{ClientRequest, PublishserRequest}
import utils.{JsonHelper, JsonUtil, TimeUtil}
import scala.concurrent.ExecutionContext
import akka.stream.Materializer
import scala.concurrent.Await
import scala.util.{Failure, Success}

class MainController(publisherService: PublisherService,clientService: ClientService)(implicit ec: ExecutionContext, mat: Materializer) extends JsonHelper {

   val appRoutes = concat{
     pathPrefix("myTask"){
    path("home") {
      get {
        println("inside the home path")
        complete("welcome to the Job Segmentation project")
      }
    } ~
      pathPrefix("generate"){
        pathPrefix("feeds"){
          path(Segment){id=>
            put{
              onComplete(publisherService.createPublisherFeed(id)){
                _ match{
                  case Success(result) => {
                    println(s"Result of creation of the publisher feeds: \n$result")
                    complete(StatusCodes.OK, s"Created the publisher feeds for the client with id: $id")
                  }
                  case Failure(exception) => {
                    println(s"Error while creating the publisher feeds for the client: \n$exception")
                    complete(StatusCodes.InternalServerError, exception.getMessage)
                  }
                }
              }
            }
          }
        }
      } ~
     pathPrefix("create"){
      path("client"){
        post{
          println("inside the create client function in main controller")
          entity(as[String]) { clientJson =>
            val clientRequest = parse(clientJson).extract[ClientRequest]
            onComplete(clientService.createClient(clientRequest)){
              _ match{
                case Success(result) => {
                  println(s"Result of creation of the client: \n$result")
                  complete(StatusCodes.OK, "Created the client")
                }
                case Failure(exception) => {
                  println(s"Error while creating the client: \n$exception")
                  complete(StatusCodes.InternalServerError, exception.getMessage)
                }
              }
            }
          }
        }
      } ~
        path("publisher"){
          post{
            println("inside the create publisher method in main controller")
            entity(as[String]) { publisherJson =>
              val publisherReq = parse(publisherJson).extract[PublishserRequest]
              println("my publisher Request is "+publisherReq)
              onComplete(publisherService.createPublisher(publisherReq)){
                _ match{
                  case Success(result) => {
                    println(s"Result of creation of the publisher: \n$result")
                    complete(StatusCodes.OK, "Created the publisher")
                  }
                  case Failure(exception) => {
                    println(s"Error while creating the publisher: \n$exception")
                    complete(StatusCodes.InternalServerError, exception.getMessage)
                  }
                }
              }
            }
          }
        }
    } ~
    pathPrefix("delete"){
      pathPrefix("client"){
        path(Segment){ id =>
          delete{
            onComplete(clientService.deleteClient(id)){
              _ match{
                case Success(res) => {
                  println(s"deletion of the client: \n$res")
                  complete(StatusCodes.OK, s"Deleted the client with id: $id")
                }
                case Failure(exception) => {
                  println(s"Error while deleting the client: \n$exception")
                  complete(StatusCodes.InternalServerError, exception.getMessage)
                }
              }
            }
          }
        }
      } ~
        pathPrefix("publisher"){
          path(Segment){ id =>
            delete{
              onComplete(publisherService.deletePublisher(id)){
                _ match{
                  case Success(result) => {
                    println(s"Result of deletion of the publisher: \n$result")
                    complete(StatusCodes.OK, s"Deleted the publisher with id: $id")
                  }
                  case Failure(exception) => {
                    println(s"Error while deleting the publisher: \n$exception")
                    complete(StatusCodes.InternalServerError, exception.getMessage)
                  }
                }
              }
            }
          }
        }
    }



    }
   }


}
