package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.components.conditions._
import org.neodsl.queries.components.conditions.Or
import org.neodsl.queries.components.conditions.And
import org.neodsl.db.neo4j.cypher.exceptions.UnsupportedSimpleValueTypeException

object WhereSerializer {
  def serialize(conditions: Condition, resolver: NameResolver) = {
    conditions match {
      case NoConditions => ""
      case _ => {
        "%s %s" format(CypherKeywords.WHERE, serializeConditions(conditions, resolver))
      }
    }
  }

  private def serializeConditions(conditions: Condition, resolver: NameResolver): String = {
    conditions match {
      case And(fst, snd) => {
        "(%s AND %s)" format(serializeConditions(fst, resolver), serializeConditions(snd, resolver))
      }
      case Or(fst, snd) => {
        "(%s OR %s)" format(serializeConditions(fst, resolver), serializeConditions(snd, resolver))
      }
      case Not(cond) => {
        "NOT(%s)" format serializeConditions(cond, resolver)
      }
      case PropertyComparison(leftOp, op, rightOp) => {
        "%s %s %s" format(serializeSelector(leftOp, resolver), serializeOperator(op), serializeSelector(rightOp, resolver))
      }
      case NoConditions => {
        ""
      }
    }
  }

  def serializeSelector(propSelector: PropertySelector, resolver: NameResolver): String = {
    propSelector match {
      case ObjectPropertySelector(obj, property) => {
        "%s.%s" format (resolver.name(obj), property)
      }
      case SimpleValueSelector(value) => {
        value match {
          case _: String | _: Char => {
            "'%s'" format (value)
          }
          case _: Long | _: Int | _: Double | _: Short | _: Byte | _: Float => {
            "%s" format(value)
          }
          case _ => {
            throw new UnsupportedSimpleValueTypeException(value.getClass)
          }
        }
      }
    }
  }

  def serializeOperator(operator: ComparisonOperator): String = {
    operator match {
      case Eq => {
        CypherKeywords.eqOp
      }
      case Le => {
        CypherKeywords.leOp
      }
      case Ge => {
        CypherKeywords.geOp
      }
      case Lt => {
        CypherKeywords.ltOp
      }
      case Gt => {
        CypherKeywords.gtOp
      }
      case Neq => {
        CypherKeywords.neqOp
      }
    }
  }
}