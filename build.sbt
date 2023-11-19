scalaVersion := "3.3.1"

name := "taminar"
organization := "io.github.philemone"
version := "0.0.1-SNAPSHOT"
scalacOptions ++= Seq(
"-Xcheck-macros",
"-Wunused:all"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.17" % "test",
  "org.scalatest" %% "scalatest-flatspec" % "3.2.17" % "test",
)