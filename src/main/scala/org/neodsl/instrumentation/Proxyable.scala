package org.neodsl.instrumentation

import org.neodsl.instrumentation.proxies.Proxy

trait Proxyable {
  def proxy: Proxy = {
    throw NonProxiedException("Trying to get proxy instance from nonproxied object", this)
  }

  def isProxied = false
}
