package org.neodsl.dsl.patterns

import org.neodsl.dsl.domain.Node
import scala.Null
import scala.collection.immutable.HashMap
import org.neodsl.reflection.proxy.ObjectFactory

case class PatternTrippleElement[T >: Null <: Node[T], U >: Null <: Node[U]](node: NodeElement[T], relation: RelationElement[T, U], tail: PatternElement[U])
extends PatternElement[T]
{
  def apply[V >: Null <: Node[V]](buildPattern: U => PatternTrippleElement[U, V])(implicit manifest: Manifest[U]) = {
    val obj = createInstance[U]

    PatternTrippleElement(node, relation, buildPattern(obj))
  }

  def apply(nextNode: U) = {
    PatternTrippleElement(node, relation, NodeElement(nextNode))
  }

  def apply[V >: Null <: Node[V]](buildPattern: => PatternTrippleElement[U, V]) = {
    PatternTrippleElement(node, relation, buildPattern)
  }

  def apply[V >: Null <: Node[V]](nextPattern: PatternTrippleElement[U, V]) = {
    PatternTrippleElement(node, relation, nextPattern)
  }

  private def createInstance[T >: Null](implicit manifest: Manifest[T]): T = {
    ObjectFactory.createObject[T](HashMap())
  }

  def and[W >: Null <: Node[W], X >: Null <: Node[X]](buildPattern: => PatternTrippleElement[W, X]) = {
    NoPatterns and this and buildPattern
  }
}