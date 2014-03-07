package org.neodsl.tests.db.neo4j.cypher

import org.neodsl.tests.BaseTests
import org.neodsl.queries.SelectQuery
import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And}
import org.neodsl.queries.components.patterns.{RelationPattern, NodePattern, PatternTripple}
import org.neodsl.tests.dsl.PatternDomain.{Person, PersonWithId}
import org.neodsl.dsl.patterns.PatternBuilder._
import org.neodsl.queries.domain._
import org.neodsl.queries.components.conditions._
import org.neodsl.db.neo4j.cypher.SelectSerializer
import scala.collection.immutable.HashMap
import org.neodsl.queries.components.patterns.NodePattern
import org.neodsl.queries.components.patterns.PatternTripple
import org.neodsl.queries.components.patterns.compositions.And
import org.neodsl.tests.dsl.PatternDomain.Person
import org.neodsl.queries.domain.Relation
import org.neodsl.queries.components.patterns.RelationPattern
import org.neodsl.queries.SelectQuery
import scala.Some
import org.neodsl.queries.components.conditions.ObjectPropertySelector
import org.neodsl.queries.components.conditions.PropertyComparison

class SelectSerializerTests extends BaseTests {
  val john = new PersonWithId(Some(1), "John")
  val friend = anonymous[Person]
  val fixedResolver = new FixedNameResolver(HashMap(john -> "john", friend -> "friend"))

  "Select query with single pattern and selected node" should "be serialized" in {
    val pattern = PatternTripple(NodePattern(john), RelationPattern(Relation[Person, Person]("KNOWS", -->), Range(1, 1)), NodePattern(friend))
    val patterns = And(pattern, NoPatterns)
    val query = SelectQuery(List(friend), patterns, NoConditions)

    val serializer = new SelectSerializer(query, fixedResolver)
    val expectedQuery =
      """
        |START john=node(1)
        |MATCH john-[:KNOWS]->friend
        |RETURN friend
      """.stripMargin

    serializer.serialize shouldEqual expectedQuery.trim
  }

  "Select query with single pattern and one condition" should "be serialized" in {
    val pattern = PatternTripple(NodePattern(john), RelationPattern(Relation[Person, Person]("KNOWS", -->), Range(1, 1)), NodePattern(friend))
    val patterns = And(pattern, NoPatterns)
    val cond = PropertyComparison(ObjectPropertySelector("john", "age"), Gt, SimpleValueSelector(20))
    val query = SelectQuery(List(friend), patterns, cond)

    val serializer = new SelectSerializer(query, fixedResolver)
    val expectedQuery =
      """
        |START john=node(1)
        |MATCH john-[:KNOWS]->friend
        |WHERE john.age > 20
        |RETURN friend
      """.stripMargin

    serializer.serialize shouldEqual expectedQuery.trim
  }
}
