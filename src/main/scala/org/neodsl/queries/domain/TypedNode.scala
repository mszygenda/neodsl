package org.neodsl.queries.domain

import org.neodsl.reflection.proxy.Proxyable
import scala.reflect.runtime.universe._

trait Node extends Proxyable {
  val id: Option[Long]
  val nodeType: Type
}

abstract class TypedNode[+T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]) extends Node {
  val nodeType: Type = typeOf[T]
}