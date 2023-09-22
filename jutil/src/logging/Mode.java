package logging;

public enum Mode {
  Main,
  Bench,
  Test;

  @Override
  public String toString() {
    return switch (this) {
      case Main -> "main";
      case Test -> "test";
      case Bench -> "bench";
    };
  }

  public String toEmoji() {
    return switch (this) {
      case Main -> "🚀";
      case Test -> "🧪";
      case Bench -> "⏱️";
    };
  }
}
