package org.neodsl.tests.dsl

import org.neodsl.tests.BaseTests
import org.neodsl.tests.dsl.PatternDomain._
import org.neodsl.dsl.patterns.PatternBuilder._
import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And}
import org.neodsl.queries.components.conditions.NoConditions

class SelectQueriesTests extends BaseTests {
  val john = Person("John")
  "Select query" should "be created" in {
    val friend = p[Person]
    val select = john knows friend select friend

    select.patterns shouldEqual (And(john knows friend, NoPatterns))
    select.condition shouldEqual NoConditions
    select.nodes shouldEqual List(friend)
  }
}
