package org.neodsl.reflection
import scala.reflect.runtime.universe._
import scala.collection.mutable

class Constructor(methodSymbol: MethodSymbol, classMirror: ClassMirror, classLoader: Mirror) {
  private val ctorMirror = classMirror.reflectConstructor(methodSymbol)
  private val fstParamList = methodSymbol.paramLists.head
  private val defaultParamValuesCache = mutable.HashMap[String, Any]()

  val isPrimary = methodSymbol.isPrimaryConstructor
  val isDefault = methodSymbol.paramLists.isEmpty || methodSymbol.paramLists.head.isEmpty

  val paramTypes = {
    fstParamList.map(param => classLoader.runtimeClass(param.typeSignature))
  }

  def callWithDefaults(): Any = {
    call(Map())
  }

  def call(paramsMap: Map[String, Any]): Any = {
    val params = for (param <- fstParamList) yield {
      paramsMap.getOrElse(param.name.decodedName.toString, {
        getDefaultValueFor(param)
      })
    }

    ctorMirror(params.toArray.toSeq: _*)
  }

  private def getDefaultValueFor(param: Symbol): Any = {
    defaultParamValuesCache.getOrElseUpdate(param.name.decodedName.toString, {
      calculateDefaultValueFor(param)
    })
  }
  
  private def calculateDefaultValueFor(param: Symbol): Any = {
    val typeSignature = param.typeSignature

    if (typeSignature.<:<(typeOf[AnyRef])) {
      null
    } else if (typeSignature.<:<(typeOf[Int])) {
      0
    } else if (typeSignature.<:<(typeOf[Long])) {
      0L
    } else if (typeSignature.<:<(typeOf[Short])) {
      0.toShort
    } else if (typeSignature.<:<(typeOf[Double])) {
      0.0
    } else if (typeSignature.<:<(typeOf[Char])) {
      0.toChar
    } else if (typeSignature.<:<(typeOf[Byte])) {
      0.toByte
    } else if (typeSignature.<:<(typeOf[Boolean])) {
      false
    } else {
      null
    }
  }
}
