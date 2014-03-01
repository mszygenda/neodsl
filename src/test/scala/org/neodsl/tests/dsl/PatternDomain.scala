package org.neodsl.tests.dsl

import org.neodsl.dsl.domain.DomainObject

object PatternDomain {
  case class Person(name: String) extends DomainObject[Person] {
    val knows = -->[Person]("KNOWS")
    val wrote = -->[Comment]("WROTE")
    val likes = -->[Comment]("LIKES")
  }

  case class Comment(content: String) extends DomainObject[Comment] {
    val writtenBy = <--[Person]("WROTE")
  }
}
