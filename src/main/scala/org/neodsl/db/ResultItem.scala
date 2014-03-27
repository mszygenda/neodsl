package org.neodsl.db

trait ResultItem {
  def getObjectProperty(objectName: String, property: String): Any
  def getObjectMap(objectName: String): Map[String, Any]
}
