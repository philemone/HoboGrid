scalaVersion := "3.3.1"

name := "taminar"
organization := "filemon.taminar"
version := "1.0"
scalacOptions += "-Xcheck-macros"

enablePlugins(ScalaJSPlugin)
scalaJSUseMainModuleInitializer := true

libraryDependencies += "com.raquo" %%% "laminar" % "16.0.0" 
