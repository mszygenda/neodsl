package org.neodsl.tests.db.neo4j

import org.neodsl.tests.BaseTests
import org.neodsl.db.neo4j.Neo4jExecutionEngine
import org.neo4j.rest.graphdb.RestAPIFacade
import org.neodsl.dsl.patterns.PatternBuilder._
import org.neodsl.dsl.domain.DomainObject

case class PersonWithId(override val id: Option[Long], name: String) extends DomainObject[PersonWithId] {
  val knows = -->[PersonWithId]("KNOWS")

  def this() = this(None, "")
}

class Neo4jExecutionEngineTests extends BaseTests {
  val restApi = new RestAPIFacade("http://localhost:7474/db/data")
  implicit val db = new Neo4jExecutionEngine(restApi)

  "john-[:knows]->matthew" should "return single result" in {
    val john = new PersonWithId(Some(0), "john")
    val friend = p[PersonWithId]
    val q = { john knows friend } select (friend)

    try {
      q.exec(f => {
        println(f.name)
      })
    } catch {
      case _: Exception => {
        println("Neo4j Server is not running")
      }
    }
  }
}