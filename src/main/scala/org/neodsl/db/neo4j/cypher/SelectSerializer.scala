package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.SelectQuery

class SelectSerializer(query: SelectQuery, nameResolver: NameResolver) {
  def serialize: String = {
    val start = StartSerializer.serialize(query.startNodes, nameResolver)
    val patternMatching = MatchSerializer.serialize(query.patterns, nameResolver)
    val returns = ReturnSerializer.serialize(query.nodes, nameResolver, query.mapper)
    val where = WhereSerializer.serialize(query.condition, nameResolver) match {
      case "" => ""
      case str => "\n" + str
    }

    "%s\n%s%s\n%s" format (start, patternMatching, where, returns)
  }
}
