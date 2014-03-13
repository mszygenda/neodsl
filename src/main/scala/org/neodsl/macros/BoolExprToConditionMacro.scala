package org.neodsl.macros

import scala.reflect.macros.Context
import org.neodsl.macros.ast.Operators
import Operators._
import org.neodsl.macros.ast.builders.ConditionsCodeBuilder
import org.neodsl.macros.ast.transformers.BooleanExpressionTransformer

class BoolExprToConditionMacro[C <: Context](val context: C) extends BooleanExpressionTransformer with ConditionsCodeBuilder {
  import context._

  override def onComparison(leftOp: Tree, cmpOperator: BinaryComparisonOperator, rightOp: Tree): Tree = {
    val leftSelector = buildSelector(leftOp)
    val rightSelector = buildSelector(rightOp)

    buildPropertyComparison(leftSelector, cmpOperator, rightSelector)
  }

  override def onUnaryLogicalOperation(leftOp: Tree, operator: UnaryLogicalOperator): Tree = {
    operator match {
      case Not => {
        buildNot(leftOp)
      }
    }
  }

  override def onBinaryLogicalOperation(leftOp: Tree, operator: BinaryLogicalOperator, rightOp: Tree): Tree = {
    operator match {
      case Or => {
        buildOr(leftOp, rightOp)
      }
      case And => {
        buildAnd(leftOp, rightOp)
      }
    }
  }
}
