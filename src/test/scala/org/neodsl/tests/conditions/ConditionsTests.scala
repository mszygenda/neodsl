package org.neodsl.tests.conditions

import org.neodsl.tests.BaseTests
import org.neodsl.queries.components.conditions._

class ConditionsTests extends BaseTests {
  "Property equality to simple value" should "be created" in {
    PropertyComparison(ObjectPropertySelector("person", "name"), Eq, SimpleValueSelector("John"))
  }

  "Property equality to another node property" should "be created" in {
    PropertyComparison(ObjectPropertySelector("person", "name"), Eq, ObjectPropertySelector("friend", "name"))
  }
}
