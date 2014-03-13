package org.neodsl.tests.dsl

import org.neodsl.dsl.domain.DomainObject

object PatternDomain {
  case class Person(name: String) extends DomainObject[Person] {
    def this() = this(null)

    val knows = -->[Person]("KNOWS")
    val wrote = -->[Comment]("WROTE")
    val likes = -->[Comment]("LIKES")
  }

  class AgingPerson(name: String, val age: Int) extends Person(name)

  class PersonWithId(override val id: Option[Long], name: String) extends Person(name)

  case class Comment(content: String) extends DomainObject[Comment] {
    def this() = this(null)

    val writtenBy = <--[Person]("WROTE")
  }
}
