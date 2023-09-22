package testing;

import logging.Logger;
import logging.Mode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

  private static final Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(false)
      .build();

  public static void main(String[] args) {
    log.info("Running Tests...");
    File dir = new File(args[0]);
    try {
      List<Class<?>> classes = getClasses(dir);
      for (Class<?> clazz : classes) {
        run(clazz);
      }
    } catch (Exception e) {
      var parts = e.getMessage().split("\\.");
      if (parts.length > 0) {
        log.error("Benchmark '" + parts[parts.length - 1] + "' failed with: " + e.getMessage());
      } else {
        log.error("Benchmark failed with: " + e.getMessage());
      }
    }

  }

  private static List<Class<?>> getClasses(File dir) throws IOException, ClassNotFoundException {
    List<Class<?>> classes = new ArrayList<>();
    File[] files = dir.listFiles();
    if (files == null) return classes;

    URL[] classpath = {dir.toURI().toURL()};
    try (URLClassLoader classLoader = new URLClassLoader(classpath)) {
      for (File file : files) {
        if (file.isDirectory()) {
          classes.addAll(getClasses(file));
        } else if (file.getName().endsWith(".class")) {
          String className = file.getPath()
              .replace(".class", "")
              .replace(File.separator, ".");  // Convert file path to package name
          Class<?> clazz = classLoader.loadClass(className);
          classes.add(clazz);
        }
      }
    }
    return classes;

  }

  public static void run(Class<?> clazz) {
    log.info("Testing: " + clazz.getSimpleName());
    for (Method met : clazz.getDeclaredMethods()) {
      if (met.isAnnotationPresent(Unit.class)) {
        try {
          met.invoke(null);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }
}