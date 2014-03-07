package org.neodsl.db.neo4j.cypher

object CypherKeywords {
  val START = "START"
  val MATCH = "MATCH"
  val RETURN = "RETURN"
  val namedRelOpen = "[:"
  val namedRelClose = "]"
  val relLeftDir = "<-"
  val relRightDir = "->"
  val relNoDir = "-"
  val namedRelLeftOpen = relLeftDir + namedRelOpen
  val namedRelLeftClose = namedRelClose + relNoDir
  val namedRelRightOpen = relNoDir + namedRelOpen
  val namedRelRightClose = namedRelClose + relRightDir
}
