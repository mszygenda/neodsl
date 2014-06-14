package org.neodsl.macros.ast.builders

import scala.reflect.macros.blackbox.Context

trait CodeBuilder {
  val context: Context
  import context.universe._

  protected def literal(value: Any) = {
    Literal(Constant(value))
  }

  protected def selectCaseObject(name: String) = {
    selectTerm(name)
  }

  protected def applyCaseClass(className: String, args: List[Tree]) = {
    Apply(Select(selectTerm(className), TermName("apply")), args)
  }

  protected def applyTypedCaseClass(className: String, typeArgs: List[Type], args: List[Tree]) = {
    Apply(TypeApply(Select(selectTerm(className), TermName("apply")), typeArgs.map(TypeTree(_))), args)
  }

  protected def selectTerm(fullyQualifiedTerm: String) = {
    val names = fullyQualifiedTerm.split('.').toList

    def select(terms: List[String]): Tree = {
      terms match {
        case leastImportant :: Nil => {
          Ident(TermName(leastImportant))
        }
        case qualifier :: tail => {
          Select(select(tail), TermName(qualifier))
        }
        case Nil => {
          throw new Exception("Invalid term name")
        }
      }
    }

    select(names.reverse)
  }
}