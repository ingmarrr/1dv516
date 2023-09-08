package src.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Benchmark {
  private long start;
  private long end;
  private List<Snapshot> snapshots;
  private long lastSnapshot;

  public Benchmark() {
    snapshots = new ArrayList<Snapshot>();
  }

  public <T> void run(
      T state, BiConsumer<T, Integer> fn,
      int reps,
      int nrSnaps) {
    start();
    for (int i = 0; i < reps; i++) {
      fn.accept(state, i);
      if (i % Math.floorDiv(reps, nrSnaps) == 0) {
        // System.out.println("SNAPSHOT :: " + i);
        makeSnapshot(i);
      }
    }
    end();
  }

  public void start() {
    start = System.currentTimeMillis();
    lastSnapshot = start;
  }

  public void end() {
    end = System.currentTimeMillis();
  }

  public void reset() {
    start = 0;
    end = 0;
    snapshots.clear();
  }

  public void makeSnapshot(int iteration) {
    final long curr = System.currentTimeMillis();
    snapshots.add(new Snapshot(curr - lastSnapshot, iteration));
    lastSnapshot = curr;
  }

  public long getDuration() {
    return end - start;
  }

  /*
   * Get duration as seconds
   */
  public float getDurationS() {
    return (end - start) / 1000f;
  }

  public List<Snapshot> getSnapshots() {
    return snapshots;
  }

  public float getMean() {
    long sum = 0;
    for (Snapshot snapshot : snapshots) {
      sum += snapshot.getTime();
    }
    return sum / snapshots.size();
  }
}
