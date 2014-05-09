package org.neodsl.tests.db.neo4j.cypher

import org.neodsl.tests.BaseTests
import org.neodsl.tests.dsl.PatternDomain.Person
import scala.collection.immutable.HashMap
import org.neodsl.db.neo4j.cypher.ReturnSerializer
import org.neodsl.reflection.{NodeObjectMapper, ObjectMapper}
import org.neodsl.queries.domain.Node
import org.neodsl.tests.db.neo4j.PersonWithId

class ReturnSerializerTests extends BaseTests {
  val john = Person("John")
  val friend = Person("Friend")
  val personWithId = PersonWithId(Some(1), "I have id")
  val mapper: ObjectMapper = new NodeObjectMapper()

  val fixedResolver = new FixedNameResolver(
    HashMap(
      john -> "john",
      friend -> "friend",
      personWithId -> "personWithId"
    )
  )

  "Return single node" should "be serialized" in {

    val nodes: List[Node] = List(john)
    ReturnSerializer.serialize(nodes, fixedResolver, mapper) shouldEqual "RETURN id(john) AS `john.id`,john.name"
  }

  "Return two nodes" should "be serialized" in {

    val nodes: List[Node] = List(john, friend)
    ReturnSerializer.serialize(nodes, fixedResolver, mapper) shouldEqual "RETURN id(john) AS `john.id`,john.name,id(friend) AS `friend.id`,friend.name"
  }

  "id property" should "be handled in exceptional way" in {
    ReturnSerializer.serialize(List(personWithId), fixedResolver, mapper) shouldEqual "RETURN id(personWithId) AS `personWithId.id`,personWithId.name"
  }
}
