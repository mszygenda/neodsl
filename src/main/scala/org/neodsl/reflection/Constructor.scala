package org.neodsl.reflection
import scala.reflect.runtime.universe._

class Constructor(methodSymbol: MethodSymbol, classMirror: ClassMirror, classLoader: Mirror) {
  private val ctorMirror = classMirror.reflectConstructor(methodSymbol)
  val isPrimary = methodSymbol.isPrimaryConstructor
  val isDefault = methodSymbol.paramss.isEmpty || methodSymbol.paramss.head.isEmpty

  def callWithDefaults(): Any = {
    call(Map())
  }

  def call(paramsMap: Map[String, Any]): Any = {
    val params = for (param <- methodSymbol.paramss.head) yield {
      paramsMap.getOrElse(param.name.decoded, {
        null
      })
    }

    ctorMirror(params.toArray.toSeq: _*)
  }
}
