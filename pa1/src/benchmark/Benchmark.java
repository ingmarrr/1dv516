package src.benchmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import src.logging.Logger;

public class Benchmark {
  private long start;
  private long end;
  private Logger log;

  public Benchmark() {
  }

  public <T> Map<Integer, Snapshot> bench(
          String name, String filename,
          Function<Integer, T> setup,
          Consumer<T> fn,
          List<Integer> sizes,
          int reps) {
    log = new Logger(name);
    log.println("\n========================================");
    final Map<Integer, Snapshot> res = benchAll(setup, fn, sizes, reps);
    log.println("\n . . . . . . . . . . . . . . . . . . . .");
    for (int sz : sizes) {
      final String fmt = String.format("%-20s", res.get(sz).toFmt());
      final String szFmt = String.format("%-10s", String.format("%,d", sz));
      log.bench(szFmt + "\t" + fmt);
    }
    log.println("\n . . . . . . . . . . . . . . . . . . . .");
    log.println("\n========================================");

    final String path = filename + ".csv";
    log.fprintln(path, "size; duration; mean; reps");
    for (int sz : sizes) {
      log.aprintln(path, sz + ";" + res.get(sz).toFmt());
    }
    return res;
  }

  private <T> Map<Integer, Snapshot> benchAll(
          Function<Integer, T> setup,
          Consumer<T> fn,
          List<Integer> sizes,
          int reps) {
    final Map<Integer, Snapshot> results = new HashMap<>();
    final int threads = Runtime.getRuntime().availableProcessors();
    final ExecutorService executor = Executors.newFixedThreadPool(threads);

    for (final int sz : sizes) {
      final Snapshot st = benchSize(setup, fn, sz, reps, threads, executor);
      results.put(sz, st);
    }

    executor.shutdown();

    return results;
  }

  private <T> Snapshot benchSize(
          Function< Integer, T> setup,
          Consumer<T> fn,
          int size,
          int reps,
          int nrThreads,
          ExecutorService executor
  ) {
    log.println("\nSIZE :: " + String.format("%,d", size) + "\nREPS :: " + reps);
    final int repsPerThread = reps / nrThreads;
    final AtomicReference<ConcurrentLinkedQueue<Long>> durations = new AtomicReference<>();
    List<Future<Object>> futures = IntStream.range(0, nrThreads)
            .mapToObj(threadIndex -> executor.submit(() -> {
              for (int j = 0; j < repsPerThread; j++) {
                T state = setup.apply(size);
                start();
                fn.accept(state);
                end();
                durations.get().add(getDurationNs());
              }
              return null;
            }))
            .toList();

    for (Future<Object> future : futures) {
      try {
        future.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    final long duration = durations.get().stream().reduce(0L, Long::sum);
    final long mean = duration / reps;
    return new Snapshot(duration, mean, reps);
  }

  public long getDurationNs() {
    return end - start;
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
