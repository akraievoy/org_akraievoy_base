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

package org.akraievoy.base.swing;

import java.awt.*;

public class Swing {
  protected Swing() {
    //	sealed
  }

  /**
   * After packing a Frame or Dialog, center it on the screen.
   *
   * @param w to center
   */
  public static void centerOnScreen(Window w) {
    //  TODO this does not properly work with multiple screens
    final Dimension us = w.getSize(),
        them = Toolkit.getDefaultToolkit().getScreenSize();
    int newX = (them.width - us.width) / 2;
    int newY = (them.height - us.height) / 2;
    w.setLocation(newX, newY);
  }
}
