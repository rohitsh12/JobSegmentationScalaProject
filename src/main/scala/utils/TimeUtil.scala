package utils

import scala.concurrent.duration.{DurationInt, FiniteDuration}

object TimeUtil {


  val atMostDuration: FiniteDuration = 2.seconds
  val timeoutMills: Long = 2 * 1000

}
