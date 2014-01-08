package org.neodsl.dsl.patterns

import org.neodsl.dsl.domain.{RelationDirection, Node}

case class RelationPatternElement[T >: Null <: Node[T], U >: Null <: Node[U]](name: String, dir: RelationDirection) extends PatternElement[T]
