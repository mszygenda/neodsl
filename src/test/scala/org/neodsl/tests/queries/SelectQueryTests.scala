package org.neodsl.tests.queries

import org.neodsl.tests.BaseTests
import org.neodsl.queries.components.patterns.{RelationPattern, NodePattern, PatternTriple}
import org.neodsl.queries.domain.{-->, Relation}
import org.neodsl.tests.dsl.PatternDomain.Person
import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And}
import org.neodsl.queries.{Query, SelectQuery}
import org.neodsl.queries.components.conditions.NoConditions
import org.neodsl.db.{ExecutionEngine, ResultItem}
import scala.collection.immutable.HashMap
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.neodsl.tests.db.neo4j.cypher.FixedNameResolver

class SelectQueryTests extends BaseTests {
  val john = Person("John")
  val friend = Person("Friend")
  val fixedResolver = new FixedNameResolver(HashMap(friend -> "friend"))

  "SelectQuery#exec" should "map results into hashmap of domain objects" in {
    val pattern = PatternTriple(NodePattern(john), RelationPattern(Relation[Person, Person]("KNOWS", -->), Range(1, 1)), NodePattern(friend))
    val patterns = And(pattern, NoPatterns)
    val query = spy(SelectQuery(List(friend), patterns, NoConditions))
    when(query.nameResolver).thenReturn(fixedResolver)

    val resultMock = mockResultItem(Map("name" -> "Friend result"))
    val engineMock = mockExecutionEngine(List(resultMock))

    val results = query.exec(engineMock)

    results.head shouldEqual Map("friend" -> Person("Friend result"))
  }

  private def mockResultItem(hashMap: Map[String, Any]) = {
    val resultItemMock = mock[ResultItem]

    when(resultItemMock.getObjectMap(anyString())).thenReturn(hashMap)

    resultItemMock
  }

  private def mockExecutionEngine(results: List[ResultItem]) = {
    val execEngineMock = mock[ExecutionEngine]

    when(execEngineMock.execute(any[Query])).thenReturn(results)

    execEngineMock
  }
}
