package org.neodsl.reflection.proxy

case class IndexPlaceholderProxy(name: String, indexValue: (String, Any)) extends PlaceholderProxy