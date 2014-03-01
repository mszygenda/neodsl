package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.domain._

trait NameResolver {
  def name(node: Node[_]): String
}
