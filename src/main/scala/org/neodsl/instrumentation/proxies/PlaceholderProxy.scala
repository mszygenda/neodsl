package org.neodsl.instrumentation.proxies

import java.lang.reflect.Method
import net.sf.cglib.proxy.MethodProxy

/**
 * Used for dynamically created objects used as placeholders
 *
 * It alters equals method so it returns true only for the same instances of placeholders
 */
trait PlaceholderProxy extends Proxy {
  override def intercept(obj: AnyRef, method: Method, args: Array[AnyRef], proxy: MethodProxy): AnyRef = {
    method.getName match {
      case "equals" => {
        Boolean.box(obj.eq(args(0)))
      }
      case _ => {
        super.intercept(obj, method, args, proxy)
      }
    }
  }
}
