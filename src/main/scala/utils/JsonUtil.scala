package utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat, RootJsonFormat}

import java.time.LocalDate
import java.time.format.DateTimeFormatter

trait JsonUtil extends SprayJsonSupport with DefaultJsonProtocol{

  implicit object dateFormatter extends JsonFormat[LocalDate] {
    override def write(obj: LocalDate): JsValue = {
      JsString(obj.toString)
    }

    override def read(json: JsValue): LocalDate = {
      LocalDate.parse(json.toString(), DateTimeFormatter.ISO_DATE)
    }
  }

//  implicit val userJsonFormatter: RootJsonFormat[Users] = DefaultJsonProtocol.jsonFormat4(Users)



}
