package org.neodsl.dsl.domain

import org.neodsl.queries.domain.TypedNode
import org.neodsl.reflection.{NodeObjectMapper, ObjectMapper}
import scala.collection.immutable.HashMap
import org.neodsl.queries.components.conditions.NoConditions
import org.neodsl.queries.components.patterns.compositions.NoPatterns
import org.neodsl.dsl.queries.SelectQuery1
import org.neodsl.db.ExecutionEngine

abstract class DomainCompanionObject[T >: Null <: TypedNode[T]](implicit manifest: Manifest[T]) {
  protected val mapper: ObjectMapper = new NodeObjectMapper

  def find(id: Long)(implicit engine: ExecutionEngine): Option[T] = {
    val node = mapper.mapToObject[T](HashMap("id" -> id))(manifest)
    val query = new SelectQuery1[T](List(node), NoPatterns, NoConditions)

    query.exec(node => node).toList.headOption
  }
}
