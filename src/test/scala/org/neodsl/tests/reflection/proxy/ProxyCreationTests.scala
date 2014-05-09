package org.neodsl.tests.reflection.proxy

import org.neodsl.reflection.proxy._
import org.neodsl.tests.BaseTests
import org.neodsl.tests.reflection.proxy.ProxyDomain.{ClassWithMultipleCtors, EmptyClassWithDefaultCtor, DummyProxy}
import org.neodsl.reflection.ObjectFactory

class ProxyCreationTests extends BaseTests {
  "Proxy of empty class with default constructor" should "be created" in {
    val proxy = new DummyProxy()
    val proxiedObject = ProxyFactory.createProxiedObject[EmptyClassWithDefaultCtor](proxy)

    proxiedObject should not be(null)
    proxiedObject.isProxied should be(true)
    proxiedObject.proxy shouldEqual (proxy)
  }

  "Proxy of class with multiple constructors" should "be created" in {
    val proxy = new DummyProxy()
    val proxiedObject = ProxyFactory.createProxiedObject[ClassWithMultipleCtors](proxy)

    proxiedObject should not be(null)
    proxiedObject.isProxied should be(true)
    proxiedObject.proxy shouldEqual (proxy)
  }

  "Proxy instance" should "be accessible with proxy property of proxied object" in {
    val proxy = new DummyProxy()
    val proxiedPerson = ProxyFactory.createProxiedObject[EmptyClassWithDefaultCtor](proxy)

    proxiedPerson.proxy shouldEqual proxy
  }

  "Nonproxied object" should "throw exception when proxy property is accessed" in {
    val nonProxiedObject = new EmptyClassWithDefaultCtor

    intercept[NonProxiedException] {
      nonProxiedObject.proxy
    }
  }

  "Nonproxied object" should "return false from isProxied property" in {
    val nonProxiedObject = new EmptyClassWithDefaultCtor

    nonProxiedObject.isProxied should be(false)
  }
}
