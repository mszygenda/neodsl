package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.{NameResolver, SelectQuery}

class SelectSerializer(query: SelectQuery, nameResolver: NameResolver) {
  def serialize: String = {
    val start = StartSerializer.serialize(query.startNodes, nameResolver)
    val patternMatching = MatchSerializer.serialize(query.patterns, nameResolver)
    val returns = ReturnSerializer.serialize(query.nodes, nameResolver, query.mapper)
    val where = WhereSerializer.serialize(query.condition, nameResolver)

    joinSections(start, patternMatching, where, returns)
  }

  private def joinSections(sections: String*): String = {
    val nonEmptySections = sections.filterNot(_.isEmpty)

    nonEmptySections.mkString("\n")
  }
}
