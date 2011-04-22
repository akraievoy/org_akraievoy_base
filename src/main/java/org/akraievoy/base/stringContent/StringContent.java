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

package org.akraievoy.base.stringContent;

/**
 * Some of apache-commons-lang code related to String/HTML manipulations.
 */
public class StringContent {
  private static final String STRING_EMPTY = "";

  private StringContent() {
    // sealed
  }

  public static String stripWhitespace(final String someText) {
    if (someText == null) {
      return STRING_EMPTY;
    }

    //  note that unicode NBSP is treated as whitespace
    String charsToReplace = " \t\n\u000B\f\r\u00A0";
    StringBuffer result = new StringBuffer(someText);

    int charIndex = 0;
    boolean whitespaceToken = false;

    while (charIndex < result.length()) {
      final boolean newWhitespaceToken = charsToReplace.indexOf(result.charAt(charIndex)) >= 0;

      if (newWhitespaceToken) {
        if (!whitespaceToken) {
          result.setCharAt(charIndex, ' ');
          charIndex++;
        } else {
          result.deleteCharAt(charIndex);
        }
      } else {
        charIndex++;
      }

      whitespaceToken = newWhitespaceToken;
    }

    return result.toString();
  }

  public static String replaceUtfQuotesWithAscii(String text) {
    text = text.replace((char) 0x2018, (char) 0x27); // left single quotation mark
    text = text.replace((char) 0x2019, (char) 0x27); // right single quotation mark
    text = text.replace((char) 0x201A, (char) 0x27); // single low-9 quotation mark
    text = text.replace((char) 0x201C, (char) 0x22); // left double quotation mark
    text = text.replace((char) 0x201D, (char) 0x22); // right double quotation mark
    text = text.replace((char) 0x201E, (char) 0x22); // double low-9 quotation mark
    text = text.replace((char) 0x2013, (char) 0x2D); // en dash
    text = text.replace((char) 0x2014, (char) 0x2D); // em dash
    text = text.replace((char) 0x2015, (char) 0x2D); // horizontal bar
    return text;
  }
}
