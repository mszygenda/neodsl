package org.neodsl.reflection.proxy

import org.neodsl.dsl.domain.Node
import net.sf.cglib.proxy.{MethodProxy, MethodInterceptor}
import java.lang.reflect.Method

class IdentifierProxy(val id: String)
