package src.testing;

public class Case {
  final public String name;
  final public boolean expected;
  final public boolean actual;

  public Case(String name, boolean expected, boolean actual) {
    this.name = name;
    this.expected = expected;
    this.actual = actual;
  }

  public boolean passed() {
    return this.expected == this.actual;
  }

  public static Case of(String name, boolean expected,
      boolean actual) {
    return new Case(name, expected, actual);
  }
}
