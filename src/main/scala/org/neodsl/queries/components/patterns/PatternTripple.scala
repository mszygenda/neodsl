package org.neodsl.queries.components.patterns

import org.neodsl.queries.domain.TypedNode
import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And}
import org.neodsl.dsl.patterns.PatternTrippleExtensions

case class PatternTripple[T >: Null <: TypedNode[T], U >: Null <: TypedNode[U]](head: NodePattern[T], relation: RelationPattern[T, U], tail: PatternWithNode[U])
  extends PatternWithNode[T]
  with PatternTrippleExtensions[T, U] {
  def nodes: List[TypedNode[_]] = {
    head.node :: tail.nodes
  }
}