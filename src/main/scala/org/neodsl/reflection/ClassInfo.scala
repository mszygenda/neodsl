package org.neodsl.reflection

import scala.reflect.runtime.universe._

class ClassInfo(val classType: Type, classLoader: Mirror) {
  private val classMirror = classLoader.reflectClass(classType.typeSymbol.asClass)

  lazy val fullName: String = {
    javaClass.getName
  }

  lazy val javaClass: Class[_] = {
    classLoader.runtimeClass(classType)
  }

  def property(name: String): Option[Property] = {
    properties.find(_.name == name)
  }

  lazy val customSimpleProperties: List[Property] = {
    simpleProperties.filter(!_.isLibraryProperty)
  }

  lazy val simpleProperties: List[Property] = {
    properties.filter(_.isSimpleProperty)
  }

  lazy val properties: List[Property] = {
    val terms = classType.members.filter(_.isTerm).map(_.asTerm)

    terms.filter(_.isAccessor).
      map(term => new Property(
      term.accessed.asTerm,
      classLoader
    )).toList
  }

  lazy val defaultOrMainCtor: Constructor = {
    ctors.find(_.isDefault).getOrElse {
      mainCtor
    }
  }

  lazy val mainCtor: Constructor = {
    ctors.find(_.isPrimary).get
  }

  lazy val ctors: List[Constructor] = {
    val symbolList = classType.decl(termNames.CONSTRUCTOR).asTerm.alternatives

    symbolList.map(symbol => new Constructor(symbol.asMethod, classMirror, classLoader))
  }
}
