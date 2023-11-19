lazy val hobogrid = project
  .in(file("."))
  .settings(
    name                 := "hobogrid",
    organization         := "io.github.philemone",
    scalaVersion         := "3.3.1",
    version              := "0.0.01",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest"          % "3.2.17" % "test",
      "org.scalatest" %% "scalatest-flatspec" % "3.2.17" % "test"
    ),
    scalacOptions ++= Seq(
      "-Xcheck-macros",
      "-Wunused:all"
    ),
    developers           := List(
      Developer(
        id = "Philemone",
        name = "Filip Michalski",
        email = "filemon.michalski@gmail.com",
        url = url("https://github.com/philemone")
      )
    ),
    organization         := "io.github.philemone",
    organizationName     := "Philemone",
    organizationHomepage := Some(url("https://github.com/philemone")),
    homepage             := Some(url("https://github.com/philemone")),
    description          := "Hobogrid - work in progress :)",
    scmInfo              := Some(
      ScmInfo(
        url("https://github.com/philemone/hobogrid"),
        "scm:git@github.com:philemone/hobogrid.git"
      )
    ),
    pomIncludeRepository := { _ => false },
    publishTo            := {
      val nexus = "https://s01.oss.sonatype.org/"
      if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle    := true,
    licenses += ("MIT", new URL("https://github.com/philemone/hobogrid/blob/master/LICENSE")),
    credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials")
  )
