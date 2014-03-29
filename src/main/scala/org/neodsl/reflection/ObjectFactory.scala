package org.neodsl.reflection

import scala.collection.immutable.HashMap
import scala.reflect.runtime.universe._
import net.sf.cglib.proxy.Enhancer
import org.neodsl.reflection.proxy.{Proxy, PlaceholderProxy, Proxyable}

object ObjectFactory {
  def createObjectUsingDefaultCtor(classType: Type)(ctorParams: Map[String, Any], objProps: Map[String, Any]): Any = {
    val defaultCtor = ctors(classType).find(_.paramss.isEmpty)

    createObjectUsingCtor(classType)(ctorParams, defaultCtor.get, objProps)
  }

  def createObjectUsingMainCtor(classType: Type)(ctorParams: Map[String, Any], objProps: Map[String, Any]): Any = {
    val mainCtor = ctors(classType).sortBy(_.paramss.size).last

    createObjectUsingCtor(classType)(ctorParams, mainCtor, objProps)
  }

  private def ctors(classType: Type) = {
    val symbolList = classType.declaration(nme.CONSTRUCTOR).asTerm.alternatives

    symbolList.map(_.asMethod)
  }

  def createObjectUsingCtor(classType: Type)(ctorParams: Map[String, Any], ctor: MethodSymbol, objectProps: Map[String, Any]): Any = {
    val objClass = classType.typeSymbol.asClass
    val classMirror = classLoader.reflectClass(objClass)
    val ctorMirror = classMirror.reflectConstructor(ctor)

    val params = for (param <- ctor.paramss.head) yield {
      ctorParams.getOrElse(param.name.decoded, {
        null
      })
    }

    val obj = ctorMirror(params.toArray.toSeq: _*)
    setObjectProperties(classType)(obj, objectProps)
  }

  def setObjectProperties(classType: Type)(obj: Any, props: Map[String, Any]): Any = {
    for ((property, value) <- props) {
      field(classType)(obj, property).set(value)
    }

    obj
  }

  private def field(classType: Type)(obj: Any, name: String) = {
    val fieldTerm = classType.member(TermName(name)).asTerm
    val instanceMirror = classLoader.reflect(obj)
    val fieldMirror = instanceMirror.reflectField(fieldTerm)

    fieldMirror
  }

  def getProperties(typeClass: Type): List[TermSymbol] = {
    val terms = typeClass.members.filter(_.isTerm).map(_.asTerm)

    terms.filter(_.isAccessor).map(_.accessed.asTerm).toList
  }

  private def classLoader = runtimeMirror(getClass.getClassLoader)

  def createPlaceholderObject[P <: Proxyable](implicit manifest: Manifest[P]) = {
    val placeholderProxy = new PlaceholderProxy()

    createProxiedObject[P, PlaceholderProxy](placeholderProxy)
  }

  def createProxiedObject[P <: Proxyable, U <: Proxy](proxy: U)(implicit manifest: Manifest[P]): P = {
    val enhancer = new Enhancer()
    val objType  = typeTag[P].tpe.typeSymbol.asClass.toType
    val objClass = classLoader.runtimeClass(objType)

    enhancer.setSuperclass(objClass)
    enhancer.setCallback(proxy)

    enhancer.create match {
      case p: P => p
      case _ => throw new Exception("Proxy creation has failed")
    }
  }
}