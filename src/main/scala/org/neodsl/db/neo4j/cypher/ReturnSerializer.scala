package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.domain.Node
import org.neodsl.reflection.ObjectMapper
import org.neodsl.queries.NameResolver

object ReturnSerializer {
  def serialize(nodes: List[Node], resolver: NameResolver, mapper: ObjectMapper): String = {
    val nodeSelectors = nodes.map(selectProperties(_, resolver, mapper))

    "%s %s" format (CypherKeywords.RETURN, nodeSelectors.mkString(","))
  }

  def selectProperties(node: Node, resolver: NameResolver, mapper: ObjectMapper) = {
    val properties = mapper.getPropertyNames(node.nodeType)
    val objectName = resolver.name(node)

    properties.map(getPropertySelector(objectName, _)).mkString(",")
  }

  private def getPropertySelector(objName: String, propertyName: String) = {
    propertyName match {
      case "id" => {
        "id(%s) AS `%s.id`" format (objName, objName)
      }
      case _ => {
        objName + "." + propertyName
      }
    }
  }
}
