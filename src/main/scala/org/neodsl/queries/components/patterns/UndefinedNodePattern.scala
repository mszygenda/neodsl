package org.neodsl.queries.components.patterns

import org.neodsl.queries.domain.Node

class UndefinedNodePattern[T >: Null <: Node[T]] extends PatternWithNode[T]

object UndefinedNodePattern {
  def apply[T >: Null <: Node[T]] = {
    new UndefinedNodePattern[T]
  }
}
