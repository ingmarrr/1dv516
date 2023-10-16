package benching;

import logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static range.Range.range;

public class Benchmark {

  private final Logger log;
  public Benchmark() {
    log = Logger.builder()
        .mode(logging.Mode.Bench)
        .emoji(true)
        .build();
  }

  public Benchmark(String dir) {
    log = Logger.builder()
        .mode(logging.Mode.Bench)
        .path(dir)
        .emoji(true)
        .build();
  }

  private record Pair<T, R>(T first, R second) {

  }

  public <T> void bench(
      String name,
      String filename,
      Function<Integer, T> setup,
      Function<T, Integer> func,
      int size,
      int times,
      int reps
  ) {
    final Map<Integer, Pair<Snapshot, Integer>> sts = new java.util.HashMap<>();
    log.println("\n+======================================+");
    log.info(String.format("Benchmarking %s", name));
    log.info("Size\tResult\t" + Snapshot.headers().replace(";", "\t"));

    for (int sz : range(times)) {
      final T state = setup.apply(size);
      final Pair<Snapshot, Integer> st = benchSize(state, func, reps);
      log.info(sz + (sz < 10000 ? "  " : "") + "\t" + st.second + "\t" + st.first.toFmt());
      sts.put(sz, st);
    }
    log.println("\n+======================================+");
    final String path = filename + "_bench.csv";
    log.info(path);
    log.fprintln(path, "Size;Result;" + Snapshot.headers());
    for (int sz : range(times)) {
      var res = log.aprintln(path, sz + ";" + sts.get(sz).second + ";" + sts.get(sz).first.toString());
      if (res.equals(Logger.Result.Err)) log.error("Error writing to file");
    }
  }

  public <T> void bench(
          String name,
          String filename,
          Function<Integer, T> setup,
          Function<T, Integer> func,
          List<Integer> sizes,
          int reps
  ) {
    final Map<Integer, Pair<Snapshot, Integer>> sts = new java.util.HashMap<>();
    log.println("\n+======================================+");
    log.info(String.format("Benchmarking %s", name));
    log.info("Size\tResult\t" + Snapshot.headers().replace(";", "\t"));

    for (int sz : sizes) {
      final T state = setup.apply(sz);
      final Pair<Snapshot, Integer> st = benchSize(state, func, reps);
      log.info(sz + (sz < 10000 ? "  " : "") + "\t" + st.second + "\t" + st.first.toFmt());
      sts.put(sz, st);
    }
    log.println("\n+======================================+");
    final String path = filename + "_bench.csv";
    log.info(path);
    log.fprintln(path, "Size;Result;" + Snapshot.headers());
    for (int sz : sizes) {
      var res = log.aprintln(path, sz + ";" + sts.get(sz).second + ";" + sts.get(sz).first.toString());
      if (res.equals(Logger.Result.Err)) log.error("Error writing to file");
    }
  }

  private <T> Pair<Snapshot, Integer> benchSize(T state, Function<T, Integer> func, int reps) {
    final List<Long> times = new ArrayList<Long>(reps);
    int avgRes = 0;
    for (int i : range(reps)) {
      final long start = System.nanoTime();
      avgRes += func.apply(state);
      final long end = System.nanoTime();
      times.add(end - start);
    }
    final long duration = times.stream().mapToLong(Long::longValue).sum();
    final long avg = duration / reps;
    final long min = times.stream().mapToLong(Long::longValue).min().orElse(0);
    final long max = times.stream().mapToLong(Long::longValue).max().orElse(0);
    final double stdDev = Math.sqrt(times.stream().mapToLong(Long::longValue).mapToDouble(v -> Math.pow(v - avg, 2)).sum());
    return new Pair<>(new Snapshot(duration, avg, min, max, (long) stdDev), avgRes / reps);
  }

  public <T> void bench(
      String name,
      String filename,
      Function<Integer, T> setup,
      Consumer<T> func,
      List<Integer> sizes,
      int reps) {
    final Map<Integer, Snapshot> sts = new java.util.HashMap<>();
    log.println("\n+======================================+");
    log.info(String.format("Benchmarking %s", name));
    log.info("Size;\t" + Snapshot.headers().replace(";", "\t"));

    for (int sz : sizes) {
      final T state = setup.apply(sz);
      final Snapshot st = benchSize(state, func, reps);
      log.info(sz + (sz < 1000 ? " " : "") + "\t" + st.toFmt());
      sts.put(sz, st);
    }
    log.println("\n+======================================+");
    final String path = filename + "_bench.csv";
    log.info(path);
    log.fprintln(path, "Size; " + Snapshot.headers());
    for (int sz : sizes) {
      var res = log.aprintln(path, sz + ";" + sts.get(sz).toString());
      if (res.equals(Logger.Result.Err)) log.error("Error writing to file");
    }
  }

  private <T> Snapshot benchSize(T state, Consumer<T> func, int reps) {
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
