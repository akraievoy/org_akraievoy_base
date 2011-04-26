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

package org.akraievoy.base;

import com.google.common.base.Objects;

import javax.swing.*;

/**
 * Handy armament for defensive programming.
 *
 * @see com.google.common.base.Preconditions
 * @see org.springframework.util.Assert
 */
public class Die {
  private Die() {
    //  sealed
  }

  public static IllegalArgumentException unexpected(String property, Object actual) {
    final String msg = "unexpected " + property + " actual: '" + String.valueOf(actual) + "'";

    return new IllegalArgumentException(msg);
  }

  public static IllegalArgumentException unexpected(String property, Object actual, String message) {
    final String msg = "unexpected " + property + " actual: '" + String.valueOf(actual) + "'";

    return new IllegalArgumentException(msg + ": " + message);
  }

  public static IllegalArgumentException unexpected(String property, Object actual, Throwable cause) {
    final String msg = "unexpected " + property + " actual: '" + String.valueOf(actual) + "'";

    return new IllegalArgumentException(msg, cause);
  }

  public static void ifNotEqual(String property, Object expected, Object actual) {
    if (!Objects.equal(expected, actual)) {
      final String msg =
          "invalid " + property + ", " +
              "expected '" + String.valueOf(expected) + "'," +
              " actual: '" + String.valueOf(actual) + "'";

      throw new IllegalArgumentException(msg);
    }
  }

  public static void ifEmpty(String property, Object actual) {
    if (actual == null || actual.toString().trim().length() == 0) {
      final String msg =
          "expecting non-empty " + property + " actual: '" + String.valueOf(actual) + "'";

      throw new IllegalArgumentException(msg);
    }
  }

  public static void ifFalse(String conditionStr, boolean condition) {
    if (!condition) {
      throw new IllegalStateException("condition '" + conditionStr + "' must be TRUE");
    }
  }

  public static void ifTrue(String conditionStr, boolean condition) {
    if (condition) {
      throw new IllegalStateException("condition '" + conditionStr + "' must be FALSE");
    }
  }

  public static void ifNull(String property, Object someValue) {
    if (someValue == null) {
      throw new IllegalStateException(String.valueOf(property) + " must NOT be null");
    }
  }

  public static void ifNotNull(final String property, final Object someValue) {
    if (someValue != null) {
      throw new IllegalStateException(String.valueOf(property) + " must be null");
    }
  }

  public static RuntimeException ifReached(final Throwable cause) {
    return new IllegalStateException("Was unreachable before", cause);
  }

  public static void ifNotAwt() {
    if (!SwingUtilities.isEventDispatchThread()) {
      throw new IllegalStateException("should be running in AWT dispatcher thread");
    }
  }
}