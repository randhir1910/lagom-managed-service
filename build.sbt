
name := "external-service"

version := "0.1"

scalaVersion := "2.12.4"

organizationName in ThisBuild := "edu.knoldus"

lagomServiceLocatorPort in ThisBuild := 2002

lagomServiceGatewayPort in ThisBuild := 2004

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `hello-lagom` = (project in file("."))
    .aggregate(`hello-lagom-api`, `hello-lagom-impl`)

lazy val `hello-lagom-api` = (project in file("hello-lagom-api"))
    .settings(
      libraryDependencies ++= Seq(
        lagomScaladslApi
      )
    )

lazy val `hello-lagom-impl` = (project in file("hello-lagom-impl"))
    .enablePlugins(LagomScala)
    .settings(
      libraryDependencies ++= Seq(
        lagomScaladslPersistenceCassandra,
        lagomScaladslKafkaBroker,
        lagomScaladslTestKit,
        macwire,
        scalaTest,
        "com.knoldus" %% "user-lagom-api" % "1.0-SNAPSHOT"
      )
    )
    .settings(lagomForkedTestSettings: _*)
    .dependsOn(`hello-lagom-api`)

lazy val hello = lagomExternalScaladslProject("user-lagom", "com.knoldus" %% "user-lagom-impl" % "1.0-SNAPSHOT")
