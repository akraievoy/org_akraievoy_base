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

package org.akraievoy.base.expiringCache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpiringCache<K, V> {
  public static interface Creator<K, V> {
    V create(K key);
  }

  final LinkedHashMap<K, V> cache = new LinkedHashMap<K, V>();
  final LinkedHashMap<K, Long> accessTimes = new LinkedHashMap<K, Long>();
  final Creator<K, V> creator;
  final int validTime;

  public ExpiringCache(final Creator<K, V> creator, final int validTime) {
    this.creator = creator;
    this.validTime = validTime;
  }

  public void pack() {
    final List<K> invalidated = new ArrayList<K>();
    final long currentMillis = getCurrentMillis();

    for (K cachedStateId : cache.keySet()) {
      final Long accessTime = accessTimes.get(cachedStateId);
      if (accessTime == null || accessTime + validTime < currentMillis) {
        invalidated.add(cachedStateId);
      }
    }

    cache.keySet().removeAll(invalidated);
    accessTimes.keySet().removeAll(invalidated);
  }

  public V get(K key) {
    pack();

    final V cached = cache.get(key);
    final V toReturn;

    if (cached != null) {
      toReturn = cached;
    } else {
      toReturn = creator.create(key);
      cache.put(key, toReturn);
    }

    accessTimes.put(key, getCurrentMillis());

    return toReturn;
  }

  public void remove(K key) {
    cache.remove(key);
    accessTimes.remove(key);
  }

  public void shutdown() {
    cache.clear();
    accessTimes.clear();
  }

  public void set(K key, V value) {
    cache.put(key, value);
    accessTimes.put(key, getCurrentMillis());
  }

  protected long getCurrentMillis() {
    return System.currentTimeMillis();
  }

  public int getSize() {
    pack();
    return cache.size();
  }
}
