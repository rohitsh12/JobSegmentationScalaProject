package entity

case class Client(id: String, name: String, inboundFeedUrl: String, jobGroups: List[JobGroup])
