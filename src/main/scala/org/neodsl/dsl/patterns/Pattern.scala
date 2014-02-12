package org.neodsl.dsl.patterns

import org.neodsl.dsl.domain.Node
import scala.Null
import scala.collection.immutable.HashMap
import org.neodsl.reflection.proxy.ObjectFactory

case class Pattern[T >: Null <: Node[T], U >: Null <: Node[U]](node: NodePatternElement[T], relation: RelationPatternElement[T, U], tail: PatternElement[U])
extends PatternElement[T]
{
  def apply[V >: Null <: Node[V]](buildPattern: U => Pattern[U, V])(implicit manifest: Manifest[U]) = {
    val obj = createInstance[U]

    Pattern(node, relation, buildPattern(obj))
  }

  def apply(nextNode: U) = {
    Pattern(node, relation, NodePatternElement(nextNode))
  }

  // idea:
  def apply[V >: Null <: Node[V]](buildPattern: => Pattern[U, V]) = {
    Pattern(node, relation, buildPattern)
  }

  def apply[V >: Null <: Node[V]](nextPattern: Pattern[U, V]) = {
    Pattern(node, relation, nextPattern)
  }

  private def createInstance[T >: Null](implicit manifest: Manifest[T]): T = {
    ObjectFactory.createObject[T](HashMap())
  }
}