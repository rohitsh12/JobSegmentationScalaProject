package utils

import org.json4s.{CustomSerializer, DefaultFormats, Formats, JNothing, JValue}
import org.json4s.JsonAST.{JNull, JString}
import org.json4s.native.Serialization
import org.json4s.native.Serialization.writePretty
import org.json4s.native.Serialization.{writePretty, write => jWrite}
import org.json4s.native.JsonMethods.{parse => jParser}

import java.io.{BufferedWriter, File, FileWriter}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

trait JsonHelper {


  val EMPTY_STRING = ""
  implicit val serialization: Serialization.type = Serialization
  implicit object LocalDateSerializer extends CustomSerializer[LocalDate](format => ({
    case JString(str) => LocalDate.parse(str)
    case JNull => null
  }, {
    case value: LocalDate  => {
      val formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd")
      JString(formatter.format(value))
    }
  }
  ))

  implicit val formats: Formats = DefaultFormats ++ List(LocalDateSerializer)
  def write[T <: AnyRef](value: T): String = jWrite(value)

  protected def parse(value: String): JValue = jParser(value)

  implicit protected def extractOrEmptyString(json: JValue): String = {
    json match {
      case JNothing => EMPTY_STRING
      case data => data.extract[String]
    }
  }

//  def getEntity[T](path: String)(implicit m: Manifest[T]): T = {
//    val entitySrc = scala.io.Source.fromFile(path)
//    val entityStr = try entitySrc.mkString finally entitySrc.close()
//    parse(entityStr).extract[T]
//  }

  def writeJSONToFile[T](entity: T,filePath: String): Unit =
  {
    val bw = new BufferedWriter(new FileWriter(new File(filePath), false))
    bw.write(writePretty(entity))
    bw.close()
  }

}
