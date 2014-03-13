package org.neodsl.dsl.queries

import org.neodsl.queries.domain.TypedNode
import org.neodsl.queries.components.patterns.compositions.PatternComposition
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.queries.SelectQuery

class SelectQuery1[T >: Null <: TypedNode[T]](node: T, patterns: PatternComposition, conditions: Condition) extends SelectQuery(List(node), patterns, conditions) {
  def exec[U](processResult: T => U): U = {
    // TODO: Get result from DB
    val res1 = null

    processResult(res1)
  }
}
