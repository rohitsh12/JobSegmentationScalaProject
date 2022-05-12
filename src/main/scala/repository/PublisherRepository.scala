package repository

import db.DbConfig
import entity.{Client, Publisher}
import org.mongodb.scala.model.Filters.{equal, in}

object PublisherRepository {

  val publisherTable=DbConfig.publishers

  def insertPublisher(pub: Publisher) = {
    publisherTable.insertOne(pub).toFuture()
  }

  def deletePublisher(publisherId: String) = {
   publisherTable.deleteOne(equal("id", publisherId)).toFuture()
  }

  def findPublishersByName(publisherNames: List[String]) = {
    publisherTable.find(in("name", publisherNames:_*)).toFuture()
  }

  def findPublisherByName(publisherName: String) = {
    publisherTable.find(equal("name", publisherName)).first().toFuture()
  }

}
