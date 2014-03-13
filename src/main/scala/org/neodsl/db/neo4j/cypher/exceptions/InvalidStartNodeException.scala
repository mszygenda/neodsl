package org.neodsl.db.neo4j.cypher.exceptions

import org.neodsl.queries.domain.TypedNode

class InvalidStartNodeException(val node: TypedNode[_]) extends Exception("Can't use given node as starting one") {

}
