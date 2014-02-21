package org.neodsl.tests.dsl

import org.neodsl.tests.BaseTests
import org.neodsl.tests.dsl.PatternDomain.{Comment, Person}

class PatternExamples extends BaseTests {
  val john = Person("John")
  val friend = Person("JohnsFriend")
  val matthew = Person("Matthew")
  val wiseComment = Comment("Wise Comment")

  def queryWithConnectionsSpecified = {
    { john.knows (1 to 2) { friend knows matthew } }
  }

  def queryWithAnonymousNodes = {
    { john knows (_ knows (_ wrote wiseComment)) } and
    { john knows (_ knows { matthew likes wiseComment }) } and
    { john knows matthew }
  }

  def explicitQueryWithPlaceholders = {
    val (fof, johnFriend) = (p[Person], p[Person])

    { john knows { johnFriend knows { fof wrote wiseComment } } } and
    { john knows { johnFriend knows { matthew likes wiseComment } } } and
    { john knows matthew }
  }

  def tabbedQuery = {
    john knows (fof =>
      fof knows (
        _ wrote wiseComment
      )
    ) and {
      john knows (
        _ knows {
          matthew likes wiseComment
        }
      )
    } and {
      john knows matthew
    }
  }

  private def p[T >: Null]: T = null
}
