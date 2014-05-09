package org.neodsl.reflection

import scala.collection.immutable.HashMap
import org.neodsl.reflection.proxy.Proxyable
import scala.reflect.runtime.universe._

class NodeObjectMapper extends ObjectMapper {
  override def mapToObject(classType: Type)(map: Map[String, Any]): Any = {
    val classInfo = ClassInfoFactory.getClassInfo(classType)
    val obj = classInfo.mainCtor.callWithDefaults()

    setObjectProperties(classInfo, obj, map)

    obj
  }

  private def setObjectProperties(classInfo: ClassInfo, obj: Any, props: Map[String, Any]) {
    val convertedMap = convertValuesToConformPropertiesTypes(classInfo)(props)

    for ((prop, value) <- convertedMap) {
      classInfo.property(prop).get.set(obj, value)
    }
  }

  private def convertValuesToConformPropertiesTypes(classInfo: ClassInfo)(propertiesMap: Map[String, Any]): Map[String, Any] = {
    val converted = for ((name, value) <- propertiesMap) yield {
      if (classInfo.property(name).get.isOption) {
        name -> Some(value)
      } else {
        name -> value
      }
    }
    
    converted.toMap
  }

  override def getPropertyNames(classType: Type): List[String] = {
    val classInfo = ClassInfoFactory.getClassInfo(classType)

    classInfo.customSimpleProperties.map(_.name).sorted
  }
}
