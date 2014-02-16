package org.neodsl.dsl.patterns

import org.neodsl.dsl.domain.Node

case class NodeElement[T >: Null <: Node[T]](node: Node[T]) extends PatternElement[T]