org.scalastyle.sbt.ScalastylePlugin.Settings

name := "neodsl"

scalaVersion := "2.11.0-M7"

scalacOptions += "-Ymacro-debug-lite"

resolvers ++= Seq(
  "neo4jrepo" at "http://m2.neo4j.org/releases/",
  "maven repo" at "http://repo1.maven.org/maven2"
)

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11.0-M5" % "2.0.M7" % "test",
  "org.scala-lang" % "scala-reflect" % "2.11.0-M7",
  "org.scala-lang" % "scala-library" % "2.11.0-M7",
  "cglib" % "cglib" % "3.1",
  "org.neo4j" % "neo4j-rest-graphdb" % "2.0.1",
  "com.sun.jersey" % "jersey-core" % "1.9"
)
