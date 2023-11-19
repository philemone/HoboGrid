ThisBuild / version      := "0.0.1.001-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.1"
ThisBuild / organization := "io.github.philemone"
ThisBuild / scalacOptions ++= Seq(
  "-Xcheck-macros",
  "-Wunused:all"
)

lazy val hobogridJS = project
  .in(file("js"))
  .enablePlugins(ScalaJSPlugin)
  .settings(developerSettings)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    name                            := "hobogridJS",
    libraryDependencies ++= Seq(
      "com.raquo"     %%% "laminar"            % "16.0.0",
      "org.scalatest" %%% "scalatest"          % "3.2.17" % "test",
      "org.scalatest" %%% "scalatest-flatspec" % "3.2.17" % "test"
    ),
    pomIncludeRepository            := { _ => false },
    publishMavenStyle               := true,
    licenses += ("MIT", new URL("https://github.com/philemone/hobogrid/blob/master/LICENSE")),
    credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials"),
    publishTo                       := {
      val nexus = "https://s01.oss.sonatype.org/"
      if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    scmInfo                         := Some(
      ScmInfo(
        url("https://github.com/philemone/hobogrid"),
        "scm:git@github.com:philemone/hobogrid.git"
      )
    )
  )
  .dependsOn(hobogrid)

lazy val hobogrid = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(developerSettings)
  .settings(
    publish / skip := true,
    name           := "hobogrid",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest"          % "3.2.17" % "test",
      "org.scalatest" %% "scalatest-flatspec" % "3.2.17" % "test"
    )
  )

val developerSettings = Seq(
  organization         := "io.github.philemone",
  organizationName     := "Philemone",
  organizationHomepage := Some(url("https://github.com/philemone")),
  homepage             := Some(url("https://github.com/philemone")),
  developers           := List(
    Developer(
      id = "Philemone",
      name = "Filip Michalski",
      email = "filemon.michalski@gmail.com",
      url = url("https://github.com/philemone")
    )
  )
)
