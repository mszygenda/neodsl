package org.neodsl.dsl.queries

import org.neodsl.queries.components.patterns.compositions.PatternComposition
import org.neodsl.queries.components.conditions.{Or, And, Condition}

case class QueryBuilder(patterns: PatternComposition, conditions: Condition) {
  def and(condition: Condition) = {
    new QueryBuilder(patterns, And(condition, conditions))
  }

  def or(condition: Condition) = {
    new QueryBuilder(patterns, Or(condition, conditions))
  }
}
