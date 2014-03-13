package org.neodsl.tests.macros

import org.neodsl.tests.BaseTests
import org.neodsl.macros.Macros
import Macros._
import org.neodsl.queries.components.conditions._
import org.neodsl.tests.dsl.PatternDomain.{AgingPerson, Person}
import org.neodsl.dsl.patterns.PatternBuilder._

class ConditionBuilderMacrosTests extends BaseTests {
  val john = new AgingPerson("John", 10)

  "Boolean expression john.name == 'john'" should "be transformed into PropertyComparison object" in {
    val cond = boolExprToCondition(john.name == "john")

    cond shouldEqual (PropertyComparison(ObjectPropertySelector(john, "name"), Eq, SimpleValueSelector("john")))
  }

  "Boolean expression john.name > 'john'" should "be transformed into PropertyComparison object" in {
    val cond = boolExprToCondition(john.name > "john")

    cond shouldEqual (PropertyComparison(ObjectPropertySelector(john, "name"), Gt, SimpleValueSelector("john")))
  }

  "Boolean expression john.name >= 'john'" should "be transformed into PropertyComparison object" in {
    val cond = boolExprToCondition(john.name >= "john")

    cond shouldEqual (PropertyComparison(ObjectPropertySelector(john, "name"), Ge, SimpleValueSelector("john")))
  }

  "Boolean expression john.name < 'john'" should "be transformed into PropertyComparison object" in {
    val cond = boolExprToCondition(john.name < "john")

    cond shouldEqual (PropertyComparison(ObjectPropertySelector(john, "name"), Lt, SimpleValueSelector("john")))
  }


  "Boolean expression john.name <= 'john'" should "be transformed into PropertyComparison object" in {
    val cond = boolExprToCondition(john.name <= "john")

    cond shouldEqual (PropertyComparison(ObjectPropertySelector(john, "name"), Le, SimpleValueSelector("john")))
  }

  "Boolean expression john.name != 'john'" should "be transformed into PropertyComparison object" in {
    val cond = boolExprToCondition(john.name != "john")

    cond shouldEqual (PropertyComparison(ObjectPropertySelector(john, "name"), Neq, SimpleValueSelector("john")))
  }

  "Boolean expression !(john.name == 'john')" should "be transformed into PropertyComparison object" in {
    val cond = boolExprToCondition(!(john.name == "john"))

    cond shouldEqual (Not(PropertyComparison(ObjectPropertySelector(john, "name"), Eq, SimpleValueSelector("john"))))
  }

  "Boolean expression (john.name == 'john') && (john.age > 10)" should "be transformed into And(PropertyComparison, PropertyComparison) object" in {
    val cond = boolExprToCondition((john.name != "john") && (john.age > 10))

    val fstPropComparison = PropertyComparison(ObjectPropertySelector(john, "name"), Neq, SimpleValueSelector("john"))
    val sndPropComparison = PropertyComparison(ObjectPropertySelector(john, "age"), Gt, SimpleValueSelector(10))

    cond shouldEqual (And(fstPropComparison, sndPropComparison))
  }

  "Boolean expression (john.name == 'john') || (john.age > 10)" should "be transformed into And(PropertyComparison, PropertyComparison) object" in {
    val cond = boolExprToCondition((john.name != "john") || (john.age > 10))

    val fstPropComparison = PropertyComparison(ObjectPropertySelector(john, "name"), Neq, SimpleValueSelector("john"))
    val sndPropComparison = PropertyComparison(ObjectPropertySelector(john, "age"), Gt, SimpleValueSelector(10))

    cond shouldEqual (Or(fstPropComparison, sndPropComparison))
  }

  "Boolean expression john.age < 5" should "be transformed into PropertyComparison, PropertyComparison object" in {
    val cond = boolExprToCondition(john.age < 5)

    cond shouldEqual PropertyComparison(ObjectPropertySelector(john, "age"), Lt, SimpleValueSelector(5))
  }

  "Boolean expression ((john.name == 'john') || (john.age > 10)) && !(john.age != 10)" should "be transformed into And(Or(PropertyComparison, PropertyComparison), Not(PropertyComparison)) object" in {
    val cond = boolExprToCondition(((john.name == "john") || (john.age > 10)) && !(john.age != 10))

    val fstPropComparison = PropertyComparison(ObjectPropertySelector(john, "name"), Eq, SimpleValueSelector("john"))
    val sndPropComparison = PropertyComparison(ObjectPropertySelector(john, "age"), Gt, SimpleValueSelector(10))
    val thrdPropComparison = PropertyComparison(ObjectPropertySelector(john, "age"), Neq, SimpleValueSelector(10))

    cond shouldEqual And(Or(fstPropComparison, sndPropComparison), Not(thrdPropComparison))
  }
}
