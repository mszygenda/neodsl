package org.neodsl.reflection.proxy

import net.sf.cglib.proxy.{MethodProxy, MethodInterceptor}
import java.lang.reflect.Method

trait Proxy extends MethodInterceptor {
  def intercept(obj: AnyRef, method: Method, args: Array[AnyRef], proxy: MethodProxy): AnyRef = {
    method.getName match {
      case "proxy" => {
        this
      }
      case "isProxied" => {
        Boolean.box(true)
      }
      case _ => {
        proxy.invokeSuper(obj, args)
      }
    }
  }
}
