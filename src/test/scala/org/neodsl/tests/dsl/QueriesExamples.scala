package org.neodsl.tests.dsl

import org.neodsl.tests.BaseTests
import org.neodsl.tests.dsl.PatternDomain._
import org.neodsl.dsl.patterns.PatternBuilder._
import org.neodsl.macros.Macros._
import org.neodsl.queries.components.conditions.{SimpleValueSelector, Eq, ObjectPropertySelector, PropertyComparison}

class QueriesExamples extends BaseTests {
  val john = Person("John")

  def selectOneNode = {
    val (friend, comment) = (p[Person], p[Comment])

    { john knows friend } and
    { friend likes comment } where {
      friend.name == "Matthew"
    } select(friend, comment, john)
  }
}
