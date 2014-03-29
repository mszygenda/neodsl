package org.neodsl.db.neo4j

import org.neodsl.db.{ResultItem, ExecutionEngine}
import org.neodsl.queries.Query
import org.neodsl.db.neo4j.cypher.CypherSerializer
import org.neo4j.rest.graphdb.RestAPIFacade
import scala.collection.JavaConversions._
import java.util

class Neo4jExecutionEngine(engine: RestAPIFacade) extends ExecutionEngine {
  def execute(query: Query): List[ResultItem] = {
    val cypherQuery = serialize(query)
    val res = engine.query(cypherQuery, new util.HashMap())
    val columns = res.getColumns
    val data = res.getData.toList

    data.map(row => {
      Neo4jResultItem(columns.zip(row).toMap)
    })
  }

  private def serialize(query: Query): String = {
    val cypher = CypherSerializer.serialize(query, query.nameResolver)

    println("Serialized query into: " + cypher)

    cypher
  }
}
