package db


import ch.rasc.bsoncodec.time.LocalDateTimeDateCodec
import entity.{Client, Job, JobGroup, Publisher, Rule}
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.codecs.Macros._

object DbConfig {

  private val javaCodecs = CodecRegistries.fromCodecs(new LocalDateTimeDateCodec())
  private val customCodecs = fromProviders(classOf[Client], classOf[Job], classOf[JobGroup], classOf[Publisher], classOf[Rule])
  private val codecRegistry = fromRegistries(customCodecs, javaCodecs, DEFAULT_CODEC_REGISTRY)

  val database1 : MongoDatabase = MongoClient().getDatabase("JobSegment").withCodecRegistry(codecRegistry)
  val database2 : MongoDatabase = MongoClient().getDatabase("JobSegment").withCodecRegistry(codecRegistry)

  database1.createCollection("Clients")
  database2.createCollection("Publishers")

  val clients : MongoCollection[Client] = database1.getCollection("Clients")
  val publishers : MongoCollection[Publisher] = database2.getCollection("Publishers")

}
