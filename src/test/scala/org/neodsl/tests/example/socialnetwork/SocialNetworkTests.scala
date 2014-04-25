package org.neodsl.tests.example.socialnetwork

import org.neodsl.tests.BaseTests
import org.neodsl.dsl.patterns.PatternBuilder._
import Database._

class SocialNetworkTests extends BaseTests {
  val john: Person = autoIndex[Person]("name" -> "John")

  "Person.friends(john)" should "return only one result" in {
    val johnsFriends = Person.friends(john)

    johnsFriends.length shouldEqual 2
    johnsFriends should contain(Person("Anne"))
    johnsFriends should contain(Person("Jessica"))
  }

  "Person.writtenComments(john)" should "return wise comment" in {
    val johnsPosts = Person.writtenPosts(john)

    johnsPosts.length shouldEqual 1
    johnsPosts should contain(Comment("Wise Comment"))
  }
}
