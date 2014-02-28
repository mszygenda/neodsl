package org.neodsl.queries.components.conditions

case class PropertyComparison(leftOp: PropertySelector, op: ComparisonOperator, rightOp: PropertySelector) extends Condition
