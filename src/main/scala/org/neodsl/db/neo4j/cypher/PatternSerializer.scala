package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.components.patterns._
import org.neodsl.queries.domain._
import CypherKeywords._

object PatternSerializer {
  def serialize(pattern: Pattern): String = {
    pattern match {
      case NodePattern(node) => {
        serializeNode(node)
      }
      case PatternTripple(node, RelationPattern(rel, conn), tail) => {
        serializeNode(node.node) + serializeRelation(rel, conn) + serialize(tail)
      }
    }
  }

  private def serializeNode(node: Node[_]) = {
    "node"
  }

  private def serializeRelation(rel: Relation[_, _], conn: Range) = {
    rel.direction match {
      case <-- => {
        namedRelLeftOpen + rel.name + connections(conn) + namedRelLeftClose
      }
      case --> => {
        namedRelRightOpen + rel.name + connections(conn) + namedRelRightClose
      }
    }
  }

  private def connections(conn: Range) = {
    if (conn.start == conn.end && conn.start == 1) {
      ""
    } else {
      "*%d..%d" format (conn.start, conn.end)
    }
  }
}