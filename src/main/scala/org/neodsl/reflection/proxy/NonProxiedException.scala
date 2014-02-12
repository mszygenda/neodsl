package org.neodsl.reflection.proxy

case class NonProxiedException(msg: String, val obj: Proxyable) extends Exception(msg)
