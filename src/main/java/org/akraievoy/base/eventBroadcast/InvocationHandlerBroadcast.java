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

package org.akraievoy.base.eventBroadcast;

import com.google.common.base.Defaults;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class InvocationHandlerBroadcast<EventSpec> implements InvocationHandler {
  final EventBroadcast<EventSpec> parent;

  public InvocationHandlerBroadcast(EventBroadcast<EventSpec> parent) {
    this.parent = parent;
  }

  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object result = null;
    synchronized (parent.structuralMutex) {
      for (EventSpec eventSpec : parent.listeners) {
        try {
          result = method.invoke(eventSpec, args);
        } catch (IllegalAccessException e) {
          throw new IllegalStateException("environment is not set up properly", e);
        } catch (IllegalArgumentException e) {
          throw new IllegalStateException("should not occur", e);
        } catch (InvocationTargetException e) {
          parent.getLog().warn("failed in '" + method.toGenericString() + "': " + e.getMessage(), e);
        }
      }
    }

    Class<?> resultType = method.getReturnType();
    if (resultType.isPrimitive() && result == null) {
      return Defaults.defaultValue(resultType);
    } else {
      return result;
    }
  }
}