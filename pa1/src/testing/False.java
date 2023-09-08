package src.testing;

public class False {
  final String name;
  final boolean expected;

  public False(String name, boolean expected) {
    this.name = name;
    this.expected = expected;
  }

  public boolean passed() {
    return this.expected == false;
  }

  public static False of(String name, boolean expected) {
    return new False(name, expected);
  }
}
