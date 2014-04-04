package org.neodsl.dsl.queries

import org.neodsl.queries.domain.{ Node, TypedNode }
import org.neodsl.queries.components.patterns.compositions.PatternComposition
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.queries.SelectQuery
import org.neodsl.db.{ExecutionEngine}

/**
 * It's generated source file. Don't modify it (Change template instead)
 */
class SelectQuery10[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E], F >: Null <: TypedNode[F], G >: Null <: TypedNode[G], H >: Null <: TypedNode[H], I >: Null <: TypedNode[I], J >: Null <: TypedNode[J]](nodes: List[Node], patterns: PatternComposition, conditions: Condition) extends SelectQuery(nodes, patterns, conditions) {
  def exec[U](processResult: (A, B, C, D, E, F, G, H, I, J) => U)(implicit engine: ExecutionEngine): Seq[U] = {
    nodes match {
        case nodeA :: nodeB :: nodeC :: nodeD :: nodeE :: nodeF :: nodeG :: nodeH :: nodeI :: nodeJ :: Nil => {
            super.exec.map(resultMap => {
              processResult(
                    resultMap(nameResolver.name(nodeA)).asInstanceOf[A],
                    resultMap(nameResolver.name(nodeB)).asInstanceOf[B],
                    resultMap(nameResolver.name(nodeC)).asInstanceOf[C],
                    resultMap(nameResolver.name(nodeD)).asInstanceOf[D],
                    resultMap(nameResolver.name(nodeE)).asInstanceOf[E],
                    resultMap(nameResolver.name(nodeF)).asInstanceOf[F],
                    resultMap(nameResolver.name(nodeG)).asInstanceOf[G],
                    resultMap(nameResolver.name(nodeH)).asInstanceOf[H],
                    resultMap(nameResolver.name(nodeI)).asInstanceOf[I],
                    resultMap(nameResolver.name(nodeJ)).asInstanceOf[J]
              )
            })
        }
        case _ => {
            throw new Exception("Invalid number of nodes selected for SelectQuery10. It should be 10 nodes")
        }
    }
  }
}
