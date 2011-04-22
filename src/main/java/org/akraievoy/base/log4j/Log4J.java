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

package org.akraievoy.base.log4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Log4J {
  private static boolean loggingConfigured = false;

  public static synchronized void configure() {
    if (loggingConfigured) {
      return;
    }

    try {
      final Class<?> configuratorClass = Class.forName("org.apache.log4j.BasicConfigurator");
      final Method method = configuratorClass.getMethod("configure");
      method.invoke(null);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("please ensure you have Log4J in the classpath?", e);
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException("no configure() method in Log4J Configurator class", e);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("please ensure you're able to use reflective calls", e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException("Log4J configuration reported an error", e);
    }

    loggingConfigured = true;
  }
}
