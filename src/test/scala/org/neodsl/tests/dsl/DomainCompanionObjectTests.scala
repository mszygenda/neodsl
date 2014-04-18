package org.neodsl.tests.dsl

import org.neodsl.tests.BaseTests
import org.neodsl.tests.dsl.PatternDomain.Person
import org.neodsl.dsl.domain.{DomainObject, DomainCompanionObject}
import org.neodsl.db.ExecutionEngine
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.neodsl.queries.SelectQuery
import org.neodsl.queries.components.conditions.NoConditions
import org.neodsl.queries.components.patterns.compositions.NoPatterns
import org.neodsl.queries.domain.TypedNode
import scala.collection.immutable.HashMap

case class NodeWithId(override val id: Option[Long]) extends DomainObject[NodeWithId]
object NodeWithIdCompanionObject extends DomainCompanionObject[NodeWithId]

class DomainCompanionObjectTests extends BaseTests {

  "NodeWithIdCompanionObject#find(id)" should "create select query without conditions and patterns, using id index only" in {
    val nodeWithIdOne = NodeWithId(Some(1))
    val expectedQuery = SelectQuery(List(nodeWithIdOne), NoPatterns, NoConditions)

    val resultItem = mockResultItem(HashMap("id" -> 1))
    val execEngineMock = mockExecutionEngine(List(resultItem))

    val findByIdResult = NodeWithIdCompanionObject.find(1)(execEngineMock)

    verify(execEngineMock).execute(expectedQuery)
    findByIdResult shouldBe Some(NodeWithId(Some(1)))
  }
}
