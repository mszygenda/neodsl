package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.domain.Node

object ReturnSerializer {
  def serialize(nodes: List[Node[_]], resolver: NameResolver): String = {
    val nodeNames = nodes.map(resolver.name)

    "%s %s" format (CypherKeywords.RETURN, nodeNames.mkString(","))
  }
}
