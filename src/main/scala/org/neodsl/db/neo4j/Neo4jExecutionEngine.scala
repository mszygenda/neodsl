package org.neodsl.db.neo4j

import org.neodsl.db.ExecutionEngine
import org.neodsl.queries.Query

class Neo4jExecutionEngine extends ExecutionEngine {
  def execute(query: Query): Unit = {
    //TODO: Serialize to cypher and call DB
  }
}
