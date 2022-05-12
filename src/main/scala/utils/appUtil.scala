package utils

import entity.{Client, JobGroup, Publisher}
import utils.requests.{ClientRequest, JobGroupRequest, PublishserRequest}

import java.util.UUID

object appUtil {

  def clientFromRequest(clientRequest: ClientRequest):Client={

    val clientId=UUID.randomUUID().toString
    val jobGroup=clientRequest.jobGroups.map(jobGroupReq=>jobGroupFromRequest(jobGroupReq))
    Client(clientId,clientRequest.name,clientRequest.inboundFeedUrl,jobGroup)

  }

  def jobGroupFromRequest(jobGroupRequest: JobGroupRequest):JobGroup={

    val jobGroupId=UUID.randomUUID().toString
    JobGroup(jobGroupId,jobGroupRequest.rules,jobGroupRequest.sponsoredPublishers,jobGroupRequest.priority)

  }

  def publishserFromRequest(publishserRequest: PublishserRequest):Publisher={

    val publisherId=UUID.randomUUID().toString
    Publisher(publisherId,publishserRequest.isActive,publishserRequest.clientId,publishserRequest.name)

  }

}
