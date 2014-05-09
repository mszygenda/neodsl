package org.neodsl.queries.domain

import scala.reflect.runtime.universe._
import org.neodsl.instrumentation.Proxyable

trait Node extends Proxyable {
  val id: Option[Long]
  val nodeType: Type
}

abstract class TypedNode[+T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]) extends Node {
  override lazy val nodeType: Type = typeOf[T]
}