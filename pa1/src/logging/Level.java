package src.logging;

public enum Level {
  DEBUG,
  INFO,
  WARN,
  ERROR;

  public static Level fromString(String s) {
    return switch (s) {
      case "DEBUG" -> DEBUG;
      case "INFO" -> INFO;
      case "WARN" -> WARN;
      case "ERROR" -> ERROR;
      default -> throw new IllegalArgumentException("Unknown level: " + s);
    };
  }

  public static String toString(Level l) {
    return switch (l) {
      case DEBUG -> "DEBUG";
      case INFO -> "INFO";
      case WARN -> "WARN";
      case ERROR -> "ERROR";
    };
  }
}
