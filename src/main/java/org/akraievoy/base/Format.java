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

import java.text.*;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class Format {
  protected static final DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);
  protected static final DateFormat dateTimeFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT);
  protected static final NumberFormat decimalFormat2 = new DecimalFormat("###########0.##", new DecimalFormatSymbols(Locale.ENGLISH));
  protected static final NumberFormat decimalFormat4 = new DecimalFormat("###########0.####", new DecimalFormatSymbols(Locale.ENGLISH));
  protected static final NumberFormat decimalFormat6 = new DecimalFormat("###########0.######", new DecimalFormatSymbols(Locale.ENGLISH));

  public static String format(final long[] longs) {
    return format(longs, " ");
  }

  public static String format(final long[] longs, final String separator) {
    final StringBuilder result = new StringBuilder();

    for (int i = 0, length = longs.length; i < length; i++) {
      long aLong = longs[i];
      result.append(aLong);
      if (i < length - 1) {
        result.append(separator);
      }
    }

    return result.toString();
  }

  public static String format(final Long[] longs) {
    return format(longs, " ");
  }

  public static String format(final Long[] longs, final String separator) {
    final StringBuilder result = new StringBuilder();

    for (int i = 0, length = longs.length; i < length; i++) {
      long aLong = longs[i];
      result.append(aLong);
      if (i < length - 1) {
        result.append(separator);
      }
    }

    return result.toString();
  }

  public static String format(final Collection<Long> longs) {
    return format(longs, " ");
  }

  public static String format(final Collection<Long> longs, final String separator) {
    final StringBuilder result = new StringBuilder();

    for (Iterator<Long> longIterator = longs.iterator(); longIterator.hasNext();) {
      Long aLong = longIterator.next();
      result.append(aLong);

      if (longIterator.hasNext()) {
        result.append(separator);
      }
    }

    return result.toString();
  }

  public static String format(Date aDate) {
    return aDate != null ? dateFormat.format(aDate) : "";
  }

  public static String format(Date aDate, final boolean includeTime) {
    if (!includeTime) {
      return format(aDate);
    }

    return aDate != null ? dateTimeFormat.format(aDate) : "";
  }

  public static String formatDuration(long millis) {
    final StringBuilder stamp = new StringBuilder("");

    final int second = 1000;
    final long minute = 60 * second;
    final long hour = 60 * minute;

    stamp.insert(0, (millis % minute) / second);
    while (stamp.length() < 2) {
      stamp.insert(0, "0");
    }

    stamp.insert(0, ":");

    stamp.insert(0, (millis % hour) / minute);
    while (stamp.length() < 5) {
      stamp.insert(0, "0");
    }

    stamp.insert(0, ":");

    stamp.insert(0, millis / (hour));
    while (stamp.length() < 8) {
      stamp.insert(0, "0");
    }

    return stamp.toString();
  }

  public static String formatMem(long bytes) {
    final long tolerance = 8;
    final long k = 1024;
    if (bytes < tolerance * k) {
      return String.valueOf(bytes) + "b";
    }

    final long m = k * k;
    if (bytes < tolerance * m) {
      return String.valueOf((bytes + k / 2) / k) + "k";
    }

    final long g = m * k;
    if (bytes < tolerance * g) {
      return String.valueOf((bytes + m / 2) / m) + "m";
    }

    return String.valueOf((bytes + g / 2) / g) + "g";
  }

  public static synchronized String format(final Number somenumber, final String defaultValue) {
    if (somenumber == null) {
      return defaultValue;
    }

    if (somenumber instanceof Float) {
      return format2(somenumber.floatValue());
    } else {
      return format2(somenumber.doubleValue());
    }
  }

  public static String format(final Number somenumber) {
    return format(somenumber, "0");
  }

  public static String format(final double somenumber) {
    return format(new Double(somenumber));
  }

  public static String format(final float somenumber) {
    return format(new Float(somenumber));
  }

  public static String format2(final double somenumber) {
    return decimalFormat2.format(somenumber);
  }

  public static String format2(final float somenumber) {
    return decimalFormat2.format(somenumber);
  }

  public static String format4(final double somenumber) {
    return decimalFormat4.format(somenumber);
  }

  public static String format4(final float somenumber) {
    return decimalFormat4.format(somenumber);
  }

  public static String format6(final double somenumber) {
    return decimalFormat6.format(somenumber);
  }

  public static String format6(final float somenumber) {
    return decimalFormat6.format(somenumber);
  }

  public static String upperFirst(final String str) {
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }

  public static String lowerFirst(final String str) {
    return Character.toLowerCase(str.charAt(0)) + str.substring(1);
  }
}
