package entity

import operations.{IntOperation, StringOperation}
import utils.DataTypeConvertor

case class Rule(fieldName:String, conditionValue:String, operation:String,valType:String) extends DataTypeConvertor {
  def execute(job:Job):Boolean={

    val fieldValue=job.getJobMember(fieldName)
    if(fieldValue.isEmpty)
      return false

      valType match {
        case "String" => {
          val myField=convertToString(fieldValue.get)
          if(myField.isEmpty)return false   // try to avoid the return keyword in the middle of the code in scala
          new StringOperation(myField.get,conditionValue,operation).evaluate()


        }

        case "Int" => {
          val myField=convertToInt(fieldValue.get)
          val myConditionValue=convertToInt(conditionValue)
          if(myField.isEmpty || myConditionValue.isEmpty)return false
          new IntOperation(myField.get,myConditionValue.get,operation).evaluate()
        }

        case _ =>{
          println("this type is not supported kindly provide only Int and String type")
          false
        }

      }



  }




}
