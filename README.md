NeoDSL
======

This is simple DSL/mapper for graph databases (neo4j in particular) written in scala.

It lets you build Cypher queries with easy to read DSL which are automatically validated at compilation phase. It also handles mapping of results to domain classes. All that is needed to start building your queries is to define classes extending DomainObject. You can define relationships using <--, -->, -- methods (respectively to define incoming, outgoing or without specified direction relationship). Once you're done with that. You can start composing patterns and whole queries.

Project is still under active development. It for sure has some bugs and doesn't cover all possible use cases but it aims to develop interesting way of searching in graph databases.

**Summary of what it does:**

* Let you express complex patterns using friendly syntax that is automatically validated at compilation phase
* Let you express simple conditions for returned nodes which are serialized into WHERE statements (Using basic Scala syntax for Boolean expressions, achieved using Scala Macros)
* Map results from database to domain classes

**And what it doesn't:**

* Won't help you create/update nodes, relationships **yet**
* Won't let you define some advanced criteria for returned nodes (like expressing uniqueness)
* And probably a lot more!

### Examples

Given following model classes

```scala
case class Person(name: String, age: Int) extends DomainObject[Person] {
  val knows = --[Person]("KNOWS")
  val wrote = -->[Comment]("WROTE")
  val likes = -->[Comment]("LIKES")
}

case class Comment(content: String) extends DomainObject[Comment] {
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
MATCH john-[:KNOWS]->friend-[:KNOWS]->fof,
      fof-[:LIKES]->comment<-[:WROTE]-john
WHERE fof.age >= 20
RETURN fof.id, fof.name, fof.age
```

You can find more examples in: [Social Network Example](https://github.com/mszygenda/neodsl/blob/master/src/test/scala/org/neodsl/tests/example/socialnetwork/Person.scala), [QueriesExamples.scala](https://github.com/mszygenda/neodsl/blob/master/src/test/scala/org/neodsl/tests/dsl/QueriesExamples.scala)
