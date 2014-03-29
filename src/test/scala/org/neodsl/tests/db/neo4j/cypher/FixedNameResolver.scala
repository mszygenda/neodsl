package org.neodsl.tests.db.neo4j.cypher

import scala.collection.immutable.HashMap
import org.neodsl.queries.domain.Node
import org.neodsl.queries.NameResolver

class FixedNameResolver(names: HashMap[Node, String]) extends NameResolver {
  def name(node: Node): String = {
    names(node)
  }
}
