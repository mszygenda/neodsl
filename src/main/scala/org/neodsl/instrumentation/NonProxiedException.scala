package org.neodsl.instrumentation

case class NonProxiedException(msg: String, val obj: Proxyable) extends Exception(msg)
