package org.neodsl.tests.patterns.patterns

import org.neodsl.dsl.domain.Node

object PatternDomain {
  case class Person(name: String) extends Node[Person] {
    val knows = -->[Person]("KNOWS")
    val wrote = -->[Comment]("WROTE")
    val likes = -->[Comment]("LIKES")
  }

  case class Comment(content: String) extends Node[Comment] {

  }
}
