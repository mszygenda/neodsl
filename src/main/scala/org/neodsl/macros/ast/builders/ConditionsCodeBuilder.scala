package org.neodsl.macros.ast.builders

import org.neodsl.macros.ast.Operators
import Operators._
import org.neodsl.queries.domain.Node

trait ConditionsCodeBuilder extends CodeBuilder {
  import context.universe._

  object typeNames {
    val ObjectPropertySelector = "org.neodsl.queries.components.conditions.ObjectPropertySelector"
    val SimpleValueSelector = "org.neodsl.queries.components.conditions.SimpleValueSelector"
    val PropertyComparison = "org.neodsl.queries.components.conditions.PropertyComparison"
    val Eq = "org.neodsl.queries.components.conditions.Eq"
    val Neq = "org.neodsl.queries.components.conditions.Neq"
    val Lt = "org.neodsl.queries.components.conditions.Lt"
    val Gt = "org.neodsl.queries.components.conditions.Gt"
    val Ge = "org.neodsl.queries.components.conditions.Ge"
    val Le = "org.neodsl.queries.components.conditions.Le"
    val And = "org.neodsl.queries.components.conditions.And"
    val Or = "org.neodsl.queries.components.conditions.Or"
    val Not = "org.neodsl.queries.components.conditions.Not"
  }

  protected def buildNot(condition: Tree): Tree = {
    applyCaseClass(typeNames.Not, List(condition))
  }

  protected def buildAnd(leftCondition: Tree, rightCondition: Tree): Tree = {
    applyCaseClass(typeNames.And, List(leftCondition, rightCondition))
  }

  protected def buildOr(leftCondition: Tree, rightCondition: Tree): Tree = {
    applyCaseClass(typeNames.Or, List(leftCondition, rightCondition))
  }

  protected def buildPropertyComparison(leftOp: Tree, operator: BinaryComparisonOperator, rightOp: Tree): Tree = {
    val operatorObj = buildCmpOperator(operator)

    buildPropertyComparison(leftOp, operatorObj, rightOp)
  }

  protected def buildPropertyComparison(leftSelector: Tree, operator: Tree, rightSelector: Tree): Tree = {
    applyCaseClass(typeNames.PropertyComparison, List(leftSelector, operator, rightSelector))
  }
  
  protected def buildSelector(operand: Tree): Tree = {
    operand match {
      case _: Literal => {
        buildSimpleValueSelector(operand)
      }
      case Apply(Select(predef, TermName("augmentString")), List(stringProp)) => {
        buildSelector(stringProp)
      }
      case Select(objectSelector, TermName(property)) => {
        val objectExpr = context.Expr[Any](objectSelector)

        if (objectExpr.actualType <:< typeOf[Node]) {
            buildObjectPropertySelector(objectSelector, property)
        } else {
            buildSimpleValueSelector(operand)
        }
      }
      case Ident(_) => {
        buildSimpleValueSelector(operand)
      }
    }
  }

  protected def buildSimpleValueSelector(operand: Tree): Tree = {
    applyTypedCaseClass(typeNames.SimpleValueSelector, List(operand.tpe), List(operand))
  }

  protected def buildObjectPropertySelector(objectSelector: Tree, property: String): Tree = {
    applyCaseClass(typeNames.ObjectPropertySelector, List(objectSelector, Literal(Constant(property))))
  }
  
  protected def buildCmpOperator(operator: BinaryComparisonOperator): Tree = {
    operator match {
      case Eq => {
        selectCaseObject(typeNames.Eq)
      }
      case Lt => {
        selectCaseObject(typeNames.Lt)
      }
      case Gt => {
        selectCaseObject(typeNames.Gt)
      }
      case Le => {
        selectCaseObject(typeNames.Le)
      }
      case Ge => {
        selectCaseObject(typeNames.Ge)
      }
      case Neq => {
        selectCaseObject(typeNames.Neq)
      }
    }
  }
}
