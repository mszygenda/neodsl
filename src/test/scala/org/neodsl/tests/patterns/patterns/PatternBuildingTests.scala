package org.neodsl.reflection.proxy.tests.dsl.patterns

import org.neodsl.dsl.domain.-->
import org.neodsl.dsl.patterns.{RelationElement, PatternTrippleElement, NodeElement}
import org.neodsl.tests.BaseTests
import org.neodsl.tests.patterns.patterns.PatternDomain._

class PatternBuildingTests extends BaseTests {
  val john = Person("John")
  val friend = Person("JohnsFriend")
  val wiseComment = Comment("Wise Comment")

  "One to one relation of concrete objects" should "be represented with single Relation Pattern element" in {
    val pattern = john knows friend

    pattern should equal(PatternTrippleElement(NodeElement(john), RelationElement[Person, Person]("KNOWS", -->), NodeElement(friend)))
  }

  "Conjunction of two patterns" should "be created" in {
    val firstPattern = john knows friend
    val secondPattern = john likes wiseComment

    val patternConjunction = firstPattern and secondPattern

    List(firstPattern, secondPattern) should equal (patternConjunction.toList.reverse)
  }
}
