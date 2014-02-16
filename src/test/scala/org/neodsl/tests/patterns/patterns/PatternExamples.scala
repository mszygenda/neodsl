package org.neodsl.tests.patterns.patterns

import org.neodsl.tests.BaseTests
import org.neodsl.tests.patterns.patterns.PatternDomain.{Comment, Person}

class PatternExamples extends BaseTests {
  val john = Person("John")
  val friend = Person("JohnsFriend")
  val matthew = Person("Matthew")
  val wiseComment = Comment("Wise Comment")

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
