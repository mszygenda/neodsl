package org.neodsl.queries.components.conditions

import org.neodsl.queries.domain.Node

case class ObjectPropertySelector(obj: Node, property: String) extends PropertySelector
