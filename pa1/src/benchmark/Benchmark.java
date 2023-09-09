package src.benchmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import src.logging.Logger;

public class Benchmark {
  private long start;
  private long end;

  public Benchmark() {
  }

  public <T> Map<Integer, Float> run(
      String name, T state,
      BiConsumer<T, Integer> setup,
      BiConsumer<T, Integer> fn,
      List<Integer> sizes,
      int repeats) {
    final Map<Integer, Float> results = new HashMap<>();
    final Logger log = new Logger(name);
    log.println("\n========================================");
    for (int sz : sizes) {
      final String szFmt = String.format("%,d", sz);
      log.println("\nSIZE :: " + szFmt + "\nREPEATS :: " + repeats);
      setup.accept(state, sz);

      start();
      for (int i = 0; i < repeats; i++) {
        fn.accept(state, sz);
      }
      end();
      final float duration = getDurationMs();
      final float mean = duration / repeats;
      log.bench("Duration: " + duration + "ms");
      log.bench("Mean: " + mean + "ms");
      results.put(sz, duration);
      reset();
    }
    log.println("\n========================================\n");
    for (int sz : sizes) {
      final String fmt = String.format("%-10s", results.get(sz));
      final String szFmt = String.format("%-10s", String.format("%,d", sz));
      log.bench(szFmt + "\t" + fmt);
    }
    log.println("\n========================================\n");
    return results;
  }

  public long getDuration() {
    return end - start;
  }

  public float getDurationMs() {
    return (end - start) / 1000f;
  }

  private void start() {
    start = System.nanoTime();
  }

  private void end() {
    end = System.nanoTime();
  }

  private void reset() {
    start = 0;
    end = 0;
  }
}
