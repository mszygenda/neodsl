package org.neodsl.reflection.proxy

trait Proxyable {
  def proxy: Proxy = {
    throw NonProxiedException("Trying to get proxy instance from nonproxied object", this)
  }

  def isProxied = false
}
