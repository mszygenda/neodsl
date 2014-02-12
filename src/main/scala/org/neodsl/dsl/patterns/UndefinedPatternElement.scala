package org.neodsl.dsl.patterns

import org.neodsl.dsl.domain.Node

class UndefinedPatternElement[T >: Null <: Node[T]] extends PatternElement[T] {

}

object UndefinedPatternElement {
  def apply[T >: Null <: Node[T]] = {
    new UndefinedPatternElement[T]()
  }
}


