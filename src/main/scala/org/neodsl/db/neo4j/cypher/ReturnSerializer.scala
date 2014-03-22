package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.domain.{Node, TypedNode}
import org.neodsl.reflection.ObjectMapper
import scala.reflect.runtime.universe._

object ReturnSerializer {
  def serialize(nodes: List[Node], resolver: NameResolver, mapper: ObjectMapper): String = {
    val nodeSelectors = nodes.map(selectProperties(_, resolver, mapper))

    "%s %s" format (CypherKeywords.RETURN, nodeSelectors.mkString(","))
  }

  def selectProperties(node: Node, resolver: NameResolver, mapper: ObjectMapper) = {
    val properties = mapper.getPropertyNames(node.nodeType)
    val objectName = resolver.name(node)

    println("PROPS " + properties + "TYPE " + typeOf[node.type].typeSymbol.name.decoded)
    properties.map(objectName + "." + _).mkString(",")
  }
}
