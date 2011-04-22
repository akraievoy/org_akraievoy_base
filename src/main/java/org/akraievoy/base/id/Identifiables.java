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

package org.akraievoy.base.id;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Identifiables {
  protected static final long[] LONGS_EMPTY = new long[0];
  protected static final Long[] LONGS_OBJECTS_EMPTY = new Long[0];

  private Identifiables() {
  }

  public static Identifiable[] toArray(final Collection<Identifiable> identifiables) {
    return identifiables.toArray(new Identifiable[identifiables.size()]);
  }

  public static List<Long> idList(List<Identifiable> identifiables) {
    if (identifiables == null || identifiables.size() == 0) {
      return Collections.emptyList();
    }

    final List<Long> result = new ArrayList<Long>();
    for (final Identifiable ident : identifiables) {
      result.add(ident == null ? null : ident.getId());
    }

    return result;
  }

  public static List<Long> idList(Identifiable[] identifiables) {
    if (identifiables == null || identifiables.length == 0) {
      return Collections.emptyList();
    }

    final List<Long> result = new ArrayList<Long>(identifiables.length);
    for (final Identifiable ident : identifiables) {
      result.add(ident == null ? null : ident.getId());
    }

    return result;
  }

  public static Long[] idArray(List<Identifiable> identifiables) {
    if (identifiables == null || identifiables.size() == 0) {
      return LONGS_OBJECTS_EMPTY;
    }

    final Long[] result = new Long[identifiables.size()];
    for (int identIndex = 0; identIndex < identifiables.size(); identIndex++) {
      final Identifiable ident = identifiables.get(identIndex);
      result[identIndex] = ident == null ? null : ident.getId();
    }

    return result;
  }

  public static Long[] idArray(Identifiable[] identifiables) {
    if (identifiables == null || identifiables.length == 0) {
      return LONGS_OBJECTS_EMPTY;
    }

    final Long[] result = new Long[identifiables.length];
    for (int identIndex = 0; identIndex < identifiables.length; identIndex++) {
      final Identifiable ident = identifiables[identIndex];
      result[identIndex] = ident == null ? null : ident.getId();
    }

    return result;
  }

  public static long[] idArray(List<Identifiable> identifiables, long def) {
    if (identifiables == null || identifiables.size() == 0) {
      return LONGS_EMPTY;
    }

    final long[] result = new long[identifiables.size()];
    for (int identIndex = 0; identIndex < identifiables.size(); identIndex++) {
      final Identifiable ident = identifiables.get(identIndex);
      result[identIndex] = ident == null ? def : ident.getId() == null ? def : ident.getId()
          ;
    }

    return result;
  }

  public static long[] idArray(Identifiable[] identifiables, long def) {
    if (identifiables == null || identifiables.length == 0) {
      return LONGS_EMPTY;
    }

    final long[] result = new long[identifiables.length];
    for (int identIndex = 0; identIndex < identifiables.length; identIndex++) {
      final Identifiable ident = identifiables[identIndex];
      result[identIndex] = ident == null ? def : ident.getId() == null ? def : ident.getId();
    }

    return result;
  }
}
