import org.neodsl.tests.BaseTests
import org.neodsl.db.neo4j.cypher.WhereSerializer
import org.neodsl.tests.db.neo4j.cypher.FixedNameResolver
import scala.collection.immutable.HashMap
import org.neodsl.queries.components.conditions._
import org.neodsl.queries.components.conditions.SimpleValueSelector
import org.neodsl.queries.components.conditions.PropertyComparison
import org.neodsl.db.neo4j.cypher.exceptions.UnsupportedSimpleValueTypeException

class SomeVal(val someVal: Int) extends AnyVal

class WhereSerializerTests extends BaseTests {
  val fixedNameResolver = new FixedNameResolver(HashMap())

  "WhereSerializer#serialize" should "return empty string for NoConditions" in {
    WhereSerializer.serialize(NoConditions, fixedNameResolver) shouldEqual ""
  }

  "WhereSerializer#serialize for int eq comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "age"), Eq, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE obj.age = 20"
  }

  "WhereSerializer#serialize for int gt comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "age"), Gt, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE obj.age > 20"
  }

  "WhereSerializer#serialize for int lt comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "age"), Lt, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE obj.age < 20"
  }

  "WhereSerializer#serialize for int ge comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "age"), Ge, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE obj.age >= 20"
  }

  "WhereSerializer#serialize for int le comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "age"), Le, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE obj.age <= 20"
  }

  "WhereSerializer#serialize for int neq comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "age"), Neq, SimpleValueSelector(20))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE obj.age <> 20"
  }

  "WhereSerialize#serialize for string comparison" should "wrap string value with apostrophes" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "name"), Eq, SimpleValueSelector("Matthew"))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE obj.name = 'Matthew'"
  }

  "WhereSerializer#serialize for two objects comparison" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "age"), Eq, ObjectPropertySelector("objTwo", "age"))

    WhereSerializer.serialize(cond, fixedNameResolver) shouldEqual "WHERE obj.age = objTwo.age"
  }

  "WhereSerializer#serialize for conjunction" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "age"), Eq, SimpleValueSelector(20))
    val cond2 = PropertyComparison(ObjectPropertySelector("obj", "name"), Eq, SimpleValueSelector("Matthew"))

    WhereSerializer.serialize(And(cond, cond2), fixedNameResolver) shouldEqual "WHERE (obj.age = 20 AND obj.name = 'Matthew')"
  }

  "WhereSerializer#serialize for alternative" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "age"), Eq, SimpleValueSelector(20))
    val cond2 = PropertyComparison(ObjectPropertySelector("obj", "name"), Eq, SimpleValueSelector("Matthew"))

    WhereSerializer.serialize(Or(cond, cond2), fixedNameResolver) shouldEqual "WHERE (obj.age = 20 OR obj.name = 'Matthew')"
  }

  "WhereSerializer#serialize for not" should "return valid cypher where condition" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "age"), Eq, SimpleValueSelector(20))

    WhereSerializer.serialize(Not(cond), fixedNameResolver) shouldEqual "WHERE NOT(obj.age = 20)"
  }

  "WhereSerializer#serialize" should "throw exception for non simple value types" in {
    val cond = PropertyComparison(ObjectPropertySelector("obj", "age"), Eq, SimpleValueSelector(new SomeVal(1)))

    intercept[UnsupportedSimpleValueTypeException] {
      WhereSerializer.serialize(cond, fixedNameResolver)
    }
  }
}
