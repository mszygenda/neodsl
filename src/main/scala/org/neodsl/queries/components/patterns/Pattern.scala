package org.neodsl.queries.components.patterns

import org.neodsl.queries.domain.{TypedNode, Relation}

trait Pattern

trait PatternWithNode[T >: Null <: TypedNode[T]] extends Pattern {
  def nodes: List[TypedNode[_]]
}

case class RelationPattern[T >: Null <: TypedNode[T], U >: Null <: TypedNode[U]](relation: Relation[T, U], connections: Range) extends Pattern

case class NodePattern[T >: Null <: TypedNode[T]](node: TypedNode[T]) extends PatternWithNode[T]{
  def nodes: List[TypedNode[_]] = List(node)
}
