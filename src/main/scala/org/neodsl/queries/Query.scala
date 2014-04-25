package org.neodsl.queries

abstract class Query {
  lazy val nameResolver: NameResolver = new BasicNameResolver
}