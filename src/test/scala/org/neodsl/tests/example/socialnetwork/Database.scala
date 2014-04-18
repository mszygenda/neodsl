package org.neodsl.tests.example.socialnetwork

import org.neodsl.db.ExecutionEngine
import org.neodsl.db.neo4j.Neo4jExecutionEngine
import org.neo4j.rest.graphdb.RestAPIFacade

object Database {
  private val restService = new RestAPIFacade("http://localhost:7474/db/data")

  implicit def db: ExecutionEngine = new Neo4jExecutionEngine(restService)
}
