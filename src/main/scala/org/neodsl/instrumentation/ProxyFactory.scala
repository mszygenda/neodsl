package org.neodsl.instrumentation

import net.sf.cglib.proxy.{Factory, Enhancer}
import org.neodsl.reflection.ClassInfoFactory
import scala.collection.mutable
import org.neodsl.instrumentation.proxies.{IndexPlaceholderProxy, DummyProxy, AnonymousPlaceholderProxy}

object ProxyFactory {
  type ProxyCreator = proxies.Proxy => Any
  private val proxyCache = mutable.HashMap[String, ProxyCreator]()

  def createIndexPlaceholder[P <: Proxyable](name: String, indexVal: Tuple2[String, Any])(implicit manifest: Manifest[P]): P = {
    val indexPlaceholderProxy = new IndexPlaceholderProxy(name, indexVal)

    createProxiedObject[P](indexPlaceholderProxy)
  }

  def createAnonymousPlaceholder[P <: Proxyable](implicit manifest: Manifest[P]): P = {
    val placeholderProxy = new AnonymousPlaceholderProxy()

    createProxiedObject[P](placeholderProxy)
  }

  def createProxiedObject[P <: Proxyable](proxy: proxies.Proxy)(implicit manifest: Manifest[P]): P = {
    val proxyCreator = getProxyCreator[P]
    
    proxyCreator(proxy).asInstanceOf[P]
  }

  def getProxyCreator[P <: Proxyable](implicit manifest: Manifest[P]): ProxyCreator = {
    val classInfo = ClassInfoFactory.getClassInfo[P]

    proxyCache.getOrElseUpdate(classInfo.fullName, {
      createProxyCreator[P]
    })
  }

  private def createProxyCreator[P <: Proxyable](implicit manifest: Manifest[P]): ProxyCreator = {
    val enhancer = new Enhancer()
    val proxiedClassInfo = ClassInfoFactory.getClassInfo[P]
    val ctor = proxiedClassInfo.defaultOrMainCtor
    val ctorParamTypes = ctor.paramTypes
    val ctorParamDefaults = ctor.paramTypes.map(defaultValueForType)

    enhancer.setSuperclass(proxiedClassInfo.javaClass)
    enhancer.setCallback(new DummyProxy)

    enhancer.create(ctorParamTypes.toArray, ctorParamDefaults.toArray) match {
      case factory: Factory => {
        (proxy: proxies.Proxy) => {
          factory.newInstance(ctorParamTypes.toArray, ctorParamDefaults.toArray, Array(proxy))
        }
      }
      case _ => {
        throw new Exception("Proxy creation has failed")
      }
    }
  }

  private def defaultValueForType(classObj: Class[_]) = {
    if (None.getClass.getSuperclass.isAssignableFrom(classObj)) {
      None
    } else {
      null
    }
  }
}
