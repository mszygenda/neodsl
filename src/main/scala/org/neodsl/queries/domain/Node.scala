package org.neodsl.queries.domain

import org.neodsl.reflection.proxy.Proxyable

trait Node[+T >: Null <: Node[T]] extends Proxyable {
  val id: Option[Long]
}
