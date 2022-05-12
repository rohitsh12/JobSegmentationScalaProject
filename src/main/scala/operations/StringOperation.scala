package operations

class StringOperation(val field: String, val value: String, val op: String) extends AllOperation {

  override def evaluate(): Boolean = {
    op match {
      case "startsWith" => startsWith()
      case "equals" => isEqual()
      case _ => false
    }
  }

  def startsWith(): Boolean = {
    field.startsWith(value)
  }

  def isEqual(): Boolean = {
    field == value
  }

}
