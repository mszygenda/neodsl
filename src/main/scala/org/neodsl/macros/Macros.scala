package org.neodsl.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context
import org.neodsl.queries.components.conditions.Condition

object Macros {
  implicit def boolExprToCondition(boolExpr: Boolean): Condition = macro boolExprToConditionImpl

  def show(expr: Any) = macro showImpl

  def boolExprToConditionImpl(c: Context)(boolExpr: c.Expr[Boolean]): c.Expr[Condition] = {
    val transformer = new BoolExprToConditionMacro[c.type](c)

    c.Expr[Condition](transformer.transformBooleanExpr(boolExpr.tree.asInstanceOf[transformer.context.Tree]))
  }

  def showImpl(c: Context)(expr: c.Expr[Any]) = {
    println(expr)

    expr
  }
}
