package org.neodsl.reflection
import scala.reflect.runtime.universe._
import scala.collection.mutable

object ClassInfoFactory {
  private val infoCache = mutable.HashMap[Type, ClassInfo]()
  private val classLoader = runtimeMirror(getClass.getClassLoader)

  def getClassInfo(classType: Type): ClassInfo = {
    infoCache.getOrElseUpdate(classType, { createClassInfo(classType) })
  }

  private def createClassInfo(classType: Type): ClassInfo = {
    new ClassInfo(classType, classLoader)
  }
}
