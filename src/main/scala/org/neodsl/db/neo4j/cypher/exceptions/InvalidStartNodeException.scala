package org.neodsl.db.neo4j.cypher.exceptions

import org.neodsl.queries.domain.Node

class InvalidStartNodeException(val node: Node[_]) extends Exception("Can't use given node as starting one") {

}
