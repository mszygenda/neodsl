neodsl
======

This is simple DSL/mapper for graph databases (neo4j in particular) written in scala.

### Examples

Given following model classes

```scala
case class Person(name: String, age: Int) extends DomainObject[Person] {
  def this() = this(null, 0)

  val knows = -->[Person]("KNOWS")
  val wrote = -->[Comment]("WROTE")
  val likes = -->[Comment]("LIKES")
}

case class Comment(content: String) extends DomainObject[Comment] {
  def this() = this(null)

  val writtenBy = <--[Person]("WROTE")
}
```

Following snippet builds query to retrieve all John's friends of friends who like comments written by him and are at least 20 years old

```scala
val (friend, fof, comment) = (p[Person], p[Person], p[Comment])

{ john knows { friend knows fof } } and
{ fof likes { comment writtenBy john } } where {
  fof.age >= 20
} select(fof)
```

Such query translates to following cypher query:

```cypher
START john=node(JOHN_ID)
MATCH john-[:KNOWS]->friend->[:KNOWS]->fof,
      fof-[:LIKES]->comment<-[:WROTE]-john
WHERE fof.age >= 20
```