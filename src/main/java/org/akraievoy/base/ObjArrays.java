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
import com.google.common.collect.ObjectArrays;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Simple shortcuts relevant for ObjectArrays, which, for now, are not present in the Google Guava's library itself.
 *
 * @author Anton Kraievoy
 */
public class ObjArrays {
  private ObjArrays() {
    // utility class with no public constructor
  }

  public static <T> boolean contains(@Nullable T[] arr, @Nullable T search) {
    return indexOf(arr, search) >= 0;
  }

  public static <T> int indexOf(@Nullable T[] arr, @Nullable T search) {
    if (arr == null || arr.length == 0) {
      return -1;
    }

    for (int i = 0, arrLength = arr.length; i < arrLength; i++) {
      if (Objects.equal(arr[i], search)) {
        return i;
      }
    }

    return -1;
  }

  @Nullable
  public static <T> T[] remove(@Nullable T[] arr, @Nullable T search) {
    if (arr == null || arr.length == 0) {
      return arr;
    }

    int occurences = 0;
    for (int i = 0, arrLength = arr.length; i < arrLength; i++) {
      if (Objects.equal(arr[i], search)) {
        occurences += 1;
      }
    }

    if (occurences == 0) {
      return arr;
    }

    final T[] result = ObjectArrays.newArray(arr, arr.length - occurences);
    int resultPos = 0;
    for (int i = 0, arrLength = arr.length; i < arrLength; i++) {
      final T tCur = arr[i];
      if (Objects.equal(tCur, search)) {
        continue;
      }
      result[resultPos] = tCur;
      resultPos++;
    }

    return result;
  }

  @Nullable
  public static long[] unbox(@Nullable final Long[] longs) {
    return unbox(longs, 0);
  }

  @Nullable
  public static int[] unbox(@Nullable final Integer[] ints) {
    return unbox(ints, 0);
  }

  @Nullable
  public static double[] unbox(@Nullable final Double[] doubles) {
    return unbox(doubles, 0);
  }

  @Nullable
  public static boolean[] unbox(@Nullable final Boolean[] bools) {
    return unbox(bools, false);
  }

  @Nullable
  public static float[] unbox(@Nullable final Float[] floats) {
    return unbox(floats, 0);
  }

  @Nullable
  public static long[] unbox(@Nullable final Long[] longs, final long def) {
    if (longs == null) {
      return null;
    }

    final long[] result = new long[longs.length];
    for (int i = 0; i < longs.length; i++) {
      result[i] = longs[i] != null ? longs[i] : def;
    }
    return result;
  }

  @Nullable
  public static int[] unbox(@Nullable final Integer[] ints, final int def) {
    if (ints == null) {
      return null;
    }

    final int[] result = new int[ints.length];
    for (int i = 0; i < ints.length; i++) {
      result[i] = ints[i] != null ? ints[i] : def;
    }
    return result;
  }

  @Nullable
  public static double[] unbox(@Nullable final Double[] doubles, final double def) {
    if (doubles == null) {
      return null;
    }

    final double[] result = new double[doubles.length];
    for (int i = 0; i < doubles.length; i++) {
      result[i] = doubles[i] != null ? doubles[i] : def;
    }
    return result;
  }

  @Nullable
  public static float[] unbox(@Nullable final Float[] floats, final float def) {
    if (floats == null) {
      return null;
    }

    final float[] result = new float[floats.length];
    for (int i = 0; i < floats.length; i++) {
      result[i] = floats[i] != null ? floats[i] : def;
    }
    return result;
  }

  @Nullable
  public static boolean[] unbox(@Nullable final Boolean[] bools, final boolean def) {
    if (bools == null) {
      return null;
    }

    final boolean[] result = new boolean[bools.length];
    for (int i = 0; i < bools.length; i++) {
      result[i] = bools[i] != null ? bools[i] : def;
    }
    return result;
  }

  @SuppressWarnings({"unchecked"})
  public static <T> T[] join(final @Nonnull T[] itemsA, final @Nonnull T[] itemsB) {
    return ObjectArrays.concat(itemsA, itemsB, (Class<T>) itemsA.getClass().getComponentType());
  }
}