import Dependencies._

ThisBuild / version          := "0.1.0-SNAPSHOT"

lazy val iexcloud4s = project
  .in(file("."))
  .aggregate(iexcloud4sApi, examplesSimpleZio)
  .settings(
    projectSettings,
    crossScalaVersions := Nil,
    skip.in(publish) := true
  )

lazy val iexcloud4sApi = project
  .in(file("iexcloud4s-api"))
  .settings(
    name := "IEXCloud4s",
    libraryDependencies ++= Seq(
      // Http4s
      "org.http4s" %% "http4s-dsl" % http4sVer,
      "org.http4s" %% "http4s-blaze-client" % http4sVer,
      "org.http4s" %% "http4s-circe" % http4sVer,
      // Circe
      "io.circe" %% "circe-core" % circeVer,
      "io.circe" %% "circe-generic" % circeVer,
      "io.circe" %% "circe-parser" % circeVer,
      "io.circe" %% "circe-generic-extras" % circeVer,
      // Test
      scalaTest % Test,
    )
  )

lazy val examplesSimpleZio = project
  .in(file("examples/simple-zio"))
  .settings(
    name := "simple-zio",
    skip in publish := true,
    libraryDependencies ++= Seq(
      // Zio
      "dev.zio" %% "zio" % "1.0.3",
      "dev.zio" %% "zio-interop-cats" % "2.2.0.1",
      "dev.zio" %% "zio-logging" % "0.5.3",
    )
  )
  .dependsOn(iexcloud4sApi)

lazy val projectSettings = Seq(
  organization := "org.meadofpoetry",
  licenses ++= Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
  homepage := Some(url("https://github.com/meadofpoetry/")),
  developers := List(
    Developer("meadofpoetry", "Eugene Bulavin", "eugene.bulavin.se@gmail.com", url("https://github.com/meadofpoetry"))
  ),
  scalaVersion := scala2_13,
  crossScalaVersions := Seq(scala2_12, scalaVersion.value)
)

val scala2_13 = "2.13.3"
val scala2_12 = "2.12.8"

val http4sVer = "1.0.0-M7"
val circeVer = "0.11.2"
