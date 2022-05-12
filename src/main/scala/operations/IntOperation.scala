package operations

class IntOperation(field:Int,value:Int,op:String) extends AllOperation {

  override def evaluate(): Boolean = {
    op match {
      case "greaterThan" => greaterThan()
      case "lesserThan" => lesserThan()
      case "equal" => isEqual()
      case _ => false
    }
  }

  def greaterThan(): Boolean = {
    field > value
  }

  def lesserThan(): Boolean = {
    field < value
  }

  def isEqual():Boolean={
    value==field
  }

}
