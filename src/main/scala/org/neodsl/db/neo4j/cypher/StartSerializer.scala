package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.domain.TypedNode
import org.neodsl.db.neo4j.cypher.exceptions.InvalidStartNodeException

object StartSerializer {
  def serialize(nodes: List[TypedNode[_]], resolver: NameResolver): String = {
    val startNames = nodes.map(nodeAlias(_, resolver))

    "%s %s" format (CypherKeywords.START, startNames.mkString(","))
  }

  private def nodeAlias(node: TypedNode[_], resolver: NameResolver): String = {
    node.id match {
      case Some(id) =>
        "%s=node(%d)" format (resolver.name(node), node.id.get)
      case None =>
        throw new InvalidStartNodeException(node)
    }
  }
}
