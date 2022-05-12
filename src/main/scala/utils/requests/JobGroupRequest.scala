package utils.requests

import entity.Rule

case class JobGroupRequest(rules: List[Rule], sponsoredPublishers: List[String],priority : Int)
