package org.neodsl.tests.reflection.proxy

import org.neodsl.tests.BaseTests
import org.neodsl.queries.domain.Node
import org.neodsl.reflection.proxy.{Proxyable, ObjectFactory}

class Communist extends Proxyable {
  override def equals(obj: Any) = true
}

class PlaceholderProxyTests extends BaseTests {
  "Objects proxied with placeholder" should "equal only for same instances" in {
    val fstObj = ObjectFactory.createPlaceholderObject[Communist]
    val sndObj = ObjectFactory.createPlaceholderObject[Communist]

    fstObj shouldNot equal(sndObj)
    fstObj should equal(fstObj)
    sndObj should equal(sndObj)
  }
}
