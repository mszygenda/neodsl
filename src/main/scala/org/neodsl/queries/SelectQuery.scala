package org.neodsl.queries

import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And, PatternComposition}
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.queries.domain.{Node, TypedNode}
import org.neodsl.reflection.{NodeObjectMapper, ObjectMapper}
import org.neodsl.db.ExecutionEngine
import scala.collection.immutable.HashMap

case class SelectQuery(nodes: List[Node], patterns: PatternComposition, condition: Condition) extends Query {
  def mapper: ObjectMapper = new NodeObjectMapper

  def exec(implicit engine: ExecutionEngine): Seq[Map[String, Node]] = {
    val results = engine.execute(this)

    results.map(row => {
      val objects = for (node <- nodes) yield {
        val nodeName = nameResolver.name(node)

        nodeName -> mapper.mapToObject(node.nodeType)(row.getObjectMap(nodeName)).asInstanceOf[Node]
      }

      objects.toMap[String, Node]
    })
  }

  def startNodes = {
    allNodes.filter(_.id match {
      case Some(_) => true
      case None => false
    })
  }

  private def allNodes = {
    uniqueObjects(nodesOf(patterns) ++ nodes)
  }

  private def uniqueObjects(nodes: List[Node]): List[Node] = {
    nodes match {
      case node :: tail => {
        node :: uniqueObjects(tail.filterNot(_.eq(node)))
      }
      case Nil => {
        Nil
      }
    }
  }

  private def nodesOf(patternComposition: PatternComposition): List[Node] = {
    patternComposition match {
      case And(pattern, tail) => {
        pattern.nodes ++ nodesOf(tail)
      }
      case NoPatterns => {
        Nil
      }
    }
  }
}
