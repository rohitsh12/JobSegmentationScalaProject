package services

import entity.Job
import repository.{ClientRepository, PublisherRepository}
import utils.JsonHelper
import utils.requests.PublishserRequest

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse
import org.json4s.native.Serialization

class PublisherService extends JsonHelper with FeedService {

//  override implicit val serialization=Serialization
//  override implicit val formats=DefaultFormats

  def getMyEntity[T](path: String)(implicit m: Manifest[T]): T = {
    val entitySrc = scala.io.Source.fromFile(path)
    val entityStr = try entitySrc.mkString finally entitySrc.close()
    parse(entityStr).extract[T]
  }

def createPublisher(publisherReq:PublishserRequest)={
  val newPublisher=utils.appUtil.publishserFromRequest(publisherReq)
  println("new publisher is : "+newPublisher)

  PublisherRepository.findPublisherByName(newPublisher.name).flatMap(publisher=>{
    Option(publisher) match {
      case Some(value)=> throw new Exception("publisher with the name already exist")
      case None => PublisherRepository.insertPublisher(newPublisher)
    }
  })

}

  def deletePublisher(id: String) = {
    PublisherRepository.deletePublisher(id)
  }




  def createPublisherFeed(id :String)={
    println("inside the createPublisherFeed function having id -> "+id)
    for{
      clientData <- ClientRepository.findClient(id)
//      jobs =(getEntity[List[Job]](s"${clientData.inboundFeedUrl}"))
      jobs= getMyEntity[List[Job]]("src/main/scala/FeedData/FromClient/myFeed.json")
      jobGrouptoJobs <- Future(sendingJobToJobGroups(clientData.head,jobs))
      pubToJobs <- publisherToJobs(clientData.head,jobGrouptoJobs)
    } yield {
      println("jobs in the list are"+jobs)
      println("publisher to job are -> "+ pubToJobs)
      pubToJobs.foreach(mapping => {
        writeJSONToFile[List[Job]](mapping._2, s"/Users/rohitsharma/Desktop/Joveo/FilesFolder/JobSegmentationProject/src/main/scala/feedData/publisherFeed/${mapping._1.name}_${clientData.head.name}.json")
      })
      true
    }
  }




}
