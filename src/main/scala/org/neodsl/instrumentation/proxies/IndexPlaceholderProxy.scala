package org.neodsl.instrumentation.proxies


case class IndexPlaceholderProxy(name: String, indexValue: (String, Any)) extends PlaceholderProxy