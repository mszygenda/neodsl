package org.neodsl.queries.components.conditions

trait Condition {
  def and(other: Condition) = {
    And(this, other)
  }

  def or(other: Condition) = {
    Or(this, other)
  }
}

case object NoConditions extends Condition

case class And(fst: Condition, snd: Condition) extends Condition

case class Or(fst: Condition, snd: Condition) extends Condition

case class Not(cond: Condition) extends Condition