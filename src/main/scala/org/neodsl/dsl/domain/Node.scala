package org.neodsl.dsl.domain

import org.neodsl.dsl.domain.{ --> => Right, <-- => Left }
import org.neodsl.dsl.patterns._
import org.neodsl.dsl.patterns.PatternTrippleElement
import org.neodsl.dsl.patterns.RelationElement
import org.neodsl.dsl.patterns.NodeElement
import org.neodsl.dsl.domain.Relation

trait Node[T >: Null <: Node[T]] extends AnyRef {
  def -->[U >: Null <: Node[U]](name: String) = {
    PatternTrippleElement[T, U](NodeElement[T](this), RelationElement[T, U](Relation(name, Right), 1 to 1), UndefinedPatternElement[U])
  }

  def <--[U >: Null <: Node[U]](name: String) = {
    PatternTrippleElement[T, U](NodeElement[T](this), RelationElement[T, U](Relation(name, Left), 1 to 1), UndefinedPatternElement[U])
  }
}
