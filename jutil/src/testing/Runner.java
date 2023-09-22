package testing;

import java.lang.reflect.Method;

public class Runner {
  public static void run(Class<?> clazz) {
    for (Method met : clazz.getDeclaredMethods()) {
      if (met.isAnnotationPresent(Unit.class)) {
        try {
          met.invoke(null);
        } catch (Exception e) {
          e.getMessage();
        }
      }
    }
  }
}