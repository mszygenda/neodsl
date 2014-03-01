package org.neodsl.db.neo4j.cypher

import org.neodsl.queries.domain.Node
import scala.collection.immutable.HashMap

class BasicNameResolver extends NameResolver {
  private var lastSuffix = 0
  private var namesAssigned = HashMap[Node[_], String]()

  def name(node: Node[_]): String = {
    namesAssigned.getOrElse(node, {
      assignName(node)
    })
  }

  private def assignName(node: Node[_]): String = {
    val name = nextName(node)

    namesAssigned = namesAssigned updated (node, name)

    name
  }

  private def nextName(node: Node[_]) = {
    if (node.id.isEmpty) {
      val suffix = incrementSuffix

      "n_" + suffix
    } else {
      "id_" + node.id.get
    }
  }

  private def incrementSuffix: Long = {
    lastSuffix += 1

    lastSuffix
  }
}
