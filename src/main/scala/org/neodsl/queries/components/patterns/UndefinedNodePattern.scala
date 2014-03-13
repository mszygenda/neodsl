package org.neodsl.queries.components.patterns

import org.neodsl.queries.domain.TypedNode

class UndefinedNodePattern[T >: Null <: TypedNode[T]] extends PatternWithNode[T] {
  def nodes: List[TypedNode[_]] = Nil
}

object UndefinedNodePattern {
  def apply[T >: Null <: TypedNode[T]] = {
    new UndefinedNodePattern[T]
  }
}
