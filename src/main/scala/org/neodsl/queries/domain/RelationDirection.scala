package org.neodsl.queries.domain

trait RelationDirection

object --> extends RelationDirection {
  override def toString = "-->"
}

object <-- extends RelationDirection {
  override def toString = "<--"
}

object -- extends RelationDirection {
  override def toString = "--"
}