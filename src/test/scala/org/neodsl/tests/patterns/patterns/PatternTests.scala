package org.neodsl.reflection.proxy.tests.dsl.patterns

import org.neodsl.dsl.domain.Node
import org.neodsl.tests.BaseTests

trait PatternTests extends BaseTests {
  val query = {
    val john = Person("John")
    val matthew = Person("Matthew")
    val wiseComment = Comment("Wise Comment")

    val patt = john knows (fof =>
      fof knows (
        _ wrote wiseComment
      )
    )

    val sndPatt = john knows (
      _ knows {
        matthew likes wiseComment
      }
    )

    println(patt)
    println(sndPatt)
  }
}

case class Person(name: String) extends Node[Person] {
  val knows = -->[Person]("KNOWS")
  val wrote = -->[Comment]("WROTE")
  val likes = -->[Comment]("LIKES")
}

case class Comment(content: String) extends Node[Comment] {

}