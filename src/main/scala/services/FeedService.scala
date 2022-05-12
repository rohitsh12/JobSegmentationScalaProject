package services

import entity.{Client, Job, JobGroup}
import repository.PublisherRepository
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

trait FeedService {

  //jobToJobGroup very large dimension , reduce the same
  //rule executors bnaa lo
  //jobgroups : listofJobs
def sendingJobToJobGroups(client:Client,jobs:List[Job])={
  // we are making all possible pair of jobs and jobGroups

  val allJobJobGroupPair=jobs.flatMap(job=>client.jobGroups.map(jobGroup=>{(job,jobGroup)}))
  println("these are the all job and jobGroups pair which we have :: "+allJobJobGroupPair)

  val jobToJobGroupAfterRule=allJobJobGroupPair.filter(pair=>followRule(pair._1,pair._2))

    println("job and job group after the applying the rule are --> "+jobToJobGroupAfterRule)

    val JobGroupToJobs = client.jobGroups.map(jobGroup => {
      val listOfJobs = jobToJobGroupAfterRule.filter(pair => pair._2 == jobGroup).map(pair => pair._1)
      (jobGroup, listOfJobs)
    }).toMap

    println(" Job group with its jobs as list  "+JobGroupToJobs)


    JobGroupToJobs


  }

  def followRule(job: Job, jobGroup: JobGroup)={
    jobGroup.rules.forall(rule=>{
      val result=rule.execute(job)
      result
    })
  }

  def publisherToJobs(client: Client,jobGroupToJobs:Map[JobGroup,List[Job]])={

    val listOfPubs = client.jobGroups.flatMap(jobGroup => {
      jobGroup.sponsoredPublishers
    }).toSet.toList

    println("list of publisher is from client: "+listOfPubs)

    PublisherRepository.findPublishersByName(listOfPubs).flatMap(publishers =>{
      val pubToJobGroup = publishers.map(pub => {
        val listOfJobGroups = client.jobGroups.filter(jobGroup => {
          !jobGroup.sponsoredPublishers.find(pubName => pubName == pub.name).isEmpty
        })

        println("(pub,listofJobGrpups) is : (" +pub+ " " +listOfJobGroups+" )")

        (pub, listOfJobGroups)
      })

      println("pubToJobGroups: "+pubToJobGroup)

      Future(pubToJobGroup.map(pair => {
        val listOfJobs = pair._2.flatMap(jg => jobGroupToJobs(jg))

        println("listOfJobs are : "+listOfJobs)

        (pair._1, listOfJobs)
      }))
    })


  }


}
