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

package org.akraievoy.base.dump;

import java.io.*;

/**
 * Simplistic in-memory object (de)serialization.
 */
public class Dump {
  public static byte[] dumpObject(final Serializable obj) {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      final ObjectOutputStream objOut = new ObjectOutputStream(baos);

      objOut.writeObject(obj);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    return baos.toByteArray();
  }

  public static Object readObject(final byte[] bytes) {
    final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    try {
      final ObjectInputStream objIn = new ObjectInputStream(bais);

      return objIn.readObject();
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(e);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
