package org.neodsl.dsl.queries

import org.neodsl.queries.components.patterns.compositions.PatternComposition
import org.neodsl.queries.components.conditions.{Or, And, Condition}
import org.neodsl.queries.domain.{ TypedNode, Node }

case class QueryBuilder(patterns: PatternComposition, conditions: Condition) {
  def and(condition: Condition) = {
    new QueryBuilder(patterns, And(condition, conditions))
  }

  def or(condition: Condition) = {
    new QueryBuilder(patterns, Or(condition, conditions))
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E], F >: Null <: TypedNode[F], G >: Null <: TypedNode[G], H >: Null <: TypedNode[H], I >: Null <: TypedNode[I], J >: Null <: TypedNode[J]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E, nodeF: F, nodeG: G, nodeH: H, nodeI: I, nodeJ: J) = {
    new SelectQuery10[A, B, C, D, E, F, G, H, I, J](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeH, nodeI, nodeJ), patterns, conditions)
  }

  def select[A >: Null <: TypedNode[A]](nodeA: A) = {
    new SelectQuery1[A](List[Node](nodeA), patterns, conditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B]](nodeA: A, nodeB: B) = {
    new SelectQuery2[A, B](List[Node](nodeA, nodeB), patterns, conditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C]](nodeA: A, nodeB: B, nodeC: C) = {
    new SelectQuery3[A, B, C](List[Node](nodeA, nodeB, nodeC), patterns, conditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D]](nodeA: A, nodeB: B, nodeC: C, nodeD: D) = {
    new SelectQuery4[A, B, C, D](List[Node](nodeA, nodeB, nodeC, nodeD), patterns, conditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E) = {
    new SelectQuery5[A, B, C, D, E](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE), patterns, conditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E], F >: Null <: TypedNode[F]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E, nodeF: F) = {
    new SelectQuery6[A, B, C, D, E, F](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE, nodeF), patterns, conditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E], F >: Null <: TypedNode[F], G >: Null <: TypedNode[G]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E, nodeF: F, nodeG: G) = {
    new SelectQuery7[A, B, C, D, E, F, G](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG), patterns, conditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E], F >: Null <: TypedNode[F], G >: Null <: TypedNode[G], H >: Null <: TypedNode[H]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E, nodeF: F, nodeG: G, nodeH: H) = {
    new SelectQuery8[A, B, C, D, E, F, G, H](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeH), patterns, conditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E], F >: Null <: TypedNode[F], G >: Null <: TypedNode[G], H >: Null <: TypedNode[H], I >: Null <: TypedNode[I]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E, nodeF: F, nodeG: G, nodeH: H, nodeI: I) = {
    new SelectQuery9[A, B, C, D, E, F, G, H, I](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeH, nodeI), patterns, conditions)
  }
}
