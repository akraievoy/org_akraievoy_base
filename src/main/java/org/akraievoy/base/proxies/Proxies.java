/*
 Copyright 2011 Anton Kraievoy akraievoy@gmail.com
 This file is part of org.akraievoy:base.

 org.akraievoy:base is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 org.akraievoy:base is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with org.akraievoy:base. If not, see <http://www.gnu.org/licenses/>.
 */

package org.akraievoy.base.proxies;

import com.google.common.base.Defaults;
import com.google.common.primitives.Primitives;
import org.slf4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Proxies {
  /**
   * @param interf interface class to create proxy for
   * @return a proxy that silently ignores all method calls
   */
  @SuppressWarnings("unchecked")
  public static <E> E noop(Class<E> interf) {
    return (E) Proxy.newProxyInstance(interf.getClassLoader(), new Class[]{interf}, new InvocationHandler() {
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> resultType = method.getReturnType();
        if (Primitives.allPrimitiveTypes().contains(resultType)) {
          return Defaults.defaultValue(resultType);
        }
        return null;
      }
    });
  }

  /**
   * @param interf            interface class to create proxy for
   * @param notNullTerminates to avoid chaining on first returned value
   * @param impls             implementations vararg
   * @return a proxy that joins calls of several implementations
   */
  @SuppressWarnings("unchecked")
  public static <E> E join(Class<E> interf, boolean notNullTerminates, final E... impls) {
    return (E) Proxy.newProxyInstance(interf.getClassLoader(), new Class[]{interf},
        new InvocationHandlerJoining<E>(notNullTerminates, impls));
  }

  @SuppressWarnings("unchecked")
  public static <E> E robust(Class<E> interf, final E impl, final Logger failureLogger) {
    return (E) Proxy.newProxyInstance(interf.getClassLoader(), new Class[]{interf}, new InvocationHandler() {
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
          return method.invoke(impl, args);
        } catch (Throwable thr) {
          failureLogger.warn(method.getName() + " failed: " + thr.getMessage());
          failureLogger.debug("details: " + thr.getMessage(), thr);

          return null;
        }
      }
    });
  }

  @SuppressWarnings("unchecked")
  /**
   * Returns a proxy which will synchronize all interface calls with given
   * implementation.
   */
  public static <E> E synch(Class<E> interf, final E impl, final Object lockObj) {
    return (E) Proxy.newProxyInstance(interf.getClassLoader(), new Class[]{interf}, new InvocationHandler() {
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        synchronized (lockObj) {
          return method.invoke(impl, args);
        }
      }
    });
  }

  protected static class InvocationHandlerJoining<E> implements InvocationHandler {
    protected final E[] impls;
    protected final boolean terminateOnNotNull;

    public InvocationHandlerJoining(final boolean notNullTerminates, final E... impls) {
      this.impls = impls;
      terminateOnNotNull = notNullTerminates;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      final Class<?> retType = method.getReturnType();
      Object res = Primitives.allPrimitiveTypes().contains(retType) ? Defaults.defaultValue(retType) : null;

      for (E impl : impls) {
        res = method.invoke(impl, args);

        if (res != null && terminateOnNotNull) {
          return res;
        }
      }


      return res;
    }
  }
}
