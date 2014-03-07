package org.neodsl.tests.db.neo4j.cypher

import org.neodsl.tests.BaseTests
import org.neodsl.tests.dsl.PatternDomain.{Person, PersonWithId}
import org.neodsl.dsl.patterns.PatternBuilder._
import scala.Some
import scala.collection.immutable.HashMap
import org.neodsl.queries.components.patterns.{RelationPattern, NodePattern, PatternTripple}
import org.neodsl.queries.domain.{<--, -->, Relation}
import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And}
import org.neodsl.db.neo4j.cypher.MatchSerializer

class MatchSerializerTests extends BaseTests {
  val john = Person("John")
  val friend = anonymous[Person]
  val fof = anonymous[Person]
  val fixedResolver = new FixedNameResolver(HashMap(john -> "john", friend -> "friend", fof -> "fof"))

  "Two pattern conjunction" should "be serialized" in {
    val pattern = PatternTripple(NodePattern(john), RelationPattern(Relation[Person, Person]("KNOWS", -->), Range(1, 1)), NodePattern(friend))
    val sndPattern = PatternTripple(NodePattern(fof), RelationPattern(Relation[Person, Person]("KNOWS", <--), Range(1, 1)), NodePattern(friend))
    val conjunction = And(pattern, And(sndPattern, NoPatterns))
    val expectedMatch = "MATCH john-[:KNOWS]->friend,fof<-[:KNOWS]-friend"

    MatchSerializer.serialize(conjunction, fixedResolver) shouldEqual expectedMatch
  }

  "Single pattern" should "be serialized" in {
    val pattern = PatternTripple(NodePattern(john), RelationPattern(Relation[Person, Person]("KNOWS", -->), Range(1, 1)), NodePattern(friend))
    val singlePattern = And(pattern, NoPatterns)
    val expectedMatch = "MATCH john-[:KNOWS]->friend"

    MatchSerializer.serialize(singlePattern, fixedResolver) shouldEqual expectedMatch
  }
}
