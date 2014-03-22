package org.neodsl.reflection

import scala.collection.immutable.HashMap
import org.neodsl.reflection.proxy.Proxyable
import scala.reflect.runtime.universe._

class NodeObjectMapper extends ObjectMapper {
  override def mapToObject(classType: Type)(map: HashMap[String, Any]): Any = {
    ObjectFactory.createObjectUsingMainCtor(classType)(HashMap(), map)
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
    val basicTypes = List(typeOf[Option[Long]], typeOf[scala.Boolean], typeOf[Int], typeOf[Long], typeOf[Short], typeOf[String], typeOf[Double], typeOf[Float])

    (prop.isVal || prop.isVar) && basicTypes.exists(_ =:= prop.typeSignature)
  }

  private def isLibraryProperty(prop: TermSymbol): Boolean = {
    val proxyProps = getProperties(typeOf[Proxyable])
    val propName = prop.name.decoded.trim

    propName.contains("$") || proxyProps.contains(prop)
  }
}
