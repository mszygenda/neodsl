package org.neodsl.dsl.patterns

import org.neodsl.dsl.domain.{Node, Relation}

case class RelationElement[T >: Null <: Node[T], U >: Null <: Node[U]](relation: Relation[T, U], connections: Range) extends PatternElement[T]
