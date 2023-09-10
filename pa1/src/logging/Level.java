package src.logging;

public enum Level {
  DEBUG,
  TEST,
  BENCH,
  INFO,
  WARN,
  ERROR;

  public static String toString(Level l) {
    return switch (l) {
      case DEBUG -> "DEBUG";
      case TEST -> "TEST";
      case BENCH -> "BENCH";
      case INFO -> "INFO";
      case WARN -> "WARN";
      case ERROR -> "ERROR";
    };
  }
}
