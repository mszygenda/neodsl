package org.neodsl.dsl.patterns

import org.neodsl.queries.domain.{ TypedNode, Node }
import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And}
import org.neodsl.queries.components.patterns.PatternTriple
import org.neodsl.dsl.Selfie
import org.neodsl.queries.components.conditions.{NoConditions, Condition}
import org.neodsl.dsl.queries._

trait PatternTripleExtensions[T >: Null <: TypedNode[T], U >: Null <: TypedNode[U]] extends Selfie[PatternTriple[T, U]] {
  def and[W >: Null <: TypedNode[W], X >: Null <: TypedNode[X]](another: PatternTriple[W, X]) = {
    And(another, And(self, NoPatterns))
  }

  def where(condition: => Condition) = {
    new QueryBuilder(And(self, NoPatterns), condition)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E], F >: Null <: TypedNode[F], G >: Null <: TypedNode[G], H >: Null <: TypedNode[H], I >: Null <: TypedNode[I], J >: Null <: TypedNode[J]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E, nodeF: F, nodeG: G, nodeH: H, nodeI: I, nodeJ: J) = {
    new SelectQuery10[A, B, C, D, E, F, G, H, I, J](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeH, nodeI, nodeJ), And(self, NoPatterns), NoConditions)
  }

  def select[A >: Null <: TypedNode[A]](nodeA: A) = {
    new SelectQuery1[A](List[Node](nodeA), And(self, NoPatterns), NoConditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B]](nodeA: A, nodeB: B) = {
    new SelectQuery2[A, B](List[Node](nodeA, nodeB), And(self, NoPatterns), NoConditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C]](nodeA: A, nodeB: B, nodeC: C) = {
    new SelectQuery3[A, B, C](List[Node](nodeA, nodeB, nodeC), And(self, NoPatterns), NoConditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D]](nodeA: A, nodeB: B, nodeC: C, nodeD: D) = {
    new SelectQuery4[A, B, C, D](List[Node](nodeA, nodeB, nodeC, nodeD), And(self, NoPatterns), NoConditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E) = {
    new SelectQuery5[A, B, C, D, E](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE), And(self, NoPatterns), NoConditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E], F >: Null <: TypedNode[F]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E, nodeF: F) = {
    new SelectQuery6[A, B, C, D, E, F](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE, nodeF), And(self, NoPatterns), NoConditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E], F >: Null <: TypedNode[F], G >: Null <: TypedNode[G]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E, nodeF: F, nodeG: G) = {
    new SelectQuery7[A, B, C, D, E, F, G](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG), And(self, NoPatterns), NoConditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E], F >: Null <: TypedNode[F], G >: Null <: TypedNode[G], H >: Null <: TypedNode[H]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E, nodeF: F, nodeG: G, nodeH: H) = {
    new SelectQuery8[A, B, C, D, E, F, G, H](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeH), And(self, NoPatterns), NoConditions)
  }

  def select[A >: Null <: TypedNode[A], B >: Null <: TypedNode[B], C >: Null <: TypedNode[C], D >: Null <: TypedNode[D], E >: Null <: TypedNode[E], F >: Null <: TypedNode[F], G >: Null <: TypedNode[G], H >: Null <: TypedNode[H], I >: Null <: TypedNode[I]](nodeA: A, nodeB: B, nodeC: C, nodeD: D, nodeE: E, nodeF: F, nodeG: G, nodeH: H, nodeI: I) = {
    new SelectQuery9[A, B, C, D, E, F, G, H, I](List[Node](nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeH, nodeI), And(self, NoPatterns), NoConditions)
  }
}
