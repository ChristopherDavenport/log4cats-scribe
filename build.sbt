import com.typesafe.tools.mima.core._

val Scala213 = "2.13.12"
val Scala212 = "2.12.18"
val Scala3 = "3.3.1"

ThisBuild / tlBaseVersion := "0.4"
ThisBuild / crossScalaVersions := Seq(Scala213, Scala212, Scala3)
ThisBuild / scalaVersion := Scala213
ThisBuild / developers := List(
  Developer(
    "christopherdavenport",
    "Christopher Davenport",
    "chris@christopherdavenport.tech",
    new java.net.URL("https://christopherdavenport.github.io/")
  )
)

lazy val `log4cats-scribe` = project.in(file("."))
  .settings(commonSettings)
  .disablePlugins(MimaPlugin)
  .aggregate(scribe.jvm, scribe.js, scribe.native, docs)

lazy val scribe = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("log4cats-scribe"))
  .settings(commonSettings)
  .settings(
    name := "log4cats-scribe"
  )

lazy val docs = project
  .in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
  .dependsOn(scribe.jvm)

val log4catsV = "2.6.0"
val scribeV = "3.12.2"

// General Settings
lazy val commonSettings = Seq(
  organization := "io.chrisdavenport",

  scalaVersion := "2.13.12",
  crossScalaVersions := Seq(scalaVersion.value, "3.3.1", "2.12.18"),
  scalacOptions += "-Yrangepos",

  Compile / doc / scalacOptions ++= Seq(
      "-groups",
      "-sourcepath", (LocalRootProject / baseDirectory).value.getAbsolutePath,
      "-doc-source-url", "https://github.com/ChristopherDavenport/log4cats-scribe/blob/v" + version.value + "€{FILE_PATH}.scala"
  ),

  libraryDependencies ++= Seq(
    "com.outr"                    %%% "scribe"                     % scribeV,

    "org.typelevel"               %%% "log4cats-core"              % log4catsV,
  )
)
