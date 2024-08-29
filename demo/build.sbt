import Dependencies._

ThisBuild / scalaVersion      := "3.5.0"
ThisBuild / version           := "0.1.0-SNAPSHOT"
ThisBuild / organization      := "com.leaguecli"
ThisBuild / organizationName  := "LeagueCli"

lazy val root = project
  .in(file("."))
  .settings(
    name := "League Rank",
    libraryDependencies += "dev.zio" %% "zio"               % "2.1.9",
    libraryDependencies += "dev.zio" %% "zio-cli"           % "0.5.0",
    libraryDependencies += "dev.zio" %% "zio-nio"           % "2.0.2",
    libraryDependencies += "dev.zio" %% "zio-test"          % "2.1.8" % Test,
    libraryDependencies += "dev.zio" %% "zio-test-sbt"      % "2.1.8" % Test,
    libraryDependencies += "dev.zio" %% "zio-test-magnolia" % "2.1.8" % Test,
    libraryDependencies += munit % Test
  )
