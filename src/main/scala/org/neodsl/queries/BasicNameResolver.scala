package org.neodsl.queries

import org.neodsl.queries.domain.Node
import scala.collection.immutable.HashMap

class BasicNameResolver extends NameResolver {
  private var lastSuffix = 0
  private var namesAssigned = HashMap[Node, String]()

  def name(node: Node): String = {
    namesAssigned.getOrElse(node, {
      assignName(node)
    })
  }

  private def assignName(node: Node): String = {
    val name = nextName(node)

    namesAssigned = namesAssigned updated (node, name)

    name
  }

  private def nextName(node: Node) = {
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
