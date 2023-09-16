package src.benchmark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import src.logging.Logger;

public class Benchmark {
  private Logger log;

  public Benchmark() {
  }

  public <T> void benchST(
      String name,
      String filename,
      Function<Integer, T> setup,
      Consumer<T> fn,
      List<Integer> sizes,
      int reps) {
    final Map<Integer, Snapshot> res = new HashMap<>();
    log = new Logger(name);
    log.println("\n========================================");
    log.println("Benchmarking " + name);
    log.bench("size\t" + Snapshot.headers());

    for (int sz : sizes) {
      final T state = setup.apply(sz);
      final Snapshot st = benchSizeST(state, fn, sz, reps);
      log.bench(sz + (sz < 1000 ? " " : "") + "\t" + st.toFmt());
      res.put(sz, st);
    }

    log.println("\n========================================");
    final String path = filename + "_bench.csv";
    log.fprintln(path, "size; duration; mean; reps");
    for (int sz : sizes) {
      log.aprintln(path, sz + ";" + res.get(sz).toString());
    }
    final String pathSecs = filename + "_bench_secs.csv";
    log.fprintln(pathSecs, "size; duration; mean; reps");
    for (int sz : sizes) {
      log.aprintln(pathSecs, sz + ";" + res.get(sz).toString());
    }
  }

  public <T> Snapshot benchSizeST(T state, Consumer<T> fn, int size, int reps) {
    final List<Long> durations = IntStream.range(0, reps)
        .mapToObj(i -> {
          final long start = System.nanoTime();
          fn.accept(state);
          final long end = System.nanoTime();
          return end - start;
        })
        .toList();

    final long duration = durations.stream().reduce(0L, Long::sum);
    final long mean = duration / reps;
    final Snapshot st = new Snapshot(duration, mean, reps);
    return st;
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
    final String pathSecs = filename + "_bench_secs.csv";
    log.fprintln(pathSecs, "size; duration; mean; reps");
    for (int sz : sizes) {
      log.aprintln(pathSecs, sz + ";" + res.get(sz).toStringSecs());
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
    T state = setup.apply(size);
    final Semaphore sem = new Semaphore(nrThreads);
    final int repsPerThread = reps < nrThreads ? 1 : reps / nrThreads;
    final ConcurrentLinkedQueue<Long> durations = new ConcurrentLinkedQueue<>();
    List<Future<Object>> futures = IntStream.range(0, nrThreads)
        .mapToObj(threadIndex -> executor.submit(() -> {
          for (int j = 0; j < repsPerThread; j++) {
            final long start = System.nanoTime();
            fn.accept(state);
            final long end = System.nanoTime();
            final long duration = end - start;
            sem.acquire();
            durations.add(duration);
            sem.release();
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
    log.bench(size + (size < 1000 ? " " : "") + "\t" + st.toFmt());
    return st;
  }
}
