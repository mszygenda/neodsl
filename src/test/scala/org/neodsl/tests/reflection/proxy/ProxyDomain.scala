package org.neodsl.tests.reflection.proxy

import org.neodsl.instrumentation.Proxyable
import org.neodsl.instrumentation.proxies.Proxy

object ProxyDomain {
  class DummyProxy extends Proxy

  class EmptyClassWithDefaultCtor extends Proxyable

  class ClassWithMultipleCtors(val arg1: String, val arg2: String) extends Proxyable {
    def this() = this(null, null)
    def this(arg1: String) = this(arg1, null)
  }
}
