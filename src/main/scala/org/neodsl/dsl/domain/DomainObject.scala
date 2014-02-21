package org.neodsl.dsl.domain

import org.neodsl.dsl.patterns._
import org.neodsl.queries.domain.{--> => Right, <-- => Left, Relation, Node}
import org.neodsl.queries.components.patterns.{UndefinedNodePattern, RelationPattern, NodePattern, PatternTripple}

trait DomainObject[T >: Null <: DomainObject[T]] extends Node[T] {
  def -->[U >: Null <: Node[U]](name: String) = {
    new PatternBuilder[T, U](PatternTripple[T, U](NodePattern(this), RelationPattern(Relation(name, Right), 1 to 1), UndefinedNodePattern[U]))
  }

  def <--[U >: Null <: Node[U]](name: String) = {
    new PatternBuilder[T, U](PatternTripple[T, U](NodePattern(this), RelationPattern(Relation(name, Left), 1 to 1), UndefinedNodePattern[U]))
  }
}
