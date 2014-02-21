package org.neodsl.dsl.patterns

import org.neodsl.queries.domain.Node
import scala.Null
import scala.collection.immutable.HashMap
import org.neodsl.reflection.proxy.ObjectFactory
import org.neodsl.queries.components.patterns.{NodePattern, RelationPattern, PatternTripple}
import org.neodsl.queries.components.patterns.compositions.NoPatterns

class PatternBuilder[T >: Null <: Node[T], U >: Null <: Node[U]](val pattern: PatternTripple[T, U])
{
  def apply[V >: Null <: Node[V]](buildPattern: U => PatternTripple[U, V])(implicit manifest: Manifest[U]) = {
    val obj = createInstance[U]

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

  def apply[V >: Null <: Node[V]](buildPattern: => PatternTripple[U, V]) = {
    pattern.copy(tail = buildPattern)
  }

  def apply[V >: Null <: Node[V]](nextPattern: PatternTripple[U, V]) = {
    pattern.copy(tail = nextPattern)
  }

  private def createInstance[T >: Null](implicit manifest: Manifest[T]): T = {
    ObjectFactory.createObject[T](HashMap())
  }

  def and[W >: Null <: Node[W], X >: Null <: Node[X]](buildPattern: => PatternTripple[W, X]) = {
    NoPatterns and pattern and buildPattern
  }
}