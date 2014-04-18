package org.neodsl.tests.example.socialnetwork

import org.neodsl.tests.BaseTests

class SocialNetworkTests extends BaseTests {
  implicit val execEngine = mockExecutionEngine(List())

  "Person.friends(john)" should "return only one result" in {
    val john: Person = Person.find(1).get
    val johnsFriends = Person.friends(john)

    johnsFriends.length shouldEqual 1
  }
}
