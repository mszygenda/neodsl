package org.neodsl.dsl.patterns

import org.neodsl.dsl.domain._

trait PatternConjunction {
  def and[U >: Null <: Node[U], V >: Null <: Node[V]](buildPattern: => PatternTrippleElement[U, V]): PatternConjunction
  def toList: List[Pattern]
}

case class PatternAnd[U >: Null <: Node[U], V >: Null <: Node[V]](head: PatternTrippleElement[U, V], tail: PatternConjunction) extends PatternConjunction {
  def and[U >: Null <: Node[U], V >: Null <: Node[V]](pattern: => PatternTrippleElement[U, V]) = {
    PatternAnd(pattern, this)
  }

  def toList: List[Pattern] = {
    head :: tail.toList
  }
}

case object NoPatterns extends PatternConjunction {
  def and[U >: Null <: Node[U], V >: Null <: Node[V]](buildPattern: => PatternTrippleElement[U, V]): PatternConjunction = {
    PatternAnd(buildPattern, this)
  }

  def toList: List[Pattern] = Nil
}


