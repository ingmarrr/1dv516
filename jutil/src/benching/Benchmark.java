package benching;

import logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class Benchmark {
  private Logger log = Logger.builder()
      .mode(logging.Mode.Bench)
      .emoji(true)
      .build();

  public <T> void bench(
      String name,
      String filename,
      Function<Integer, T> setup,
      Consumer<T> func,
      List<Integer> sizes,
      int reps) {
    final Map<Integer, Snapshot> sts = new java.util.HashMap<>();
    log.println("\n======================================");
    log.info("Benchmarking %s", name);
    log.info("size\t" + Snapshot.headers());

    for (int sz : sizes) {
      final T state = setup.apply(sz);
      final Snapshot st = benchSize(state, func, sz, reps);
      log.info("%d\t%s", sz, st);
      sts.put(sz, st);
    }
  }

  private <T> Snapshot benchSize(T state, Consumer<T> func, int size, int reps) {
    final List<Long> times = new ArrayList<Long>(reps);
    for (int i = 0; i < reps; i++) {
      final long start = System.nanoTime();
      func.accept(state);
      final long end = System.nanoTime();
      times.add(end - start);
    }
    final long duration = times.stream().mapToLong(Long::longValue).sum();
    final long avg = duration / reps;
    final long median = times.get(times.size() / 2);
    final long min = times.stream().mapToLong(Long::longValue).min().getAsLong();
    final long max = times.stream().mapToLong(Long::longValue).max().getAsLong();
    final long stdDev = (long) Math.sqrt(times.stream().mapToDouble(t -> Math.pow(t - avg, 2)).sum() / reps);

    return new Snapshot(duration, avg, median, min, max, stdDev);
  }
}
