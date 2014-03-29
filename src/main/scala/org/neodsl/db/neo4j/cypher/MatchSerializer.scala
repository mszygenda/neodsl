package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And, PatternComposition}
import org.neodsl.queries.NameResolver

object MatchSerializer {
  def serialize(patternComp: PatternComposition, resolver: NameResolver): String = {
    "%s %s" format (CypherKeywords.MATCH, serializeComposition(patternComp, resolver))
  }

  private def serializeComposition(patternComp: PatternComposition, resolver: NameResolver): String = {
    patternComp match {
      case And(pattern, tail) => {
        val fstPattern = PatternSerializer.serialize(pattern, resolver)
        val tailSerialized = serializeComposition(tail, resolver)

        tailSerialized match {
          case "" => fstPattern
          case _ => fstPattern + "," + tailSerialized
        }
      }
      case NoPatterns => {
        ""
      }
    }
  }
}
