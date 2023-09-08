package src.testing;

import java.util.Arrays;

import src.logging.Logger;

public class Test {

  @SafeVarargs
  public static boolean assertTrue(
      String name,
      True... cases) {
    final Case[] converted = Arrays.stream(cases)
        .map(c -> Case.of(c.name, true, c.expected))
        .toArray(Case[]::new);
    final TestResult result = new TestResult(converted);
    final Logger log = new Logger(name);
    log.test("Running tests for " + name + "...");
    log.test(result.toString());
    return result.passed();
  }

  @SafeVarargs
  public static boolean assertFalse(
      String name,
      False... cases) {
    final Case[] converted = Arrays.stream(cases)
        .map(c -> Case.of(c.name, false, c.expected))
        .toArray(Case[]::new);
    final TestResult result = new TestResult(converted);
    final Logger log = new Logger(name);
    log.test("Running tests for " + name + "...");
    log.test(result.toString());
    return result.passed();
  }

  @SafeVarargs
  public static boolean assertEq(
      String name,
      Case... cases) {
    final TestResult result = new TestResult(cases);
    final Logger log = new Logger(name);
    log.test("Running tests for " + name + "...");
    log.test(result.toString());
    return result.passed();
  }
}
