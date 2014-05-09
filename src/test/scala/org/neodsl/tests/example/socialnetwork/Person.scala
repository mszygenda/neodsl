package org.neodsl.tests.example.socialnetwork

import org.neodsl.dsl.domain.{DomainCompanionObject, DomainObject}
import org.neodsl.dsl.patterns.PatternBuilder._
import Database._

case class Person(name: String) extends DomainObject[Person] {
  val knows = --[Person]("KNOWS")
  val likes = -->[Likeable]("LIKES")
  val wrote = -->[Comment]("WROTE")
}

object Person extends DomainCompanionObject[Person] {
  def friends(person: Person): Seq[Person] = {
    val friend = p[Person]
    val q = person knows friend select(friend)

    q.exec(p => p)
  }

  def writtenPosts(person: Person): Seq[Comment] = {
    val comment = p[Comment]
    val q = person wrote comment select(comment)

    q.exec(c => c)
  }

  def fof(person: Person): Seq[Person] = {
    val fof = p[Person]
    val q = { person knows { some[Person] knows fof } } select(fof)

    q.exec(f => f)
  }
}