package org.neodsl.macros.ast

object Operators {
  trait Operator
  trait LogicalOperator extends Operator
  trait BinaryComparisonOperator extends Operator
  trait UnaryLogicalOperator extends LogicalOperator
  trait BinaryLogicalOperator extends LogicalOperator

  case object Eq extends BinaryComparisonOperator
  case object Neq extends BinaryComparisonOperator
  case object Lt extends BinaryComparisonOperator
  case object Le extends BinaryComparisonOperator
  case object Gt extends BinaryComparisonOperator
  case object Ge extends BinaryComparisonOperator

  case object And extends BinaryLogicalOperator
  case object Or extends BinaryLogicalOperator

  case object Not extends UnaryLogicalOperator

  case class CustomOperator(name: String) extends Operator

  def scalaLiteralToOperator(lit: String) = {
    lit match {
      case "$amp$amp" => {
        And
      }
      case "$bar$bar" => {
        Or
      }
      case "$eq$eq" => {
        Eq
      }
      case "$lt" | "$less" => {
        Lt
      }
      case "$gt" | "$greater" => {
        Gt
      }
      case "$le" | "$less$eq" => {
        Le
      }
      case "$ge" | "$greater$eq" => {
        Ge
      }
      case "$bang$eq" => {
        Neq
      }
      case "unary_$bang" => {
        Not
      }
    }
  }
}
