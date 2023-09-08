package src.benchmark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import src.logging.Logger;

public class Benchmark {
  // private Logger log = new Logger("BENCHMARK");
  private long start;
  private long end;
  // private List<Snapshot> snapshots;
  // private long lastSnapshot;

  public Benchmark() {
    // snapshots = new ArrayList<Snapshot>();
  }

  public <T> Map<Integer, Float> run(
      String name, T state,
      BiConsumer<T, Integer> setup,
      BiConsumer<T, Integer> fn,
      int[] sizes,
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

  // public <T> void run(
  // T state, BiConsumer<T, Integer> fn,
  // int reps,
  // int nrSnaps) {
  // start();
  // for (int i = 0; i < reps; i++) {
  // fn.accept(state, i);

  // }
  // end();
  // }

  public void start() {
    start = System.nanoTime();
    // lastSnapshot = start;
  }

  public void end() {
    end = System.nanoTime();
  }

  public void reset() {
    start = 0;
    end = 0;
    // snapshots.clear();
  }

  // public void makeSnapshot(int iteration) {
  // final long curr = System.currentTimeMillis();
  // snapshots.add(new Snapshot(curr - lastSnapshot, iteration));
  // lastSnapshot = curr;
  // }

  public long getDuration() {
    return end - start;
  }

  /*
   * Get duration as seconds
   */
  public float getDurationMs() {
    return (end - start) / 1000f;
  }

  // public List<Snapshot> getSnapshots() {
  // return snapshots;
  // }

  // public float getMean() {
  // long sum = 0;
  // for (Snapshot snapshot : snapshots) {
  // sum += snapshot.getTime();
  // }
  // return sum / snapshots.size();
  // }
}
