package org.neodsl.dsl.queries

import org.neodsl.queries.domain.{ Node, TypedNode }
import org.neodsl.queries.components.patterns.compositions.PatternComposition
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.queries.SelectQuery
import org.neodsl.db.{ExecutionEngine}

/**
 * It's generated source file. Don't modify it (Change template instead)
 */
class SelectQuery1[A >: Null <: TypedNode[A]](nodes: List[Node], patterns: PatternComposition, conditions: Condition) extends SelectQuery(nodes, patterns, conditions) {
  def exec[U](processResult: (A) => U)(implicit engine: ExecutionEngine): Seq[U] = {
    nodes match {
        case nodeA :: Nil => {
            super.exec.map(resultMap => {
              processResult(
                    resultMap(nameResolver.name(nodeA)).asInstanceOf[A]
              )
            })
        }
        case _ => {
            throw new Exception("Invalid number of nodes selected for SelectQuery1. It should be 1 nodes")
        }
    }
  }
}
