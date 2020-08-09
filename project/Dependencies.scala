import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest"  %% "scalatest"   % "3.1.1"
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.1.4"
  lazy val cats = "org.typelevel"       %% "cats-core"   % "2.0.0"
}
