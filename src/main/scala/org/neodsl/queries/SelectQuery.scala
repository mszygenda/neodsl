package org.neodsl.queries

import org.neodsl.queries.components.patterns.compositions.PatternComposition
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.queries.domain.Node

abstract case class SelectQuery(nodes: List[Node[_]], patterns: PatternComposition, condition: Condition) extends Query {
  def startNodes = {
    nodes.filter(_.id match {
      case Some(_) => true
      case None => false
    })
  }
}
