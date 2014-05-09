package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.domain.Node
import org.neodsl.db.neo4j.cypher.exceptions.InvalidStartNodeException
import org.neodsl.queries.NameResolver
import org.neodsl.reflection.proxy.IndexPlaceholderProxy

object StartSerializer {
  def serialize(nodes: List[Node], resolver: NameResolver): String = {
    nodes match {
      case Nil => ""
      case _ => {
        val startNames = nodes.map(nodeAlias(_, resolver))

        "%s %s" format(CypherKeywords.START, startNames.mkString(","))
      }
    }
  }

  private def nodeAlias(node: Node, resolver: NameResolver): String = {
    node.id match {
      case Some(id) =>
        "%s=node(%d)" format (resolver.name(node), node.id.get)
      case None => {
        if (node.isProxied) {
          nodeAliasForPlaceholder(node, resolver)
        } else {
          throw new InvalidStartNodeException(node)
        }
      }
    }
  }

  def nodeAliasForPlaceholder(node: Node, resolver: NameResolver): String = {
    node.proxy match {
      case IndexPlaceholderProxy(indexName, (property, value)) => {
        "%s=node:%s(%s=%s)" format (
          resolver.name(node),
          indexName,
          property,
          SimpleValueSerializer.serialize(value)
        )
      }
      case _ => {
        throw new InvalidStartNodeException(node)
      }
    }
  }
}
