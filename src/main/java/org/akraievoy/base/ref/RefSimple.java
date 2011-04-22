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

package org.akraievoy.base.ref;

/**
 * This is required in case your method uses in-out parameters of some type.
 * This has nothing to do with GC and java.lang.ref package.
 */
public class RefSimple<T> implements Ref<T> {
  protected T v;

  public RefSimple(final T v) {
    this.v = v;
  }

  public T getValue() {
    return v;
  }

  public void setValue(T v) {
    this.v = v;
  }
}
