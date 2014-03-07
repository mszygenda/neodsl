package org.neodsl.tests.conditions

import org.neodsl.tests.BaseTests
import org.neodsl.queries.components.conditions._
import org.neodsl.tests.dsl.PatternDomain.Person

class ConditionsTests extends BaseTests {
  val john = Person("John")
  val friend = Person("Friend")

  "Property equality to simple value" should "be created" in {
    PropertyComparison(ObjectPropertySelector(john, "name"), Eq, SimpleValueSelector("John"))
  }

  "Property equality to another node property" should "be created" in {
    PropertyComparison(ObjectPropertySelector(john, "name"), Eq, ObjectPropertySelector(friend, "name"))
  }
}
