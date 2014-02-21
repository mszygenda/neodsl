package org.neodsl.queries.components.patterns

import org.neodsl.queries.domain.Node
import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And}
import org.neodsl.dsl.patterns.PatternTrippleExtensions

case class PatternTripple[T >: Null <: Node[T], U >: Null <: Node[U]](head: NodePattern[T], relation: RelationPattern[T, U], tail: PatternWithNode[U])
  extends PatternWithNode[T]
  with PatternTrippleExtensions[T, U]