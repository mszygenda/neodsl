package org.neodsl.tests.reflection.proxy

import org.neodsl.tests.BaseTests
import org.neodsl.queries.domain.TypedNode
import org.neodsl.reflection.proxy.Proxyable
import org.neodsl.reflection.ObjectFactory

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
