package testing;

import logging.Logger;
import logging.Mode;
import logging.Result;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

  private static final Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .modeEmoji(false)
      .build();

  private static File root;

  public static void main(String[] args) {
    log.info("Running Tests...");
    root = new File(args[0]);
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
      var successes = new ArrayList<>();
      var fails = new ArrayList<>();
      for (Method met : clazz.getDeclaredMethods()) {
        if (met.isAnnotationPresent(Unit.class)) {
          try {
            if (Modifier.isStatic(met.getModifiers())) {
                met.invoke(null);
                return;
            }
            met.invoke(instance);
            successes.add("Test `" + met.getName() + "` passed.");
//            log.success("Test `" + met.getName() + "` passed.");
          } catch (Exception e) {
            fails.add("Test `" + met.getName() + "` failed.");
//            log.error("Test `" + met.getName() + "` failed.");
          }
        }
      }
      log.println();
      for (var res : successes) {
        log.success(res);
      }
      for (var fail : fails) {
        log.error(fail);
      }

    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}