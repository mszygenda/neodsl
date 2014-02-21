package org.neodsl.queries.components.patterns

import org.neodsl.queries.domain.{Node, Relation}

trait Pattern

trait PatternWithNode[T >: Null <: Node[T]] extends Pattern

case class RelationPattern[T >: Null <: Node[T], U >: Null <: Node[U]](relation: Relation[T, U], connections: Range) extends Pattern

case class NodePattern[T >: Null <: Node[T]](node: Node[T]) extends PatternWithNode[T]
