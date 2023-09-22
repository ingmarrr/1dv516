package benching;

import logging.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import benching.Snapshot;

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

    return Snapshot.from(times);
  }
}
