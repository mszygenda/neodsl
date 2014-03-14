package org.neodsl.macros.ast.transformers

import scala.reflect.macros.Context
import org.neodsl.macros.ast.Operators
import Operators._

trait BooleanExpressionTransformer {
  val context: Context
  import context.universe._

  def transformBooleanExpr(boolTree: Tree): Tree = {
    boolTree match {
      case Select(leftOp, TermName(operator)) => {
        transformUnaryOperationTree(leftOp, scalaLiteralToOperator(operator))
      }
      case Apply(Select(leftOp, TermName(operator)), List(rightOp)) => {
        transformBinaryOperationTree(leftOp, scalaLiteralToOperator(operator), rightOp)
      }
    }
  }

  private def transformUnaryOperationTree(leftOp: Tree, operator: Operator): Tree = {
    operator match {
      case unaryOp: UnaryLogicalOperator =>
        onUnaryLogicalOperation(leftOp, unaryOp)
      case customOp: CustomOperator => {
        onCustomUnaryOperator(leftOp, customOp)
      }
    }
  }

  private def transformBinaryOperationTree(leftOp: Tree, operator: Operator, rightOp: Tree): Tree = {
    operator match {
      case binLogicalOp: BinaryLogicalOperator => {
        val leftOpTransformed = transformBooleanExpr(leftOp)
        val rightOpTransformed = transformBooleanExpr(rightOp)

        onBinaryLogicalOperation(leftOpTransformed, binLogicalOp, rightOpTransformed)
      }
      case binCmpOp: BinaryComparisonOperator => {
        onComparison(leftOp, binCmpOp, rightOp)
      }
    }
  }

  def onBinaryLogicalOperation(leftOp: Tree, operator: BinaryLogicalOperator, rightOp: Tree): Tree

  def onUnaryLogicalOperation(leftOp: Tree, operator: UnaryLogicalOperator): Tree

  def onComparison(leftOp: Tree, cmpOperator: BinaryComparisonOperator, rightOp: Tree): Tree

  def onCustomUnaryOperator(leftOp: Tree, customOp: CustomOperator): Tree
}
