package org.neodsl.tests.db.neo4j.cypher

import org.neodsl.tests.BaseTests
import org.neodsl.queries.components.patterns.{RelationPattern, NodePattern, PatternTriple}
import org.neodsl.tests.dsl.PatternDomain.Person
import org.neodsl.queries.domain.{<--, -->, Relation}
import org.neodsl.db.neo4j.cypher.PatternSerializer
import scala.collection.immutable.HashMap

class PatternSerializationTests extends BaseTests {
  val john = Person("John")
  val friend = Person("Friend")
  val knows = Relation[Person, Person]("KNOWS", -->)
  val isKnownBy = Relation[Person, Person]("KNOWS", <--)
  val nameResolver = new FixedNameResolver(HashMap(john -> "john", friend -> "friend"))

  "1 to 1 relation directed right" should "be serialized to Cypher" in {
    val tripple = PatternTriple(NodePattern(john), RelationPattern(knows, Range(1, 1)), NodePattern(friend))
    val cypher = PatternSerializer.serialize(tripple, nameResolver)

    cypher shouldEqual("john-[:KNOWS]->friend")
  }

  "Variable connections relation directed right" should "be serialized to Cypher" in {
    val tripple = PatternTriple(NodePattern(john), RelationPattern(knows, Range(1, 2)), NodePattern(friend))
    val cypher = PatternSerializer.serialize(tripple, nameResolver)

    cypher shouldEqual("john-[:KNOWS*1..2]->friend")
  }

  "1 to 1 relation with directed left" should "be serialized to Cypher" in {
    val tripple = PatternTriple(NodePattern(john), RelationPattern(isKnownBy, Range(1, 1)), NodePattern(friend))
    val cypher = PatternSerializer.serialize(tripple, nameResolver)

    cypher shouldEqual("john<-[:KNOWS]-friend")
  }

  "Variable connections relation directed left" should "be serialized to Cypher" in {
    val tripple = PatternTriple(NodePattern(john), RelationPattern(isKnownBy, Range(2, 5)), NodePattern(friend))
    val cypher = PatternSerializer.serialize(tripple, nameResolver)

    cypher shouldEqual("john<-[:KNOWS*2..5]-friend")
  }
}
