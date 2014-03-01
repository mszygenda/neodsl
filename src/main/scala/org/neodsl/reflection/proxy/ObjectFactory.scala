package org.neodsl.reflection.proxy

import scala.collection.immutable.HashMap
import scala.reflect.runtime.{universe => ru}
import net.sf.cglib.proxy.Enhancer
import org.neodsl.reflection.proxy.IdentifierProxy

object ObjectFactory {
    def createObject[P](map: HashMap[String, Any])(implicit manifest: Manifest[P]) : P = {
      val objClass = ru.typeTag[P].tpe.typeSymbol.asClass
      val classMirror = classLoader.reflectClass(objClass)
      val ctor = ru.typeOf[P].declaration(ru.nme.CONSTRUCTOR).asMethod
      val ctorMirror = classMirror.reflectConstructor(ctor)

      val params = for (param <- ctor.paramss.head) yield {
        map.getOrElse(param.name.decoded, {
          null
        })
      }

      ctorMirror(params.toArray.toSeq: _*).asInstanceOf[P]
    }

    private def classLoader = ru.runtimeMirror(getClass.getClassLoader)

    def createPlaceholderObject[P <: Proxyable](implicit manifest: Manifest[P]) = {
      val placeholderProxy = new PlaceholderProxy()

      createProxiedObject[P, PlaceholderProxy](placeholderProxy)
    }

    def createProxiedObject[P <: Proxyable, U <: Proxy](proxy: U)(implicit manifest: Manifest[P]): P = {
      val enhancer = new Enhancer()
      val objType  = ru.typeTag[P].tpe.typeSymbol.asClass.toType
      val objClass = classLoader.runtimeClass(objType)

      enhancer.setSuperclass(objClass)
      enhancer.setCallback(proxy)

      enhancer.create match {
        case p: P => p
        case _ => throw new Exception("Proxy creation has failed")
      }
    }
}