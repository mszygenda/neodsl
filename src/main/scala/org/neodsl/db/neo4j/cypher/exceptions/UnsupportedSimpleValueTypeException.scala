package org.neodsl.db.neo4j.cypher.exceptions

class UnsupportedSimpleValueTypeException(classObj: Class[_]) extends Exception("Unsupported simple value type: %s" format (classObj.getName))
