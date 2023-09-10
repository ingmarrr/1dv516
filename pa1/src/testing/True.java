package src.testing;

public class True {
  final String name;
  final boolean expected;

  public True(String name, boolean expected) {
    this.name = name;
    this.expected = expected;
  }

  public static True of(String name, boolean expected) {
    return new True(name, expected);
  }
}
