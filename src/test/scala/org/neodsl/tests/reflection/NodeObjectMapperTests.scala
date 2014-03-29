package org.neodsl.tests.reflection

import org.neodsl.reflection.NodeObjectMapper
import org.neodsl.tests.BaseTests
import org.neodsl.dsl.domain.DomainObject
import scala.collection.immutable.HashMap

case class CaseClassWithOneProp(val name: String)

case class CaseClassWithMultipleCtors(name: String, age: Int) {
  def this(name: String) = {
    this(name, 0)
  }
}

class ClassWithNoSuperType {
  val prop1: String = "sth"
  val prop2: Int = 10
  val prop3: Boolean = true
}

class ClassWithSuperType extends ClassWithNoSuperType {
  val prop4: String = "sth2"
}

class ClassWithSimpleAndComplexProperties {
  val boolProp: Boolean = true
  val intProp: Int = 10
  val stringProp: String = "abc"
  val shortProp: Short = 10
  val longProp: Long = 10
  val floatProp: Float = 1.0f
  val doubleProp: Double = 1.0
  val charProp: Char = 'a'
  val complexProp: ClassWithSuperType = new ClassWithSuperType
}

class ClassWhichInheritsByDomainObject extends DomainObject[ClassWhichInheritsByDomainObject] {
  val prop1: String = "test"
}

class ClassWithOptionalFields extends DomainObject[ClassWithOptionalFields] {
  val optionalLong: Option[Long] = None
  val optionalString: Option[String] = None
  val optionalInt: Option[Int] = None
  val optionalShort: Option[Short] = None
  val optionalChar: Option[Char] = None
  val optionalBoolean: Option[Boolean] = None
  val optionalFloat: Option[Float] = None
  val optionalDouble: Option[Double] = None
}

class NodeObjectMapperTests extends BaseTests {
  val mapper = new NodeObjectMapper()

  "NodeObjectMapper#getProperties[CaseClassWithOneProp(name)]" should "return single property 'name'" in {
    mapper.getPropertyNames[CaseClassWithOneProp] shouldEqual List("name")
  }

  "NodeObjectMapper#getProperties[CaseClassWithMultipleCtors]" should "return all properties name, age" in {
    mapper.getPropertyNames[CaseClassWithMultipleCtors] shouldEqual List("age", "name")
  }

  "NodeObjectMapper#getProperties[ClassWithNoSuperType]" should "return all properties: prop1, prop2, prop3" in {
    mapper.getPropertyNames[ClassWithNoSuperType] shouldEqual List("prop1", "prop2", "prop3")
  }

  "NodeObjectMapper#getProperties[ClassWithSuperType]" should "own properties and superclass properties: prop1, prop2, prop3, prop4" in {
    mapper.getPropertyNames[ClassWithSuperType] shouldEqual List("prop1", "prop2", "prop3", "prop4")
  }

  "NodeObjectMapper#getProperties[ClassWithSimpleAndComplexProperties]" should "return only simple properties: stringProp, intProp, shortProp, doubleProp, floatProp, boolProp, longProp, charProp" in {
    mapper.getPropertyNames[ClassWithSimpleAndComplexProperties] shouldEqual List("boolProp", "charProp", "doubleProp", "floatProp", "intProp", "longProp", "shortProp", "stringProp")
  }

  "NodeObjectMapper#getProperties[ClassWhichInheritsByDomainObject]" should "not return any properties defined on Node, DomainObject, ProxyableObject EXCEPT for id field" in {
    mapper.getPropertyNames[ClassWhichInheritsByDomainObject] shouldEqual List("id", "prop1")
  }

  "NodeObjectMapper#mapToObject[ClassWithSuperType]" should "create instance of the type with properties altered despite they were vals" in {
    val defaultInstanceObj = new ClassWithSuperType
    val obj = mapper.mapToObject[ClassWithSuperType](
      HashMap[String, Any](
        "prop1" -> (defaultInstanceObj.prop1 + "CHANGED"),
        "prop2" -> (defaultInstanceObj.prop2 + 10),
        "prop3" -> !defaultInstanceObj.prop3,
        "prop4" -> (defaultInstanceObj.prop4 + "CHANGED")
      )
    )

    obj.prop1 shouldEqual (defaultInstanceObj.prop1 + "CHANGED")
    obj.prop2 shouldEqual (defaultInstanceObj.prop2 + 10)
    obj.prop3 shouldEqual (!defaultInstanceObj.prop3)
    obj.prop4 shouldEqual (defaultInstanceObj.prop4 + "CHANGED")
  }

  "NodeObjectMapper#mapToObject[ClassWithOptionalFields]" should "create instance of the type with properties wrapped with Some case class" in {
    val defaultInstanceObj = new ClassWithOptionalFields
    val obj = mapper.mapToObject[ClassWithOptionalFields](
      HashMap[String, Any](
        "optionalLong" -> 10L,
        "optionalString" -> "Some string",
        "optionalShort" -> 20.toShort,
        "optionalBoolean" -> true,
        "optionalInt" -> 30,
        "optionalChar" -> 'a',
        "optionalFloat" -> 1.0f,
        "optionalDouble" -> 2.0
      )
    )

    defaultInstanceObj.optionalChar shouldEqual None
    defaultInstanceObj.optionalInt shouldEqual None
    defaultInstanceObj.optionalLong shouldEqual None
    defaultInstanceObj.optionalString shouldEqual None
    defaultInstanceObj.optionalBoolean shouldEqual None
    defaultInstanceObj.optionalShort shouldEqual None
    defaultInstanceObj.optionalFloat shouldEqual None
    defaultInstanceObj.optionalDouble shouldEqual None

    obj.optionalChar shouldEqual Some('a')
    obj.optionalInt shouldEqual Some(30)
    obj.optionalLong shouldEqual Some(10L)
    obj.optionalString shouldEqual Some("Some string")
    obj.optionalBoolean shouldEqual Some(true)
    obj.optionalShort shouldEqual Some(20.toShort)
    obj.optionalFloat shouldEqual Some(1.0f)
    obj.optionalDouble shouldEqual Some(2.0)
  }
}
