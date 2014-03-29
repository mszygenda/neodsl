package org.neodsl.queries

import org.neodsl.queries.domain._

trait NameResolver {
  def name(node: Node): String
}
