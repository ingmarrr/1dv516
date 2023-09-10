package src.benchmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import src.logging.Logger;

public class Benchmark {
  private Logger log;

  public Benchmark() {
  }

  public <T> void bench(
      String name,
      String filename,
      Function<Integer, T> setup,
      Consumer<T> fn,
      List<Integer> sizes,
      int reps) {
    log = new Logger(name);
    log.println("\n========================================");
    log.println("Benchmarking " + name);
    log.bench("size\t" + Snapshot.headers());
    final Map<Integer, Snapshot> res = benchAll(setup, fn, sizes, reps);
    log.println("\n========================================");

    final String path = filename + "_bench.csv";
    log.fprintln(path, "size; duration; mean; reps");
    for (int sz : sizes) {
      log.aprintln(path, sz + ";" + res.get(sz).toString());
    }
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
      Function<Integer, T> setup,
      Consumer<T> fn,
      int size,
      int reps,
      int nrThreads,
      ExecutorService executor) {
    final int repsPerThread = reps / nrThreads;
    final ConcurrentLinkedQueue<Long> durations = new ConcurrentLinkedQueue<>();
    List<Future<Object>> futures = IntStream.range(0, nrThreads)
        .mapToObj(threadIndex -> executor.submit(() -> {
          for (int j = 0; j < repsPerThread; j++) {
            T state = setup.apply(size);
            final long start = System.nanoTime();
            fn.accept(state);
            final long end = System.nanoTime();
            final long duration = end - start;
            durations.add(duration);
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

    final long duration = durations.stream().reduce(0L, Long::sum);
    final long mean = duration / reps;
    final Snapshot st = new Snapshot(duration, mean, reps);
    log.bench(size + size < 1000 ? "\t" : "" + "\t" + st.toFmt());
    return st;
  }
}
