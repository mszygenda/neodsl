package org.neodsl.db

import org.neodsl.queries.Query

/**
 * Execution engine responsible for processing queries on database
 */
trait ExecutionEngine {
  def execute(query: Query): List[ResultItem]
}
