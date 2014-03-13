package org.neodsl.queries.components.patterns.compositions

import org.neodsl.queries.domain._
import org.neodsl.queries.components.patterns.{PatternTripple}

case class And[U >: Null <: TypedNode[U], V >: Null <: TypedNode[V]](head: PatternTripple[U, V], tail: PatternComposition) extends PatternComposition {

}




