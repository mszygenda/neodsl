package org.neodsl.queries.domain

import org.neodsl.reflection.proxy.Proxyable

trait Node extends Proxyable {
  val id: Option[Long]
}

trait TypedNode[+T >: Null <: TypedNode[T]] extends Node