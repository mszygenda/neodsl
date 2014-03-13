package org.neodsl.dsl.patterns

import org.neodsl.queries.domain.TypedNode
import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And}
import org.neodsl.queries.components.patterns.PatternTripple
import org.neodsl.dsl.Selfie
import org.neodsl.queries.components.conditions.{NoConditions, Condition}
import org.neodsl.dsl.queries.{SelectQuery1, QueryBuilder}

trait PatternTrippleExtensions[T >: Null <: TypedNode[T], U >: Null <: TypedNode[U]] extends Selfie[PatternTripple[T, U]] {
  def and[W >: Null <: TypedNode[W], X >: Null <: TypedNode[X]](another: PatternTripple[W, X]) = {
    And(another, And(self, NoPatterns))
  }

  def where(condition: => Condition) = {
    new QueryBuilder(And(self, NoPatterns), condition)
  }

  def select[T >: Null <: TypedNode[T]](node: T) = {
    new SelectQuery1(node, And(self, NoPatterns), NoConditions)
  }
}
