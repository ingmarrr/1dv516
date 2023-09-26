package testing;

import logging.Logger;
import logging.Mode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

  private static final Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(false)
      .build();

  private static File root;

  public static void main(String[] args) {
    log.info("Running Tests...");
    File dir = new File(args[0]);
    root = dir;
    final List<String> fails = new ArrayList<>();
    try {
      List<Class<?>> classes = getClasses("");
      for (Class<?> clazz : classes) {
        run(clazz);
      }
    } catch (Exception e) {
      var parts = e.getMessage().split("\\.");
      if (parts.length > 0) {
        e.printStackTrace();

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

  private static List<Class<?>> getClasses(String name) throws IOException, ClassNotFoundException {
    var dir = new File(root.getCanonicalPath() + name);
    List<Class<?>> classes = new ArrayList<>();
    File[] files = dir.listFiles();
    if (files == null) return classes;

    URL[] classpath = {dir.toURI().toURL()};
    try (URLClassLoader classLoader = new URLClassLoader(classpath)) {
      for (File file : files) {
        if (file.isDirectory()) {
          classes.addAll(getClasses(file.getCanonicalPath() .replace(root.getCanonicalPath(), "")));
          continue;
        }

        String className = file.getCanonicalPath()
            .replace(root.getCanonicalPath(), "")
            .replace(".class", "")
            .replace(File.separator, ".");
        if (className.startsWith(".")) {
          className = className.substring(1);
        }
        Class<?> clazz = classLoader.loadClass(className);
        classes.add(clazz);
      }
    }

    return classes;

  }

  public static void run(Class<?> clazz) {
    try {
      var instance = clazz.getDeclaredConstructor().newInstance();
      for (Method met : clazz.getDeclaredMethods()) {
        if (met.isAnnotationPresent(Unit.class)) {
          try {
            if (Modifier.isStatic(met.getModifiers())) {
                met.invoke(null);
              return;
            }
            met.invoke(instance);
          } catch (Exception e) {
            log.error("Test `" + met.getName() + "` failed.");
          } finally {
            log.success("Test `" + met.getName() + "` passed.");
          }
        }
      }

    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}