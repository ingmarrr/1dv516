package logging;

public enum Level {
  Debug,
  Info,
  Warn,
  Success,
  Error;

  @Override
  public String toString() {
    return switch (this) {
      case Debug -> "debug";
      case Info -> "info";
      case Warn -> "warn";
      case Success -> "success";
      case Error -> "error";
    };
  }

  public String toEmoji() {
    return switch (this) {
      case Debug -> "🐞";
      case Info -> "💡";
      case Warn -> "❗";
      case Success -> "✅ ";
      case Error -> "❌ ";
    };
  }
}
