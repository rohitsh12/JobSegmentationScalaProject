import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{concat, pathPrefix}
import akka.stream.ActorMaterializer

import scala.util.{Failure, Success}

//import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import api.MainController

object WebServer extends App {

  println("this is our server for job segmentation project:")

  implicit val system = ActorSystem("job-segmentation-app")
  implicit val dispatcher = system.dispatcher

  val controller=new MainController()

  val apiRoutes= {
    pathPrefix("api") {

      concat(
        controller.appRoutes
      )


    }
  }

  Http().bindAndHandle(controller.appRoutes,"localhost",8080).onComplete {
    case Success(value) =>
      println("Server started")
      system.log.info(s"Server Stated on 0.0.0.0:8080, $value")

    case Failure(exception) =>
      println(exception.getMessage)
      system.log.error(s"Can't Start the Server, ${exception.getMessage}")
  }



  println("Server started  for Job Segmentation Project...")

//    .flatMap(_.unbind())
//    .onComplete(_ => system.terminate())


}
