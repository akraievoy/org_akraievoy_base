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

import com.google.common.base.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Parse {
  private static final Integer[] INT_OBJS_EMPTY = new Integer[]{};
  private static final Long[] LONG_OBJS_EMPTY = new Long[]{};
  private static final Boolean[] BOOL_OBJS_EMPTY = new Boolean[]{};
  private static final Float[] FLOAT_OBJS_EMPTY = new Float[]{};
  private static final Double[] DOUBLE_OBJS_EMPTY = new Double[]{};
  private static final Byte[] BYTE_OBJS_EMPTY = new Byte[]{};

  private Parse() {
  }

  @Nullable
  public static Byte oneByte(@Nullable String value, @Nullable Byte defaultVal) {
    if (Strings.isNullOrEmpty(value)) {
      return defaultVal;
    }

    try {
      return Byte.parseByte(value);
    } catch (NumberFormatException e) {
      return defaultVal;
    }
  }

  @Nullable
  public static Integer oneInt(@Nullable String value, @Nullable Integer defaultVal) {
    if (Strings.isNullOrEmpty(value)) {
      return defaultVal;
    }

    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return defaultVal;
    }
  }

  @Nullable
  public static Long oneLong(@Nullable String value, @Nullable Long defaultVal) {
    if (Strings.isNullOrEmpty(value)) {
      return defaultVal;
    }

    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      return defaultVal;
    }
  }

  @Nullable
  public static Double oneDouble(@Nullable final String value, @Nullable final Double defaultVal) {
    if (Strings.isNullOrEmpty(value)) {
      return defaultVal;
    }

    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException e) {
      return defaultVal;
    }
  }

  @Nullable
  public static Float oneFloat(@Nullable final String value, @Nullable final Float defaultVal) {
    if (Strings.isNullOrEmpty(value)) {
      return defaultVal;
    }

    try {
      return Float.parseFloat(value);
    } catch (NumberFormatException e) {
      return defaultVal;
    }
  }

  @Nullable
  public static Boolean oneBool(@Nullable String value, @Nullable Boolean defaultVal) {
    if (Strings.isNullOrEmpty(value)) {
      return defaultVal;
    }

    try {
      return Boolean.valueOf(value);
    } catch (NumberFormatException e) {
      return defaultVal;
    }
  }

  @Nonnull
  public static Integer[] ints(@Nullable final String[] strings, @Nullable final Integer defaultVal) {
    if (strings == null) {
      return INT_OBJS_EMPTY;
    }

    final Integer[] result = new Integer[strings.length];
    for (int index = 0; index < result.length; index++) {
      result[index] = oneInt(strings[index], defaultVal);
    }
    return result;
  }

  @Nonnull
  public static Long[] longs(@Nullable final String[] strings, @Nullable final Long defaultVal) {
    if (strings == null) {
      return LONG_OBJS_EMPTY;
    }

    final Long[] result = new Long[strings.length];
    for (int index = 0; index < result.length; index++) {
      result[index] = oneLong(strings[index], defaultVal);
    }
    return result;
  }

  @Nonnull
  public static Byte[] bytes(@Nullable final String[] strings, @Nullable final Byte defaultVal) {
    if (strings == null) {
      return BYTE_OBJS_EMPTY;
    }

    final Byte[] result = new Byte[strings.length];
    for (int index = 0; index < result.length; index++) {
      result[index] = oneByte(strings[index], defaultVal);
    }
    return result;
  }

  @Nonnull
  public static Double[] doubles(@Nullable final String[] strings, @Nullable final Double defaultVal) {
    if (strings == null) {
      return DOUBLE_OBJS_EMPTY;
    }

    final Double[] result = new Double[strings.length];
    for (int index = 0; index < result.length; index++) {
      result[index] = oneDouble(strings[index], defaultVal);
    }
    return result;
  }

  @Nonnull
  public static Float[] floats(@Nullable final String[] strings, @Nullable final Float defaultVal) {
    if (strings == null) {
      return FLOAT_OBJS_EMPTY;
    }

    final Float[] result = new Float[strings.length];
    for (int index = 0; index < result.length; index++) {
      result[index] = oneFloat(strings[index], defaultVal);
    }
    return result;
  }

  @Nonnull
  public static Boolean[] bools(@Nullable final String[] strings, @Nullable final Boolean defaultVal) {
    if (strings == null) {
      return BOOL_OBJS_EMPTY;
    }

    final Boolean[] result = new Boolean[strings.length];
    for (int index = 0; index < result.length; index++) {
      result[index] = oneBool(strings[index], defaultVal);
    }
    return result;
  }
}
