package utils

import scala.util.Try
import org.json4s.{Formats,DefaultFormats}

trait DataTypeConvertor {

  implicit val formats: Formats = DefaultFormats

  def convertToInt( s: Any ) = Try(s.toString.toIntOption).get
//  def tryToDouble(s: Any) = Try(s.toString.toDoubleOption).get
//  def tryToList(s: Any) = Try(JsonMethods.parse(s.toString).extract[List[Any]]).toOption
  def convertToString(s: Any) = Try(s.toString).toOption

}
