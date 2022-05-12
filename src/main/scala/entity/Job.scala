package entity

import java.lang.annotation.Documented

@Documented
case class Job(jobId: String, category: String, title: String, minExperience: Int, tags: List[String], valid: Boolean){
  def getJobMember(member:String):Option[Any]={
  val res= member match {
    case "title" => title
    case "category" =>category
    case "minExperience"=> minExperience
    case "jobId" => jobId
    case _ => null
  }

    Option(res)
  }
}
