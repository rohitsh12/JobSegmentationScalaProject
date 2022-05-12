package repository

import db.DbConfig
import entity.Client
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.result

import scala.concurrent.Future

object ClientRepository {

  val clientTable=DbConfig.clients

  def insertClient(newClient:Client)={

 clientTable.insertOne(newClient).toFuture()

  }

  def deleteClient(clientId: String) = {
    clientTable.deleteOne(equal("id", clientId)).toFuture()
  }

  def findClient(clientId: String) = {
    clientTable.find(equal("id", clientId)).toFuture()
  }

  def findClientByName(clientName: String) = {
    clientTable.find(equal("name", clientName)).first().toFuture()
  }



}
