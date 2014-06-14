package org.neodsl.macros

import scala.reflect.macros.blackbox.Context
import org.neodsl.queries.components.conditions.Condition

object Macros {
  implicit def boolExprToCondition(boolExpr: Boolean): Condition = macro boolExprToConditionImpl

  def boolExprToConditionImpl(c: Context)(boolExpr: c.Expr[Boolean]): c.Expr[Condition] = {
    val transformer = new BoolExprToConditionMacro[c.type](c)

    c.Expr[Condition](transformer.transformBooleanExpr(boolExpr.tree.asInstanceOf[transformer.context.Tree]))
  }
}
