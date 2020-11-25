import Dependencies._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val http4sVer = "1.0.0-M7"
val circeVer = "0.13.0"

libraryDependencies ++= Seq(
  // Zio
  "dev.zio" %% "zio" % "1.0.3",
  "dev.zio" %% "zio-interop-cats" % "2.2.0.1",
  "dev.zio" %% "zio-logging" % "0.5.3",
  // Http4s
  "org.http4s" %% "http4s-dsl" % http4sVer,
  "org.http4s" %% "http4s-dsl" % http4sVer,
  "org.http4s" %% "http4s-blaze-client" % http4sVer,
  "org.http4s" %% "http4s-circe" % http4sVer,
  // Circe
  "io.circe" %% "circe-core" % circeVer,
  "io.circe" %% "circe-generic" % circeVer,
  "io.circe" %% "circe-parser" % circeVer,

)

lazy val root = (project in file("."))
  .settings(
    name := "IEXCloudAPI",
    libraryDependencies += scalaTest % Test
  )
