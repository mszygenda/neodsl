import org.neodsl.dsl.domain.Node

case class Person(name: String) extends Node[Person] {
  val knows = -->[Person]("KNOWS")
  val wrote = -->[Comment]("WROTE")
  val likes = -->[Comment]("LIKES")
}

case class Comment(content: String) extends Node[Comment] {

}

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

    //Pattern(Node(Person(John)),Relation(KNOWS,-->),Pattern(Node(Person(null)),Relation(KNOWS,-->),Pattern(Node(Person(null)),Relation(KNOWS,-->),Pattern(Node(Person(Fof)),Relation(KNOWS,-->),Pattern(Node(Person(John)),Relation(WROTE,-->),Node(Comment(Wise Comment)))))))
    //Pattern(Node(Person(John)),Relation(KNOWS,-->),Pattern(Node(Person(null)),Relation(KNOWS,-->),Pattern(Node(Person(null)),Relation(KNOWS,-->),Pattern(Node(Person(Fof)),Relation(KNOWS,-->),Pattern(Node(Person(John)),Relation(KNOWS,-->),Node(Person(Fof)))))))
    println(patt)
    println(sndPatt)
  }
}
