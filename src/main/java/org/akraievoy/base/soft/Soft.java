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

package org.akraievoy.base.soft;

/**
 * Precision-aware comparisons.
 */
public class Soft {
  /**
   * Default grain to be used in comparisons.
   */
  public final double e;

  public static final Soft MILLI = new Soft(1e-3);
  public static final Soft MICRO = new Soft(1e-6);
  public static final Soft NANO = new Soft(1e-9);
  public static final Soft PICO = new Soft(1e-12);

  public Soft(final double e) {
    this.e = e;
  }

  /**
   * Returns <code>true</code> if and only if <code>Abs(a) &lt; Abs(b)-grain</code>.
   *
   * @param a value to be compared.
   * @param b value to be compared.
   * @return <code>true</code> if and only if <code>Abs(a) &lt; Abs(b)-grain</code>.
   */
  public boolean lessAbs(final double a, final double b) {
    return Math.abs(a) < Math.abs(b) - e;
  }

  /**
   * Returns <code>true</code> if and only if <code>Abs(a) &gt; Abs(b)+grain</code>.
   *
   * @param a value to be compared.
   * @param b value to be compared.
   * @return <code>true</code> if and only if <code>Abs(a) &gt; Abs(b)+grain</code>.
   */
  public boolean greaterAbs(final double a, final double b) {
    return Math.abs(a) > Math.abs(b) + e;
  }

  /**
   * Returns <code>true</code> if and only if <code>a-b &lt; -grain</code>.
   *
   * @param a value to be compared.
   * @param b value to be compared.
   * @return <code>true</code> if and only if <code>a-b &lt; -grain</code>.
   */
  public boolean less(final double a, final double b) {
    return negative(a - b);
  }

  /**
   * Returns <code>true</code> if and only if <code>a-b &gt; grain</code>.
   *
   * @param a value to be compared.
   * @param b value to be compared.
   * @return <code>true</code> if and only if <code>a-b &gt; grain</code>.
   */
  public boolean greater(final double a, final double b) {
    return positive(a - b);
  }

  /**
   * Returns <code>true</code> if and only if <code>Abs(a) &lt; threshold</code>.
   *
   * @param a value to be compared.
   * @return <code>true</code> if and only if <code>Abs(a) &lt; threshold</code>.
   */
  public boolean zero(final double a) {
    return Math.abs(a) < e;
  }

  /**
   * Returns <code>true</code> if and only if <code>Abs(a) &gt; threshold</code>.
   *
   * @param a value to be compared.
   * @return <code>true</code> if and only if <code>Abs(a) &gt; threshold</code>.
   */
  public boolean notZero(final double a) {
    return Math.abs(a) > e;
  }

  /**
   * Returns <code>true</code> if and only if <code>a &lt; -grain</code>.
   *
   * @param a value to be compared.
   * @return <code>true</code> if and only if <code>a &lt; -grain</code>.
   */
  public boolean negative(final double a) {
    return a < -e;
  }

  /**
   * Returns <code>true</code> if and only if <code>a &gt; grain</code>.
   *
   * @param a value to be compared.
   * @return <code>true</code> if and only if <code>a &gt; grain</code>.
   */
  public boolean positive(final double a) {
    return a > e;
  }

  /**
   * Returns <code>true</code> if and only if <code>Abs(a-b) &lt; threshold</code>.
   *
   * @param a value to be compared.
   * @return <code>true</code> if and only if <code>Abs(a-b) &lt; threshold</code>.
   */
  public boolean equal(final double a, final double b) {
    return zero(a - b);
  }
}
