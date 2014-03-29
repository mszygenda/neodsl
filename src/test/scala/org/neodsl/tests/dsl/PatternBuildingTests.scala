package org.neodsl.tests.dsl

import org.neodsl.queries.domain.{Relation, -->}
import org.neodsl.tests.BaseTests
import org.neodsl.queries.components.patterns.{PatternTripple, RelationPattern, NodePattern}
import org.neodsl.tests.dsl.PatternDomain.{Comment, Person}
import org.neodsl.queries.components.patterns.compositions.{And, NoPatterns}

class PatternBuildingTests extends BaseTests {
  val john = Person("John")
  val friend = Person("JohnsFriend")
  val wiseComment = Comment("Wise Comment")

  "One to one relation of concrete objects" should "be represented with single Relation Pattern element" in {
    val pattern = john knows friend

    pattern should equal(PatternTripple(NodePattern(john), RelationPattern[Person, Person](Relation("KNOWS", -->), 1 to 1), NodePattern(friend)))
  }

  "Default number of connections between nodes" should "be one" in {
    val builder = john.likes
    val pattern = builder.pattern

    pattern.relation.connections.length should equal(1)
    pattern.relation.connections should equal (1 to 1)
  }

  "Number of connections between nodes" should "be updated" in {
    val builder = john likes (1 to 2)
    val pattern = builder.pattern

    pattern.relation.connections.length should equal(2)
    pattern.relation.connections should equal (1 to 2)
  }

  "Conjunction of two patterns" should "be created" in {
    val firstPattern = john knows friend
    val secondPattern = john likes wiseComment

    val patternConjunction = firstPattern and secondPattern

    patternConjunction should equal (And(secondPattern, And(firstPattern, NoPatterns)))
  }
}
