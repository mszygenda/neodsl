package org.neodsl.dsl.domain

import org.neodsl.dsl.patterns._
import org.neodsl.queries.domain.{--> => Right, <-- => Left, Relation, TypedNode}
import org.neodsl.queries.components.patterns.{UndefinedNodePattern, RelationPattern, NodePattern, PatternTriple}

abstract class DomainObject[T >: Null <: DomainObject[T]](implicit manifest: Manifest[T]) extends TypedNode[T] {
  val id: Option[Long] = None

  def -->[U >: Null <: TypedNode[U]](name: String) = {
    new PatternBuilder[T, U](PatternTriple[T, U](NodePattern(this), RelationPattern(Relation(name, Right), 1 to 1), UndefinedNodePattern[U]))
  }

  def <--[U >: Null <: TypedNode[U]](name: String) = {
    new PatternBuilder[T, U](PatternTriple[T, U](NodePattern(this), RelationPattern(Relation(name, Left), 1 to 1), UndefinedNodePattern[U]))
  }
}
