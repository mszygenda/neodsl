package org.neodsl.db.neo4j.cypher

import org.neodsl.queries._

object CypherSerializer {
  def serialize(query: Query) = {
    query match {
      case select: SelectQuery => {
        new SelectSerializer(select, new BasicNameResolver).serialize
      }
    }
  }
}
