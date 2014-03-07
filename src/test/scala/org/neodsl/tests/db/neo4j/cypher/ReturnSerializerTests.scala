package org.neodsl.tests.db.neo4j.cypher

import org.neodsl.tests.BaseTests
import org.neodsl.tests.dsl.PatternDomain.Person
import scala.collection.immutable.HashMap
import org.neodsl.db.neo4j.cypher.ReturnSerializer

class ReturnSerializerTests extends BaseTests {
  val john = Person("John")
  val friend = Person("Friend")
  val fixedResolver = new FixedNameResolver(HashMap(john -> "john", friend -> "friend"))

  "Return single node" should "be serialized" in {
    val nodes = List(john)

    ReturnSerializer.serialize(nodes, fixedResolver) shouldEqual "RETURN john"
  }

  "Return two nodes" should "be serialized" in {
    val nodes = List(john, friend)

    ReturnSerializer.serialize(nodes, fixedResolver) shouldEqual "RETURN john,friend"
  }
}
