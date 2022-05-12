ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "JobSegmentationProject"
  )


libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.4.2",
  "com.softwaremill.sttp" %% "core" % "1.7.2",

  "com.typesafe.akka" %% "akka-stream" % "2.6.14",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.14",

  "com.typesafe.akka" %% "akka-slf4j" % "2.5.26",
//  "com.typesafe.akka" %% "akka-actor" % "2.5.26",
  "com.typesafe.akka" %% "akka-http" % "10.2.4",
//  "com.typesafe.akka" %% "akka-stream" % "2.5.26",
  "ch.rasc" % "bsoncodec" % "1.0.1",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.7.0",
//  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.4",
  "com.google.code.gson" % "gson" % "2.9.0",

"org.json4s" %% "json4s-jackson" % "3.7.0-M7",
"org.json4s" %% "json4s-native" % "3.7.0-M7",
  "com.lihaoyi" %% "upickle" % "0.9.5",
  "com.lihaoyi" %% "os-lib" % "0.7.1"


)