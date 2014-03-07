package org.neodsl.queries

import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And, PatternComposition}
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.queries.domain.Node

case class SelectQuery(nodes: List[Node[_]], patterns: PatternComposition, condition: Condition) extends Query {
  def startNodes = {
    allNodes.filter(_.id match {
      case Some(_) => true
      case None => false
    })
  }

  private def allNodes = nodesOf(patterns)

  private def nodesOf(patternComposition: PatternComposition): List[Node[_]] = {
    patternComposition match {
      case And(pattern, tail) => {
        pattern.nodes ++ nodesOf(tail)
      }
      case NoPatterns => {
        Nil
      }
    }
  }
}
