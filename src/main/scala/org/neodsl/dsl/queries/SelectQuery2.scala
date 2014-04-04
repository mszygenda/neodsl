package org.neodsl.dsl.queries

import org.neodsl.queries.domain.{ Node, TypedNode }
import org.neodsl.queries.components.patterns.compositions.PatternComposition
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.queries.SelectQuery
import org.neodsl.db.{ExecutionEngine}

/**
 * It's generated source file. Don't modify it (Change template instead)
 */
class SelectQuery2[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B]](nodes: List[Node], patterns: PatternComposition, conditions: Condition) extends SelectQuery(nodes, patterns, conditions) {
  def exec[U](processResult: (A, B) => U)(implicit engine: ExecutionEngine): Seq[U] = {
    nodes match {
        case nodeA :: nodeB :: Nil => {
            super.exec.map(resultMap => {
              processResult(
                    resultMap(nameResolver.name(nodeA)).asInstanceOf[A],
                    resultMap(nameResolver.name(nodeB)).asInstanceOf[B]
              )
            })
        }
        case _ => {
            throw new Exception("Invalid number of nodes selected for SelectQuery2. It should be 2 nodes")
        }
    }
  }
}
