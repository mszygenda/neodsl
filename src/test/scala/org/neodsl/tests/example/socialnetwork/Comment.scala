package org.neodsl.tests.example.socialnetwork

import org.neodsl.dsl.domain.DomainObject
import org.neodsl.dsl.patterns.PatternBuilder._
import Database._

class Comment extends DomainObject[Comment] with Likeable {
  val content: String = null

  val writtenBy = <--[Person]("WROTE")
}

object Comment {
  def authorsOf(comment: Comment): Seq[Person] = {
    val author = some[Person]
    val q = comment writtenBy author select(author)

    q.exec(a => a)
  }
}
