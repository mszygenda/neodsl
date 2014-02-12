import org.neodsl.reflection.proxy.tests.dsl.patterns.PatternTests

class PatternGeneratorsTests extends PatternTests {
  "Generator" should "create pattern" in {
    /*node("10")[org.neodsl.dsl.tests.patterns.org.neodsl.tests.patterns.patterns.Person] knows node("11")[org.neodsl.dsl.tests.patterns.org.neodsl.tests.patterns.patterns.Person] wrote (comment => returnAs(comment))

    val person = Node("10")

    person knows someone knows (fof => fof.name === person)*/
  }
}
