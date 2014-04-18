package org.neodsl.tests

import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.mock.MockitoSugar
import org.neodsl.db.{ExecutionEngine, ResultItem}
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.neodsl.queries.Query

trait BaseTests extends FlatSpec with Matchers with MockitoSugar {
  protected def mockResultItem(hashMap: Map[String, Any]) = {
    val resultItemMock = mock[ResultItem]

    when(resultItemMock.getObjectMap(anyString())).thenReturn(hashMap)

    resultItemMock
  }

  protected def mockExecutionEngine(results: List[ResultItem]) = {
    val execEngineMock = mock[ExecutionEngine]

    when(execEngineMock.execute(any[Query])).thenReturn(results)

    execEngineMock
  }
}
