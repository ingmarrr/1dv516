package src.benchmark;

public record Snapshot(long duration, long mean, int reps) {

  @Override
  public String toString() {
    return asMs(duration) + ";" + asMs(mean) + ";" + reps;
  }

  public String toFmt() {
    return "Duration :" + fmtNano(duration) + " | Mean: " + fmtNano(mean) + " | Reps: " +reps;
  }

  private float asMs(long nanoTime) {
    return nanoTime / 1000f;
  }

  private String fmtNano(long nanoTime) {
    if (nanoTime < 1_000) {
      return nanoTime + " ns";
    } else if (nanoTime < 1_000_000) {
      return nanoTime / 1_000.0 + " µs";  // microseconds
    } else if (nanoTime < 1_000_000_000) {
      return nanoTime / 1_000_000.0 + " ms";  // milliseconds
    } else {
      double seconds = nanoTime / 1_000_000_000.0;
      if (seconds < 60) {
        return seconds + " s";
      } else if (seconds < 3600) {
        return seconds / 60.0 + " mins";
      } else {
        return seconds / 3600.0 + " hrs";
      }
    }
  }
}
