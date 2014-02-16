package org.neodsl.dsl.domain

import org.neodsl.dsl.patterns.{UndefinedPatternElement, RelationElement, NodeElement, PatternTrippleElement}
import org.neodsl.dsl.domain.{ --> => Right }

trait Node[T >: Null <: Node[T]] extends AnyRef {
  def -->[U >: Null <: Node[U]](name: String) = {
    PatternTrippleElement(NodeElement[T](this), RelationElement[T, U](name, Right), UndefinedPatternElement[U])
  }

  def <--[U >: Null <: Node[U]](name: String) = {
    -->(name)
  }
}
