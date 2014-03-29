package org.neodsl.reflection

import scala.collection.immutable.HashMap
import scala.reflect.runtime.universe._
import org.neodsl.queries.domain.Node

trait ObjectMapper {
  def getPropertyNames[T](implicit manifest: Manifest[T]): List[String] = {
    getPropertyNames(typeOf[T])
  }

  def getPropertyNames(classType: Type): List[String]

  def mapToObject[T](map: HashMap[String, Any])(implicit manifest: Manifest[T]): T = {
    mapToObject(typeOf[T])(map).asInstanceOf[T]
  }

  def mapToObject(classType: Type)(map: Map[String, Any]): Any
}
