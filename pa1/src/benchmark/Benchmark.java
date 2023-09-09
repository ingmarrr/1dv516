package src.benchmark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import src.logging.Logger;

public class Benchmark {
  private long start;
  private long end;

  public Benchmark() {
  }

  public <T> Map<Integer, Snapshot> run(
      String name, T state,
      BiConsumer<T, Integer> setup,
      BiConsumer<T, Integer> fn,
      List<Integer> sizes,
      int repeats) {
    final Map<Integer, Snapshot> results = new HashMap<>();
    final Logger log = new Logger(name);

    final int numThreads = Runtime.getRuntime().availableProcessors(); // Use available cores
    final ExecutorService executor = Executors.newFixedThreadPool(numThreads);

    log.println("\n========================================");
    for (int sz : sizes) {
      final String szFmt = String.format("%,d", sz);
      log.println("\nSIZE :: " + szFmt + "\nREPEATS :: " + repeats);
      setup.accept(state, sz);

      ConcurrentLinkedQueue<Float> durations = new ConcurrentLinkedQueue<>();

      int repeatsPerThread = repeats / numThreads;
      List<Future<Object>> futures = IntStream.range(0, numThreads)
          .mapToObj(threadIndex -> executor.submit(() -> {
            for (int j = 0; j < repeatsPerThread; j++) {
              long startTime = System.nanoTime();
              fn.accept(state, sz);
              long endTime = System.nanoTime();
              durations.add((endTime - startTime) / 1e6f);
            }
            return null;
          }))
          .collect(Collectors.toList());

      // Wait for all threads to complete
      for (Future<Object> future : futures) {
        try {
          future.get();
        } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
        }
      }

      float duration = durations.stream().reduce(0f, Float::sum);
      final float mean = duration / repeats;

      // final int threads = 10;
      // final int chunk = sz / threads;
      // final int rem = sz % threads;

      // for (int i = 0; i < repeats; i++) {
      // fn.accept(state, sz);
      // }

      // final float duration = getDuration();
      // final float mean = duration / repeats;
      log.bench("Duration: " + duration + "ms (" + duration / 1000f + "s)");
      log.bench("Mean: " + mean + "ms (" + mean / 1000f + "s)");
      results.put(sz, new Snapshot(duration, mean, repeats));
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

  public <T> Map<Integer, Snapshot> run(
      String name, T state,
      BiConsumer<T, Integer> setup,
      Consumer<T> fn,
      List<Integer> sizes,
      int repeats) {
    return run(name, state, setup, (s, i) -> fn.accept(s), sizes, repeats);
  }

  public long getDuration() {
    return end - start;
  }

  // public float getDurationMs() {
  // return (end - start) / 1000f;
  // }

  private void start() {
    start = System.currentTimeMillis();
  }

  private void end() {
    end = System.currentTimeMillis();
  }

  private void reset() {
    start = 0;
    end = 0;
  }
}
