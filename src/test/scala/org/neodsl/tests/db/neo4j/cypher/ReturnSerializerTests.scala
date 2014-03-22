package org.neodsl.tests.db.neo4j.cypher

import org.neodsl.tests.BaseTests
import org.neodsl.tests.dsl.PatternDomain.Person
import scala.collection.immutable.HashMap
import org.neodsl.db.neo4j.cypher.ReturnSerializer
import org.neodsl.reflection.{NodeObjectMapper, ObjectMapper}
import org.neodsl.queries.domain.Node

class ReturnSerializerTests extends BaseTests {
  val john = Person("John")
  val friend = Person("Friend")
  val fixedResolver = new FixedNameResolver(HashMap(john -> "john", friend -> "friend"))
  val mapper: ObjectMapper = new NodeObjectMapper()

  "Return single node" should "be serialized" in {
    val nodes: List[Node] = List(john)

    ReturnSerializer.serialize(nodes, fixedResolver, mapper) shouldEqual "RETURN john.id,john.name"
  }

  "Return two nodes" should "be serialized" in {
    val nodes: List[Node] = List(john, friend)

    ReturnSerializer.serialize(nodes, fixedResolver, mapper) shouldEqual "RETURN john.id,john.name,friend.id,friend.name"
  }
}
