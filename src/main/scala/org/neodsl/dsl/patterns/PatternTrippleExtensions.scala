package org.neodsl.dsl.patterns

import org.neodsl.queries.domain.Node
import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And}
import org.neodsl.queries.components.patterns.PatternTripple
import org.neodsl.dsl.Selfie

trait PatternTrippleExtensions[T >: Null <: Node[T], U >: Null <: Node[U]] extends Selfie[PatternTripple[T, U]] {
  def and[W >: Null <: Node[W], X >: Null <: Node[X]](another: PatternTripple[W, X]) = {
    And(another, And(self, NoPatterns))
  }
}
