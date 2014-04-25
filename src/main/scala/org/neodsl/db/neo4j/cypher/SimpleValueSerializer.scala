package org.neodsl.db.neo4j.cypher

import org.neodsl.db.neo4j.cypher.exceptions.UnsupportedSimpleValueTypeException

object SimpleValueSerializer {
  def serialize(value: Any) = {
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
