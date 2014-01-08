package org.neodsl.dsl.patterns

import org.neodsl.dsl.domain.Node

case class UndefinedPatternElement[T >: Null <: Node[T]] extends PatternElement[T] {

}
