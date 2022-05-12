package utils.requests

case class ClientRequest(name: String, inboundFeedUrl: String, jobGroups: List[JobGroupRequest])
