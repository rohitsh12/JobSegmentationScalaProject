package services

import org.mongodb.scala.{Completed, result}
import repository.ClientRepository
import utils.JsonHelper
import utils.requests.ClientRequest

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ClientService extends JsonHelper with FeedService{

def createClient(clientReq:ClientRequest)={

  val newClient=utils.appUtil.clientFromRequest(clientReq)
  println("here is our data in clientReq"+newClient)

  ClientRepository.findClientByName(newClient.name).flatMap(client=>{
    Option(client) match {
      case Some(value) => throw  new Exception("client with the name already exist")
      case None=> ClientRepository.insertClient(newClient)
    }
  })



}

  def deleteClient(id: String) = {
    ClientRepository.deleteClient(id)
  }

}
