package org.neodsl.tests.dsl

import org.neodsl.tests.BaseTests
import org.neodsl.tests.dsl.PatternDomain._
import org.neodsl.dsl.patterns.PatternBuilder._
import org.neodsl.queries.components.conditions.{ObjectPropertySelector, Eq, SimpleValueSelector, PropertyComparison}
import org.neodsl.queries.components.patterns.compositions.{And, NoPatterns}
import org.neodsl.dsl.queries.QueryBuilder

class ConditionBuildingTests extends BaseTests {
  val john = Person("John")
  val friend = Person("Johns Friend")

  "Query with single condition on property" should "be created" in {
    val friend = some[Person]
    val condition = PropertyComparison(ObjectPropertySelector("friend", "name"), Eq, SimpleValueSelector("Matthew"))

    val pattern = john knows friend
    val queryBuilder = pattern where {
      condition
    }

    queryBuilder should equal (QueryBuilder(And(pattern, NoPatterns), condition))
  }
}
