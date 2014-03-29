package org.neodsl.queries

abstract class Query {
  def nameResolver: NameResolver = new BasicNameResolver
}