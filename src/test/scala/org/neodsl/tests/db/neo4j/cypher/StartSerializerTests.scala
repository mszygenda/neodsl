package org.neodsl.tests.db.neo4j.cypher

import org.neodsl.tests.BaseTests
import org.neodsl.queries.domain.Node
import org.neodsl.db.neo4j.cypher.StartSerializer
import scala.collection.immutable.HashMap
import org.neodsl.db.neo4j.cypher.exceptions.InvalidStartNodeException
import org.neodsl.tests.dsl.PatternDomain.{PersonWithId, Person}

class NodeWithoutId extends Node[NodeWithoutId] {
  val id: Option[Long] = None
}

class StartSerializerTests extends BaseTests {
  val john = new PersonWithId(Some(1), "John")
  val friend = new PersonWithId(Some(2), "Friend")
  val nodeWithoutId = new NodeWithoutId
  val fixedResolver = new FixedNameResolver(HashMap(friend -> "friend", john -> "john"))

  "InvalidStartNodeException" should "be thrown when anonymous node is used as start node" in {
    val invalidNodes = List(new NodeWithoutId)

    intercept[InvalidStartNodeException] {
      StartSerializer.serialize(invalidNodes, fixedResolver)
    }
  }

  "START with two nodes with ids" should "be serialized" in {
    val validNodes = List(john, friend)

    StartSerializer.serialize(validNodes, fixedResolver) shouldEqual "START john=node(1),friend=node(2)"
  }

  "START with single node with id" should "be serialized" in {
    val validNode = List(john)

    StartSerializer.serialize(validNode, fixedResolver) shouldEqual "START john=node(1)"
  }
}
