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

import java.lang.annotation.*;

/**
 * Marks general/basic methods/classes we may want to move to utils.
 * <p/>
 * Upon move to the dedicated package the annotation shall be removed.
 * This way we'd be able to find pending bubble-ups by searching for annotation usages.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({
    ElementType.ANNOTATION_TYPE,
    ElementType.METHOD,
    ElementType.TYPE
})
@Documented
@Util
public @interface Util {
  /**
   * Define a category/classifiers, optional, default is empty
   *
   * @return category/classifier, space separated
   */
  String value() default "";
}