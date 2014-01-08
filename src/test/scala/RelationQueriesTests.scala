package org.neodsl.query.test

import org.scalatest._

class RelationQueriesTests extends FlatSpec with Matchers {
  /*"Pattern with right direction" should "generate valid match statement" in {
    val pattern = new Pattern(NamedNode("person"), Relation("KNOWS", -->), NamedNode("another"))

    pattern.toString should be ("person-[:KNOWS]->another")
  }

  "Pattern with left direction" should "generate valid match statement" in {
    val pattern = new Pattern(NamedNode("person"), Relation("KNOWS", <--), NamedNode("another"))

    pattern.toString should be ("person<-[:KNOWS]-another")
  }

  "Pattern without direction" should "generate valid match statement" in {
    val pattern = new Pattern(NamedNode("person"), Relation("KNOWS", --), NamedNode("another"))

    pattern.toString should be ("person-another")
  }

  "Pattern with mixed directions" should "generate valid match statement" in {
    val pattern = new Pattern(NamedNode("person"), Relation("KNOWS", -->), NamedNode("another"), Relation("WRITTEN_BY", <--), NamedNode("comment"))

    pattern.toString should be ("person-[:KNOWS]->another<-[:WRITTEN_BY]-comment")
  }

  "Pattern with anonymous node" should "generate valid match statement" in {
    val pattern = new Pattern(NamedNode("person"), Relation("KNOWS", -->), AnonymousNode, Relation("KNOWS", -->), NamedNode("fof"))

    pattern.toString should be ("person-[:KNOWS]->()-[:KNOWS]->fof")
  }*/
}
