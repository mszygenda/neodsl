package org.neodsl.queries

import org.neodsl.queries.components.patterns.compositions.PatternComposition
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.queries.domain.Node

abstract class SelectQuery(val nodes: List[Node[_]], val patterns: PatternComposition, val condition: Condition) extends Query
