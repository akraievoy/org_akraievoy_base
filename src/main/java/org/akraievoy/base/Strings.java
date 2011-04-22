/*
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

import javax.annotation.Nonnull;


public class Strings {
  public static final String EMPTY = "";
  public static final String[] NONE = {};

  private Strings() {
    // Utility class with sealed public constructor
  }

  /*
	 * This is not a regex-based method: it treats params as regular strings.
	 * Was pulled off apache-commons-lang and simplified.
	 */
  @Nonnull
  public static String replaceAll(
      @Nonnull final String text, @Nonnull final String repl, @Nonnull final String with
  ) {
    if (repl.equals(with)) {
      return text;
    }

    int max = -1;   //  may be parameterized

    if (
        com.google.common.base.Strings.isNullOrEmpty(text) ||
            com.google.common.base.Strings.isNullOrEmpty(repl) ||
            with == null /*|| max == 0*/) {
      return text;
    }

    int start = 0;
    int end = text.indexOf(repl, start);
    if (end == -1) {
      return text;
    }
    int replLength = repl.length();
    StringBuffer buf = new StringBuffer(text.length());
    while (end != -1) {
      buf.append(text.substring(start, end)).append(with);
      start = end + replLength;
      if (--max == 0) {
        break;
      }
      end = text.indexOf(repl, start);
    }
    buf.append(text.substring(start));
    return buf.toString();
  }
}