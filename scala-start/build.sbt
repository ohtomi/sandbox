name := "scala-start"
organization := "example"
scalaVersion := "2.12.4"
version      := "0.1.0-SNAPSHOT"
libraryDependencies ++= Seq(
    "org.json4s" %% "json4s-jackson" % "3.5.3",
    "org.scala-sbt" %% "io" % "1.1.0",
    "org.scalatest" %% "scalatest" % "3.0.5" % Test)
