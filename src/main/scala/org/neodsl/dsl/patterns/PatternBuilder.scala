package org.neodsl.dsl.patterns

import org.neodsl.queries.domain.TypedNode
import scala.Null
import scala.collection.immutable.HashMap
import org.neodsl.queries.components.patterns.{NodePattern, RelationPattern, PatternTripple}
import org.neodsl.queries.components.patterns.compositions.NoPatterns
import org.neodsl.reflection.ObjectFactory

class PatternBuilder[T >: Null <: TypedNode[T], U >: Null <: TypedNode[U]](val pattern: PatternTripple[T, U])
{
  def apply[V >: Null <: TypedNode[V]](buildPattern: U => PatternTripple[U, V])(implicit manifest: Manifest[U]) = {
    val obj = PatternBuilder.createInstance[U]

    pattern.copy(tail = buildPattern(obj))
  }

  def apply(connections: Range) = {
    val PatternTripple(_, RelationPattern(relation, _), _) = pattern
    val alteredRelationPattern = RelationPattern(relation, connections)

    new PatternBuilder(pattern.copy(relation = alteredRelationPattern))
  }

  def apply(nextNode: U) = {
    pattern.copy(tail = NodePattern(nextNode))
  }

  def apply[V >: Null <: TypedNode[V]](buildPattern: => PatternTripple[U, V]) = {
    pattern.copy(tail = buildPattern)
  }

  def apply[V >: Null <: TypedNode[V]](nextPattern: PatternTripple[U, V]) = {
    pattern.copy(tail = nextPattern)
  }

  def and[W >: Null <: TypedNode[W], X >: Null <: TypedNode[X]](buildPattern: => PatternTripple[W, X]) = {
    NoPatterns and pattern and buildPattern
  }
}

object PatternBuilder {
  def sth[T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]) = something[T]

  def something[T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]) = anonymous[T]

  def sbd[T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]) = someone[T]

  def some[T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]) = someone[T]

  def someone[T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]) = anonymous[T]

  def p[T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]) = anonymous[T]

  def placeholder[T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]) = anonymous[T]

  def anonymous[T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]) = {
    createInstance[T]
  }

  private def createInstance[T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]): T = {
    ObjectFactory.createPlaceholderObject[T]
  }
}