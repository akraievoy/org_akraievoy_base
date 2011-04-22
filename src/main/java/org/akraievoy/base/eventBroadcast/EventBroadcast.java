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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple reflection-based event broadcasting, couple of goodies related to robustness, reporting and return values.
 */
public class EventBroadcast<EventSpec> {
  private static final Logger log = LoggerFactory.getLogger(EventBroadcast.class);

  final Object structuralMutex = new Object();
  final List<EventSpec> listeners = new ArrayList<EventSpec>();

  EventSpec broadcast;

  public void add(EventSpec impl) {
    synchronized (structuralMutex) {
      listeners.add(impl);
    }
  }

  public void remove(EventSpec impl) {
    synchronized (structuralMutex) {
      listeners.remove(impl);
    }
  }

  @SuppressWarnings({"unchecked"})
  public EventSpec getBroadcast(Class<EventSpec> classRef) {
    if (broadcast == null) {
      broadcast = (EventSpec) Proxy.newProxyInstance(
          EventBroadcast.class.getClassLoader(),
          new Class[]{classRef},
          new InvocationHandlerBroadcast<EventSpec>(this)
      );
    }

    return broadcast;
  }

  //  LATER use separate loggers depending on EventSpec type

  protected Logger getLog() {
    return log;
  }
}
