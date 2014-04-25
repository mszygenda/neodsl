package org.neodsl.reflection

import scala.collection.immutable.HashMap
import scala.reflect.runtime.universe._
import net.sf.cglib.proxy.Enhancer
import org.neodsl.reflection.proxy.{IndexPlaceholderProxy, Proxy, AnonymousPlaceholderProxy, Proxyable}

object ObjectFactory {
  def createObjectUsingDefaultCtor(classType: Type)(ctorParams: Map[String, Any], objProps: Map[String, Any]): Any = {
    createObjectUsingCtor(classType)(ctorParams, defaultCtor(classType).get, objProps)
  }

  def createObjectUsingMainCtor(classType: Type)(ctorParams: Map[String, Any], objProps: Map[String, Any]): Any = {
    createObjectUsingCtor(classType)(ctorParams, mainCtor(classType), objProps)
  }

  private def defaultOrMainCtor(classType: Type) = {
    defaultCtor(classType) match {
      case Some(defaultCtor) => defaultCtor
      case None => mainCtor(classType)
    }
  }

  private def mainCtor(classType: Type): MethodSymbol = {
    ctors(classType).find(_.isPrimaryConstructor).get
  }

  private def defaultCtor(classType: Type): Option[MethodSymbol] = {
    ctors(classType).find(ctor => ctor.paramss.isEmpty || ctor.paramss.head.isEmpty)
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

  def createIndexPlaceholder[P <: Proxyable](name: String, indexVal: Tuple2[String, Any])(implicit manifest: Manifest[P]): P = {
    val indexPlaceholderProxy = new IndexPlaceholderProxy(name, indexVal)

    createProxiedObject[P, IndexPlaceholderProxy](indexPlaceholderProxy)
  }

  def createAnonymousPlaceholderObject[P <: Proxyable](implicit manifest: Manifest[P]): P = {
    val placeholderProxy = new AnonymousPlaceholderProxy()

    createProxiedObject[P, AnonymousPlaceholderProxy](placeholderProxy)
  }

  def createProxiedObject[P <: Proxyable, U <: Proxy](proxy: U)(implicit manifest: Manifest[P]): P = {
    val enhancer = new Enhancer()
    val objType  = typeTag[P].tpe.typeSymbol.asClass.toType
    val objClass = classLoader.runtimeClass(objType)
    val objCtor = defaultOrMainCtor(objType)
    val ctorParamTypes = ctorArgumentTypes(objCtor)

    enhancer.setSuperclass(objClass)
    enhancer.setCallback(proxy)

    enhancer.create(ctorParamTypes.toArray, ctorParamTypes.map(tpe => defaultValueForClass(tpe)).toArray) match {
      case p: P => p
      case _ => throw new Exception("Proxy creation has failed")
    }
  }

  private def defaultValueForClass(tpe: Class[_]): AnyRef = {
    if (None.getClass.getSuperclass.isAssignableFrom(tpe)) {
      None
    } else {
      null
    }
  }

  private def ctorArgumentTypes(ctor: MethodSymbol) = {
    val paramList = ctor.paramss.head

    paramList.map(param => classLoader.runtimeClass(param.typeSignature))
  }
}