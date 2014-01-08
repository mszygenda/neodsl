package org.neodsl.dsl.domain

import org.neodsl.dsl.patterns.{UndefinedPatternElement, RelationPatternElement, NodePatternElement, Pattern}
import org.neodsl.dsl.domain.{ --> => Right }

trait Node[T >: Null <: Node[T]] extends AnyRef {
  def -->[U >: Null <: Node[U]](name: String) = {
    Pattern(NodePatternElement[T](this), RelationPatternElement[T, U](name, Right), UndefinedPatternElement[U])
  }

  def <--[U >: Null <: Node[U]](name: String) = {
    -->(name)
  }
}
