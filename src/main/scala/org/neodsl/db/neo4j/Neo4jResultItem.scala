package org.neodsl.db.neo4j

import org.neodsl.db.ResultItem

case class Neo4jResultItem(properties: Map[String, Any]) extends ResultItem {
  override def getObjectProperty(objectName: String, property: String): Any = {
    properties("%s.%s" format (objectName, property))
  }

  override def getObjectMap(objectName: String): Map[String, Any] = {
    val objProps = properties.filter(_._1.startsWith(objectName + "."))

    stripObjectPrefix(objProps)
  }

  private def stripObjectPrefix(objProps: Map[String, Any]): Map[String, Any] = {
    objProps.map(_ match {
      case (name, value) => {
        (name.split('.').last, value)
      }
    })
  }
}
