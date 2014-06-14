package hacks

import scala.collection.immutable.HashMap
import scala.reflect.runtime.universe._
import net.sf.cglib.proxy.Enhancer
import org.neodsl.reflection.NodeObjectMapper

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

class Person(var name: String, var age: Int, var surname: String, var id: Option[Long]) {
}

object ReflectionBenchmark {
  val mapper = new NodeObjectMapper()
  val personClassType = typeOf[Person]

  def createPersonUsingReflection(name: String, age: Int): Person = {
    ObjectFactory.createObjectUsingMainCtor(typeOf[Person])(HashMap("age" -> 10), HashMap("name" -> name, "age" -> age, "id" -> Some(1L), "surname" -> "Morgue")) match {
      case p: Person => {
        p
      }
      case _ => {
        throw new Exception("Failed to create person using reflection")
      }
    }
  }

  def createPersonDirectly(name: String, age: Int): Person = {
    val p = new Person(null, 0, null, None)

    p.name = name
    p.age = age
    p.surname = "Bon jovi"
    p.id = Some(1L)

    p
  }

  def createPersonUsingCachedReflection(name: String, age: Int): Person = {
    mapper.mapToObject(personClassType)(HashMap("name" -> name, "age" -> age, "id" -> Some(1L), "surname" -> "Morgue")).asInstanceOf[Person]
  }

  def warmUp() {
    for (i <- 1 to 10000) {
      createPersonDirectly("john", 10)
      createPersonUsingCachedReflection("john", 10)
      createPersonUsingReflection("john", 10)
    }
  }

  def bench(methodName: String, code: (Int) => Unit) {
    val testRuns = 5
    val execTimes: Iterable[Double] = for (i <- 1 to testRuns) yield {
      measureTimeInSecs(200000)(code)
    }

    println(methodName + "," + (execTimes.sum / execTimes.size))
  }
  
  def measureTimeInSecs(times: Int)(code: (Int) => Unit): Double = {
    val start = System.currentTimeMillis()

    for (i <- 1 to times) {
      code(i)
    }

    val end = System.currentTimeMillis()

    ((end - start).asInstanceOf[Double] / 1000.0)
  }

  def main(args: Array[String]) {
    warmUp()

    bench("Directly", (i) => {
      createPersonDirectly("John", 10 * i)
    })

    bench("Cached Reflection", (i) => {
      createPersonUsingCachedReflection("John", 10 * i)
    })

    bench("Reflection", (i) => {
      createPersonUsingReflection("John", 10 * i)
    })
  }
}
