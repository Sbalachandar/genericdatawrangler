name := "GenericDataWrangler"

description := "SparkGenericDataWrangler"

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

version := "1.0"

scalaVersion := "2.11.8"

// append -deprecation to the options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

val sparkVersion = "2.2.0"


resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion
)

//command line tools
libraryDependencies += "com.typesafe" % "config" % "1.3.1"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.12" % "2.4.16"


//command line arg parser
libraryDependencies += "com.github.scopt" %% "scopt" % "3.6.0"

//date time
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.16.0"

// for scala unit testing.
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"

// for json
val jacksonVersion = "2.8.5"
libraryDependencies ++=
  Seq("com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion
    , "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion
    , "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % jacksonVersion
  )

//http client
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.2"

//email
libraryDependencies += "org.apache.commons" % "commons-email" % "1.4"

//logging
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.25"
//json
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"


parallelExecution in Test := false
autoScalaLibrary := false

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "Releases" at "http://nexus-dev/content/repositories/releases"
resolvers += "Snapshots" at "http://nexus-dev/content/repositories/snapshots"
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

resolvers += Resolver.mavenLocal
