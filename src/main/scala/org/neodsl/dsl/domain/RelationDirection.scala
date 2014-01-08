package org.neodsl.dsl.domain

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