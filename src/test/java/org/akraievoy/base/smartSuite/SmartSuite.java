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

package org.akraievoy.base.smartSuite;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.akraievoy.base.Die;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmartSuite extends TestSuite {
  private final String root;
  private final List<Class> excludedClasses = new ArrayList<Class>();
  private final JavaSourceAndDirectoryFilter filter = new JavaSourceAndDirectoryFilter();
  private static final String DEFAULT_ROOT = "src/test/java";
  private final char separator = System.getProperty("file.separator").charAt(0);

  public static Test suite() {
    return new SmartSuite();
  }

  public SmartSuite() {
    this(DEFAULT_ROOT, new Class[]{});
  }

  public SmartSuite(final String root, final Class excludedClass) {
    this(root, new Class[]{excludedClass});
  }

  public SmartSuite(final String root, final Class[] excludedClasses) {
    this.root = root;
    this.excludedClasses.add(getClass());
    this.excludedClasses.addAll(Arrays.asList(excludedClasses));
    collectTests(root);
  }

  protected void collectTests(final String root) {
    processDirectory(new File(root));
  }

  private void processDirectory(final File directory) {
    Die.ifFalse(directory.getPath() + " is directory", directory.isDirectory());
    final File[] files = directory.listFiles(filter);
    for (int i = 0; i < files.length; i++) {
      final File file = files[i];
      if (file.isDirectory()) {
        processDirectory(file);
      } else {
        try {
          final Class clazz = Class.forName(buildClassName(file));
          if (notExcluded(clazz) && isTest(clazz)) {
            addTestSuite(clazz);
          }
        } catch (ClassNotFoundException e) {
          System.out.println("WARN: Class for '" + file.getAbsolutePath() + "' not found!");
        }
      }
    }
  }

  private boolean isTest(final Class clazz) {
    return Test.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers());
  }

  private boolean notExcluded(final Class clazz) {
    return !excludedClasses.contains(clazz);
  }

  private String buildClassName(final File file) {
    return file.getAbsolutePath().
        substring(getClassNameStart(file), getClassNameEnd(file)).replace(separator, '.');
  }

  private int getClassNameEnd(final File file) {
    return file.getAbsolutePath().length() - ".java".length();
  }

  private int getClassNameStart(final File file) {
    return file.getAbsolutePath().indexOf(root) + root.length() + 1;
  }


  private static final class JavaSourceAndDirectoryFilter implements FileFilter {
    public boolean accept(final File file) {
      return file.isDirectory() || isJavaFile(file);

    }

    private boolean isJavaFile(final File file) {
      return file.getAbsolutePath().endsWith(".java");
    }
  }
}
