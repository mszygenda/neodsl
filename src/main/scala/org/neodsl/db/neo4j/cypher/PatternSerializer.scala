package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.components.patterns._
import org.neodsl.queries.domain._
import CypherKeywords._
import org.neodsl.queries.NameResolver

object PatternSerializer {
  def serialize(pattern: Pattern, resolver: NameResolver): String = {
    pattern match {
      case NodePattern(node) => {
        serializeNode(node, resolver)
      }
      case PatternTriple(node, RelationPattern(rel, conn), tail) => {
        serializeNode(node.node, resolver) + serializeRelation(rel, conn) + serialize(tail, resolver)
      }
    }
  }

  private def serializeNode(node: TypedNode[_], resolver: NameResolver) = {
    resolver.name(node)
  }

  private def serializeRelation(rel: Relation[_, _], conn: Range) = {
    rel.direction match {
      case <-- => {
        namedRelLeftOpen + rel.name + connections(conn) + namedRelLeftClose
      }
      case --> => {
        namedRelRightOpen + rel.name + connections(conn) + namedRelRightClose
      }
      case -- => {
        namedRelNoDirOpen + rel.name + connections(conn) + namedRelNoDirClose
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