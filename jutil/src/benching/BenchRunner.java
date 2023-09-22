package benching;

import java.lang.reflect.Method;

public class BenchRunner {

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
