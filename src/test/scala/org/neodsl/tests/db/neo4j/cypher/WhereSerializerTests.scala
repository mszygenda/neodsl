import org.neodsl.tests.BaseTests
import org.neodsl.db.neo4j.cypher.WhereSerializer
import org.neodsl.tests.db.neo4j.cypher.FixedNameResolver
import org.neodsl.tests.dsl.PatternDomain.Person
import scala.collection.immutable.HashMap
import org.neodsl.queries.components.conditions._
import org.neodsl.queries.components.conditions.SimpleValueSelector
import org.neodsl.queries.components.conditions.PropertyComparison
import org.neodsl.db.neo4j.cypher.exceptions.UnsupportedSimpleValueTypeException

class SomeVal(val someVal: Int) extends AnyVal

class WhereSerializerTests extends BaseTests {
  val john = Person("John")
  val friend = Person("Friend")
  
  val fixedNameResolver = new FixedNameResolver(HashMap(john -> "john", friend -> "friend"))

  "WhereSerializer#serialize" should "return empty string for NoConditions" in {
    WhereSerializer.serialize(NoConditions, fixedNameResolver) shouldEqual ""
  }

  "WhereSerializer#serialize for int eq comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "age"), Eq, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE john.age = 20"
  }

  "WhereSerializer#serialize for int gt comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "age"), Gt, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE john.age > 20"
  }

  "WhereSerializer#serialize for int lt comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "age"), Lt, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE john.age < 20"
  }

  "WhereSerializer#serialize for int ge comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "age"), Ge, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE john.age >= 20"
  }

  "WhereSerializer#serialize for int le comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "age"), Le, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE john.age <= 20"
  }

  "WhereSerializer#serialize for int neq comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "age"), Neq, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE john.age <> 20"
  }

  "WhereSerialize#serialize for string comparison" should "wrap string value with apostrophes" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "name"), Eq, SimpleValueSelector("John"))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE john.name = 'John'"
  }

  "WhereSerializer#serialize for two objects comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "age"), Eq, ObjectPropertySelector(friend, "age"))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE john.age = friend.age"
  }

  "WhereSerializer#serialize for conjunction" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "age"), Eq, SimpleValueSelector(20))
    val cond2 = PropertyComparison(ObjectPropertySelector(john, "name"), Eq, SimpleValueSelector("John"))

    WhereSerializer.serialize(And(cond, cond2), fixedNameResolver) shouldEqual "WHERE (john.age = 20 AND john.name = 'John')"
  }

  "WhereSerializer#serialize for alternative" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "age"), Eq, SimpleValueSelector(20))
    val cond2 = PropertyComparison(ObjectPropertySelector(john, "name"), Eq, SimpleValueSelector("John"))

    WhereSerializer.serialize(Or(cond, cond2), fixedNameResolver) shouldEqual "WHERE (john.age = 20 OR john.name = 'John')"
  }

  "WhereSerializer#serialize for not" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "age"), Eq, SimpleValueSelector(20))

    WhereSerializer.serialize(Not(cond), fixedNameResolver) shouldEqual "WHERE NOT(john.age = 20)"
  }

  "WhereSerializer#serialize" should "throw exception for non simple value types" in {
    val cond = PropertyComparison(ObjectPropertySelector(john, "age"), Eq, SimpleValueSelector(new SomeVal(1)))

    intercept[UnsupportedSimpleValueTypeException] {
      WhereSerializer.serialize(cond, fixedNameResolver)
    }
  }
}
