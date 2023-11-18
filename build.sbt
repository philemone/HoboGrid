scalaVersion := "3.3.1"

name := "taminar"
organization := "filemon.taminar"
version := "1.0"
scalacOptions += "-Xcheck-macros"

//enablePlugins(ScalaJSPlugin)
//scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.17" % "test",
  "org.scalatest" %% "scalatest-flatspec" % "3.2.17" % "test",
// "com.raquo" %%% "laminar" % "16.0.0"
)