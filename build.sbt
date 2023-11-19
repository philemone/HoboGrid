import Versions._

ThisBuild / version      := "0.0.1.001-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.1"
ThisBuild / organization := "io.github.philemone"
ThisBuild / scalacOptions ++= Seq(
  "-Xcheck-macros",
  "-Wunused:all"
)

val githubPage = "https://github.com/philemone"

lazy val hobogrid = project
  .in(file("js"))
  .enablePlugins(ScalaJSPlugin)
  .settings(developerSettings)
  .settings(
    name                 := "hobogrid",
    libraryDependencies ++= Seq(
      "com.raquo"     %%% "laminar"            % laminarV,
      "org.scalatest" %%% "scalatest"          % scalatestV % "test",
      "org.scalatest" %%% "scalatest-flatspec" % scalatestV % "test"
    ),
    pomIncludeRepository := { _ => false },
    publishMavenStyle    := true,
    licenses += ("MIT", new URL("https://github.com/philemone/hobogrid/blob/master/LICENSE")),
    credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials"),
    publishTo            := {
      val nexus = "https://s01.oss.sonatype.org/"
      if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    scmInfo              := Some(
      ScmInfo(
        url("https://github.com/philemone/hobogrid"),
        "scm:git@github.com:philemone/hobogrid.git"
      )
    )
  )
  .dependsOn(hoboshape)

lazy val hoboshape = project
  .in(file("hoboshape"))
  .enablePlugins(ScalaJSPlugin)
  .settings(developerSettings)
  .settings(
    publish / skip := true,
    name           := "hoboshape",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest"          % scalatestV % "test",
      "org.scalatest" %% "scalatest-flatspec" % scalatestV % "test"
    )
  )

lazy val example = project
  .in(file("example"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    name           := "example",
    publish / skip := true,
    libraryDependencies ++= Seq(
      "com.raquo" %%% "laminar" % laminarV
    )
  ).dependsOn(hobogrid)

val developerSettings = Seq(
  organization         := "io.github.philemone",
  organizationName     := "Philemone",
  organizationHomepage := Some(url(githubPage)),
  homepage             := Some(url(githubPage)),
  developers           := List(
    Developer(
      id = "Philemone",
      name = "Filip Michalski",
      email = "filemon.michalski@gmail.com",
      url = url(githubPage)
    )
  )
)
