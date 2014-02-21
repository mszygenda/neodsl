package org.neodsl.queries.components.patterns

import org.neodsl.queries.domain.Node
import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And}

case class PatternTripple[T >: Null <: Node[T], U >: Null <: Node[U]](head: NodePattern[T], relation: RelationPattern[T, U], tail: PatternWithNode[U]) extends PatternWithNode[T] {
  def and[W >: Null <: Node[W], X >: Null <: Node[X]](another: PatternTripple[W, X]) = {
    And(another, And(this, NoPatterns))
  }
}