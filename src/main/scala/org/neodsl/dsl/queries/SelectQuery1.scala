package org.neodsl.dsl.queries

import org.neodsl.queries.domain.TypedNode
import org.neodsl.queries.components.patterns.compositions.PatternComposition
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.queries.SelectQuery
import org.neodsl.db.{ResultItem, ExecutionEngine}

class SelectQuery1[T >: Null <: TypedNode[T]](node: T, patterns: PatternComposition, conditions: Condition) extends SelectQuery(List(node), patterns, conditions) {
  def exec[U](processResult: T => U)(implicit engine: ExecutionEngine): List[U] = {
    val res = engine.execute(this).map(buildObjects)

    res.map(processResult)
  }

  private def buildObjects(row: ResultItem): T = {
    null
  }
}
