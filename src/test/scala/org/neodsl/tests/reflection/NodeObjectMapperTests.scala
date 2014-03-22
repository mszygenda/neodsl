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
}

class ClassWhichInheritsByDomainObject extends DomainObject[ClassWhichInheritsByDomainObject] {
  val prop1: String = "test"
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

  "NodeObjectMapper#getProperties[ClassWithSimpleAndComplexProperties]" should "return only simple properties: stringProp, intProp, shortProp, doubleProp, floatProp, boolProp, longProp" in {
    mapper.getPropertyNames[ClassWithSimpleAndComplexProperties] shouldEqual List("boolProp", "doubleProp", "floatProp", "intProp", "longProp", "shortProp", "stringProp")
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
}
