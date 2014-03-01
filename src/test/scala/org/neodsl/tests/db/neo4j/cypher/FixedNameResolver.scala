package org.neodsl.tests.db.neo4j.cypher

import org.neodsl.db.neo4j.cypher.NameResolver
import scala.collection.immutable.HashMap
import org.neodsl.queries.domain.Node

class FixedNameResolver(names: HashMap[Node[_], String]) extends NameResolver {
  def name(node: Node[_]): String = {
    names(node)
  }
}
