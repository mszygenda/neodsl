package org.neodsl.tests.db.neo4j.cypher

import org.neodsl.tests.BaseTests
import org.neodsl.tests.dsl.PatternDomain.Person
import org.neodsl.queries.components.patterns.{RelationPattern, NodePattern, PatternTripple}
import org.neodsl.db.neo4j.cypher.{BasicNameResolver, PatternSerializer}
import org.neodsl.queries.domain.{-->, Relation}
import org.neodsl.dsl.patterns.PatternBuilder._

class BasicNameResolverTests extends BaseTests {
  class PersonWithId(override val id: Option[Long], name: String) extends Person(name)

  val nodeWithId = new PersonWithId(Some(123), "John")
  val nodeWithoutId = p[Person]
  val anotherNodeWithoutId = p[Person]

  "Nodes with id defined" should "have id_ prefix" in {
    val resolver = new BasicNameResolver()

    resolver.name(nodeWithId) shouldEqual ("id_123")
  }

  "Placeholder nodes" should "be assigned subsequent numbers suffixes" in {
    val resolver = new BasicNameResolver()

    resolver.name(nodeWithoutId) shouldEqual ("n_1")
    resolver.name(anotherNodeWithoutId) shouldEqual ("n_2")
  }

  "Resolver" should "always return the same name for given *placeholder instance*" in {
    val resolver = new BasicNameResolver()

    resolver.name(nodeWithoutId) shouldEqual ("n_1")
    resolver.name(anotherNodeWithoutId) shouldEqual ("n_2")
    resolver.name(nodeWithoutId) shouldEqual ("n_1")
    resolver.name(anotherNodeWithoutId) shouldEqual ("n_2")
  }
}
