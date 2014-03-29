package org.neodsl.queries

import org.neodsl.queries.BasicNameResolver

abstract class Query {
  def nameResolver: NameResolver = new BasicNameResolver
}