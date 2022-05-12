package entity

case class JobGroup(id: String, rules: List[Rule], sponsoredPublishers: List[String],priority : Int)
