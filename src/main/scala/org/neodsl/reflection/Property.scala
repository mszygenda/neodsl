package org.neodsl.reflection
import scala.reflect.runtime.universe._

class Property(fieldTerm: TermSymbol, classLoader: Mirror) {
  val name = fieldTerm.name.decoded.trim

  val isOption = {
    val optionType = typeOf[Option[Any]]

    fieldTerm.typeSignature <:< optionType
  }

  def set(obj: Any, value: Any) {
    getFieldMirror(obj).set(value)
  }

  def get(obj: Any): Any = {
    getFieldMirror(obj).get
  }

  private def getFieldMirror(obj: Any) = {
    val instanceMirror = classLoader.reflect(obj)

    instanceMirror.reflectField(fieldTerm)
  }

  val isSimpleProperty = {
    val basicTypes = List(typeOf[Option[Long]], typeOf[Option[scala.Boolean]], typeOf[Option[Int]], typeOf[Option[Long]], typeOf[Option[Short]], typeOf[Option[String]], typeOf[Option[Double]], typeOf[Option[Float]], typeOf[Option[Char]], typeOf[scala.Boolean], typeOf[Int], typeOf[Long], typeOf[Short], typeOf[String], typeOf[Double], typeOf[Float], typeOf[Char])

    (fieldTerm.isVal || fieldTerm.isVar) && basicTypes.exists(_ =:= fieldTerm.typeSignature)
  }

  private val proxyProps = List("proxy", "isProxied")

  val isLibraryProperty = {
    name.contains("$") || proxyProps.contains(name)
  }
}
