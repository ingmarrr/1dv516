package benching;

import java.util.Locale;

public record Snapshot(
    long duration,
    long mean,
    long min,
    long max,
    long stdDev) {

  @Override
  public String toString() {
    return String.format(
        Locale.US,
        "%.6f; %.6f; %.6f; %.6f; %.6f",
        asMs(duration), asMs(mean), asMs(min), asMs(max), asMs(stdDev));
  }

  public String toFmt() {
    final String fmt = "%-20s";
    return String.format(
        "Duration: %s\tMean: %s\tMin: %s\tMax: %s\tStdDev: %s",
        fmtNano(duration, fmt), fmtNano(mean, fmt), fmtNano(min, fmt), fmtNano(max, fmt),
        fmtNano(stdDev));
  }

  public static String headers() {
    final String fmt = "%-20s";
    return String.format(
        "%s;%s;%s;%s;%s",
        "Duration", "Mean", "Median", "Min", "Max", "StdDev");
  }

  public static Snapshot fromString(String str) {
    String[] parts = str.split("; ");
    return new Snapshot(
        Long.parseLong(parts[0]),
        Long.parseLong(parts[1]),
        Long.parseLong(parts[2]),
        Long.parseLong(parts[3]),
        Long.parseLong(parts[4]));
  }

  public static String fmtNano(long nano) {
    return switch (String.valueOf(nano).length()) {
      case 1, 2, 3 -> String.format("%d ns", nano);
      case 4, 5, 6 -> String.format("%d μs", nano / 1_000);
      case 7, 8, 9 -> String.format("%d ms", nano / 1_000_000);
      case 10, 11, 12 -> String.format("%d s", nano / 1_000_000_000);
      default -> String.format("%d ms", nano / 1_000_000_000);
    };
  }

  public static String fmtNano(long nano, String fmt) {
    return switch (String.valueOf(nano).length()) {
      case 1, 2, 3 -> String.format(fmt, nano, "ns");
      case 4, 5, 6 -> String.format(fmt, nano / 1_000, "μs");
      case 7, 8, 9 -> String.format(fmt, nano / 1_000_000, "ms");
      case 10, 11, 12 -> String.format(fmt, nano / 1_000_000_000, "s");
      default -> String.format(fmt, nano / 1_000_000_000, "s");
    };
  }

  private double asMs(long nano) {
    return nano / 1_000_000.0;
  }

}
