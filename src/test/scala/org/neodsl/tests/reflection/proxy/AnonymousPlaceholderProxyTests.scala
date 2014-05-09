package org.neodsl.tests.reflection.proxy

import org.neodsl.tests.BaseTests
import org.neodsl.instrumentation.{ProxyFactory, Proxyable}

class Communist extends Proxyable {
  override def equals(obj: Any) = true
}

class AnonymousPlaceholderProxyTests extends BaseTests {
  "Objects proxied with placeholder" should "equal only for same instances" in {
    val fstObj = ProxyFactory.createAnonymousPlaceholder[Communist]
    val sndObj = ProxyFactory.createAnonymousPlaceholder[Communist]

    fstObj shouldNot equal(sndObj)
    fstObj should equal(fstObj)
    sndObj should equal(sndObj)
  }
}
