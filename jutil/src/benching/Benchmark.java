package benching;

import logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static range.Range.range;

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
    log.info(String.format("Benchmarking %s", name));
    log.info("size\t" + Snapshot.headers());

    for (int sz : sizes) {
      final T state = setup.apply(sz);

    }
  }

  private <T> Snapshot benchSize(T state, Consumer<T> func, int size, int reps) {
    final List<Long> times = new ArrayList<Long>(reps);
    for (int i : range(reps)) {
      final long start = System.nanoTime();
      func.accept(state);
      final long end = System.nanoTime();
      times.add(end - start);
    }
    final long duration = times.stream().mapToLong(Long::longValue).sum();
    final long avg = duration / reps;
    final long min = times.stream().mapToLong(Long::longValue).min().orElse(0);
    final long max = times.stream().mapToLong(Long::longValue).max().orElse(0);
    final double stdDev = Math.sqrt(times.stream().mapToLong(Long::longValue).mapToDouble(v -> Math.pow(v - avg, 2)).sum());
    return new Snapshot(duration, avg, min, max, (long) stdDev);
  }
}
