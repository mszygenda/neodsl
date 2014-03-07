package org.neodsl.queries.components.conditions

import org.neodsl.queries.domain.Node

case class ObjectPropertySelector[T >: Null <: Node[T]](obj: Node[T], property: String) extends PropertySelector
