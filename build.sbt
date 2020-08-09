import Dependencies._

ThisBuild / scalaVersion := "2.13.3"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "Bank Account Example"
  )
  .aggregate(
    domain,
    application,
    api,
    persistance,
    main
  )

lazy val domain = (project in file("domain"))
  .settings(
    commonSettings
  )

lazy val application = (project in file("application"))
  .settings(
    commonSettings
  )
  .dependsOn(domain)

lazy val api = (project in file("api"))
  .settings(
    commonSettings
  )
  .dependsOn(application)

lazy val persistance = (project in file("persistance"))
  .settings(
    commonSettings
  )
  .dependsOn(application)

lazy val main = (project in file("main"))
  .settings(
    commonSettings
  )
  .dependsOn(application, persistance, api)

lazy val commonSettings = Seq(
  libraryDependencies += scalaTest % Test,
  libraryDependencies += cats,
  libraryDependencies += catsEffect,
  libraryDependencies += fuuid,
  sbt.addCompilerPlugin("org.augustjune" %% "context-applied" % "0.1.3"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xlint",
    "-Xlog-free-terms",
    "-Xlog-free-types",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-unused"
  )
)
