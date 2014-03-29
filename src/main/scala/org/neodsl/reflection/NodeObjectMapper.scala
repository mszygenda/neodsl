package org.neodsl.reflection

import scala.collection.immutable.HashMap
import org.neodsl.reflection.proxy.Proxyable
import scala.reflect.runtime.universe._

class NodeObjectMapper extends ObjectMapper {
  override def mapToObject(classType: Type)(map: Map[String, Any]): Any = {
    val convertedMap = convertValuesToConformPropertiesTypes(classType)(map)
    
    ObjectFactory.createObjectUsingMainCtor(classType)(HashMap(), convertedMap)
  }

  private def convertValuesToConformPropertiesTypes(classType: Type)(propertiesMap: Map[String, Any]): Map[String, Any] = {
    val props = getProperties(classType).map(
      prop => prop.name.decoded.trim -> prop
    ).toMap
    val optionType = typeOf[Option[Any]]

    val converted = for ((name, value) <- propertiesMap) yield {
      if (props(name).typeSignature.<:<(optionType)) {
        name -> Some(value)
      } else {
        name -> value
      }
    }
    
    converted.toMap
  }

  override def getPropertyNames(classType: Type): List[String] = {
    val simpleProps: List[TermSymbol] = getProperties(classType)

    simpleProps.map(_.name.decoded.trim).sortBy(s => s)
  }

  private def getProperties(classType: Type): List[TermSymbol] = {
    ObjectFactory.getProperties(classType).filter(prop => {
      isSimpleProperty(prop) && !isLibraryProperty(prop)
    })
  }

  private def isSimpleProperty(prop: TermSymbol): Boolean = {
    val basicTypes = List(typeOf[Option[Long]], typeOf[Option[scala.Boolean]], typeOf[Option[Int]], typeOf[Option[Long]], typeOf[Option[Short]], typeOf[Option[String]], typeOf[Option[Double]], typeOf[Option[Float]], typeOf[Option[Char]], typeOf[scala.Boolean], typeOf[Int], typeOf[Long], typeOf[Short], typeOf[String], typeOf[Double], typeOf[Float], typeOf[Char])

    (prop.isVal || prop.isVar) && basicTypes.exists(_ =:= prop.typeSignature)
  }

  private def isLibraryProperty(prop: TermSymbol): Boolean = {
    val proxyProps = getProperties(typeOf[Proxyable])
    val propName = prop.name.decoded.trim

    propName.contains("$") || proxyProps.contains(prop)
  }
}
