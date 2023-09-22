package benching;

import logging.Logger;
import logging.Mode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class BenchRunner {

  private static final Logger log = Logger.builder()
      .mode(Mode.Bench)
      .emoji(false)
      .build();

  public static void main(String[] args) {
    log.info("Running Benchmarks...");
    File dir = new File(args[0]);
    final List<String> fails = new ArrayList<>();
    try {
      List<Class<?>> classes = getClasses(dir);
      for (Class<?> clazz : classes) {
        run(clazz);
      }
    } catch (Exception e) {
      var parts = e.getMessage().split("\\.");
      if (parts.length > 0) {
        fails.add(parts[parts.length - 1]);
      } else {
        log.error(e.getMessage());
      }
    }

    final int failedTests = fails.size();
    if (failedTests > 0) {
      log.println();
      for (String cls : fails) {
        log.error(cls + " Failed");
      }
      log.error(failedTests + " Tests failed.");
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
    for (Method met : clazz.getDeclaredMethods()) {
      if (met.isAnnotationPresent(Bench.class)) {
        try {
          met.invoke(null);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }
}
