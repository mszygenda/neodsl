package org.neodsl.dsl.queries

import org.neodsl.queries.domain.{ Node, TypedNode }
import org.neodsl.queries.components.patterns.compositions.PatternComposition
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.queries.SelectQuery
import org.neodsl.db.{ExecutionEngine}

/**
 * It's generated source file. Don't modify it (Change template instead)
 */
class SelectQuery4[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D]](nodes: List[Node], patterns: PatternComposition, conditions: Condition) extends SelectQuery(nodes, patterns, conditions) {
  def exec[U](processResult: (A, B, C, D) => U)(implicit engine: ExecutionEngine): Seq[U] = {
    nodes match {
        case nodeA :: nodeB :: nodeC :: nodeD :: Nil => {
            super.exec.map(resultMap => {
              processResult(
                    resultMap(nameResolver.name(nodeA)).asInstanceOf[A],
                    resultMap(nameResolver.name(nodeB)).asInstanceOf[B],
                    resultMap(nameResolver.name(nodeC)).asInstanceOf[C],
                    resultMap(nameResolver.name(nodeD)).asInstanceOf[D]
              )
            })
        }
        case _ => {
            throw new Exception("Invalid number of nodes selected for SelectQuery4. It should be 4 nodes")
        }
    }
  }
}
