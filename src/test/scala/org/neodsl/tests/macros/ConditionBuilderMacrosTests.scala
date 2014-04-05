package org.neodsl.tests.macros

import org.neodsl.tests.BaseTests
import org.neodsl.macros.Macros
import Macros._
import org.neodsl.queries.components.conditions._
import org.neodsl.tests.dsl.PatternDomain.{PersonAcceptingRules, AgingPerson, Person}
import org.neodsl.dsl.patterns.PatternBuilder._

class ConditionBuilderMacrosTests extends BaseTests {
  val john = new AgingPerson("John", 10)
  val user = new PersonAcceptingRules("User", true)

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

  "Boolean expression john.age < 5" should "be transformed into PropertyComparison object" in {
    val cond = boolExprToCondition(john.age < 5)

    cond shouldEqual PropertyComparison(ObjectPropertySelector(john, "age"), Lt, SimpleValueSelector(5))
  }

  "Boolean expression user.acceptsRules" should "be transformed into PropertyComparison(ObjectPropertySelector, SimpleValueSelector(true)) object" in {
    val cond = boolExprToCondition(user.acceptsRules)

    cond shouldEqual PropertyComparison(ObjectPropertySelector(user, "acceptsRules"), Eq, SimpleValueSelector(true))
  }

  "Boolean expression !user.acceptsRules" should "be transformed into Not(PropertyComparison(ObjectPropertySelector, SimpleValueSelector(true))) object" in {
    val cond = boolExprToCondition(!user.acceptsRules)

    cond shouldEqual Not(PropertyComparison(ObjectPropertySelector(user, "acceptsRules"), Eq, SimpleValueSelector(true)))
  }

  "Boolean expression ((john.name == 'john') || (john.age > 10)) && !(john.age != 10)" should "be transformed into And(Or(PropertyComparison, PropertyComparison), Not(PropertyComparison)) object" in {
    val cond = boolExprToCondition(((john.name == "john") || (john.age > 10)) && !(john.age != 10))

    val fstPropComparison = PropertyComparison(ObjectPropertySelector(john, "name"), Eq, SimpleValueSelector("john"))
    val sndPropComparison = PropertyComparison(ObjectPropertySelector(john, "age"), Gt, SimpleValueSelector(10))
    val thrdPropComparison = PropertyComparison(ObjectPropertySelector(john, "age"), Neq, SimpleValueSelector(10))

    cond shouldEqual And(Or(fstPropComparison, sndPropComparison), Not(thrdPropComparison))
  }

  "Property comparison with simple function parameter john.name == nameParam" should "be transformed to PropertyComparison(ObjectSelector, Eq, SimpleValueSelector)" in {
    def someFun(nameParam: String): Condition = {
      john.name == nameParam
    }

    someFun("john") shouldEqual PropertyComparison(ObjectPropertySelector(john, "name"), Eq, SimpleValueSelector("john"))
  }

  "Property comparison with variable john.name == nameVar" should "be transformed to PropertyComparison(ObjectSelector, Eq, SimpleValueSelector)" in {
    var nameVar = "john"
    val cond: Condition = john.name == nameVar

    cond shouldEqual PropertyComparison(ObjectPropertySelector(john, "name"), Eq, SimpleValueSelector("john"))
  }

  "Property comparison with other object property which is not descendant of Node john.name == notNodeObj.name" should "be transformed to PropertyComparison(ObjectSelector, Eq, SimpleValueSelector)" in {
    class NotNode {
      val name: String = "john"
    }

    val notNodeObj = new NotNode()
    val cond: Condition = john.name == notNodeObj.name

    cond shouldEqual PropertyComparison(ObjectPropertySelector(john, "name"), Eq, SimpleValueSelector("john"))
  }
}
