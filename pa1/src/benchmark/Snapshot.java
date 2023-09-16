package src.benchmark;

public record Snapshot(long duration, long mean, int reps) {

  @Override
  public String toString() {
    return asMs(duration) + ";" + asMs(mean) + ";" + reps;
  }

  public String toStringSecs() {
    return asSecs(duration) + ";" + asSecs(mean) + ";" + reps;
  }

  public String toFmt() {
    final String fmt = "%-20s";
    return String.format(fmt, asMs(duration)) + String.format(fmt, asMs(mean)) + String.format(fmt, reps);
  }

  public static String headers() {
    final String fmt = "%-20s";
    return String.format(fmt, "duration") + String.format(fmt, "mean") + String.format(fmt, "reps");
  }

  private float asMs(long nanoTime) {
    return nanoTime / 1_000_000f;
  }

  private float asSecs(long nanoTime) {
    return asMs(nanoTime) / 1_000f;
  }

  public static String fmtNano(long nanoTime) {
    if (nanoTime < 1_000) {
      return nanoTime + " ns";
    } else if (nanoTime < 1_000_000) {
      return nanoTime / 1_000.0 + " µs"; // microseconds
    } else if (nanoTime < 1_000_000_000) {
      return nanoTime / 1_000_000.0 + " ms"; // milliseconds
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
