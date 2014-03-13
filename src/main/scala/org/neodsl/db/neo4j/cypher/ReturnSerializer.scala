package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.domain.TypedNode

object ReturnSerializer {
  def serialize(nodes: List[TypedNode[_]], resolver: NameResolver): String = {
    val nodeNames = nodes.map(resolver.name)

    "%s %s" format (CypherKeywords.RETURN, nodeNames.mkString(","))
  }
}
