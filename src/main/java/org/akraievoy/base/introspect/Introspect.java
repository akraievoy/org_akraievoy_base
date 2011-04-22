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

package org.akraievoy.base.introspect;

import org.akraievoy.base.Die;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Reflection boosters.
 *
 * @see org.springframework.util.ReflectionUtils
 */
public class Introspect {
  private static final Logger log = LoggerFactory.getLogger(Introspect.class);

  /**
   * Returns all interfaces that are implemented by specified class.
   *
   * @param clazz to inspect
   * @return array of all implemented interfaces
   */
  public static Class[] getDeepInterfaces(final Class clazz) {
    final List<Class> allInterfaces = new ArrayList<Class>();
    if (clazz.isInterface()) {
      allInterfaces.add(clazz);
    }

    Class currentClazz = clazz;

    while (currentClazz.getSuperclass() != null) {
      final Class[] interfaces = currentClazz.getInterfaces();
      for (Class anInterface : interfaces) {
        if (!allInterfaces.contains(anInterface)) {
          allInterfaces.add(anInterface);
        }
      }

      currentClazz = currentClazz.getSuperclass();
    }

    return allInterfaces.toArray(new Class[allInterfaces.size()]);
  }

  public static boolean declaresField(final Class clazz, final String fieldName) {
    final Field[] declaredFields = clazz.getDeclaredFields();
    for (Field declaredField : declaredFields) {
      if (declaredField.getName().equals(fieldName)) {
        return true;
      }
    }

    return false;
  }

  public static Field resolveField(final Class entityClass, final String fieldName) throws NoSuchFieldException {
    if (declaresField(entityClass, fieldName)) {
      return entityClass.getDeclaredField(fieldName);
    }

    final Class superClass = entityClass.getSuperclass();
    if (superClass != null) {
      return resolveField(superClass, fieldName);
    }

    throw Die.unexpected("class.fieldName", entityClass.getSimpleName() + "." + fieldName, "field not found in hierarchy");
  }

  public static Object getValue(final Object entityInstance, final String fieldName) {
    final Class entityClass = entityInstance.getClass();
    try {
      final Field field = resolveField(entityClass, fieldName);
      field.setAccessible(true);
      return field.get(entityInstance);
    } catch (NoSuchFieldException e) {
      throw Die.unexpected("class.fieldName", entityClass.getSimpleName() + "." + fieldName, e);
    } catch (IllegalAccessException e) {
      throw Die.unexpected("class.fieldName", entityClass.getSimpleName() + "." + fieldName, e);
    }
  }

  public static boolean setValue(final Object entityInstance, final String fieldName, final Object fieldValue,
                                 final boolean resilient) {
    final Class entityClass = entityInstance.getClass();
    final Field field;

    try {
      field = resolveField(entityClass, fieldName);
    } catch (NoSuchFieldException e) {
      throw Die.unexpected("class.fieldName", entityClass.getSimpleName() + "." + fieldName, e);
    }

    try {
      field.setAccessible(true);
      field.set(entityInstance, fieldValue);
      return true;
    } catch (IllegalAccessException e) {
      throw Die.unexpected("class.fieldName", entityClass.getSimpleName() + "." + fieldName, e);
    } catch (IllegalArgumentException e) {
      final String fieldValueClass = fieldValue != null ? fieldValue.getClass().toString() : "null";
      final String message =
          "Failed to set '" + fieldValue + "'(" + fieldValueClass + ") " +
              "to field '" + fieldName + "' (" + field.getType() + ")";
      if (resilient) {
        log.error(message, e);
      } else {
        throw new IllegalArgumentException(message, e);
      }
      return false;
    }
  }
}
