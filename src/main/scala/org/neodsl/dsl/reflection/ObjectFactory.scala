package org.neodsl.dsl.reflection

import scala.collection.immutable.HashMap
import scala.reflect.runtime.{universe => ru}

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
}
