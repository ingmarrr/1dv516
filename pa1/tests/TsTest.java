package tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import src.core.ThreeSum;
import src.logging.Logger;
import src.testing.Test;
import src.testing.True;
import src.utils.Triple;
import src.utils.ThreeSumResult;

public class TsTest {
  private static final Logger log = new Logger("ThreeSumTest");

  public static void main(String[] args) {
    final boolean[] results = {
        // tsBrute(),
        // tsBrute2(),
        tsBruteMultiple(),
        // tsBruteFalse(),
        // tsBruteI(),
        // tsBruteIFalse(),
        // tsCache(),
        // tsCache2(),
        // tsCacheFalse(),
        // tsCacheI(),
        // tsCacheI2(),
        // tsCINegNum(),
        // tsCINoSolution(),
        tsCIMultiple(),
        tsTwoP(),
        tsTwoPNoSolution(),
        tsTwoPMultiple()
    };

    final boolean allTestsPassed = Test.allTrue(results);

    if (allTestsPassed) {
      System.out.println("All tests passed!");
    } else {
      final int failedTests = IntStream.range(0, results.length)
          .filter(i -> !results[i])
          .reduce(0, (acc, i) -> acc + 1);
      System.out.println("Failed tests: " + failedTests);
    }

    assert allTestsPassed;
  }

  private static boolean tsBrute() {
    final List<Integer> input = Arrays.asList(-1, 0, 1, -1, -4);
    final ThreeSumResult result = ThreeSum.brute(input);
    log.test("input: " + input);
    log.test("result: " + result);
    return Test.assertTrue(
        "Brute 3Sum",
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(-1, 1, 0)", result.has(-1, 1, 0)),
        True.of("(0, -1, 1)", result.has(0, -1, 1)),
        True.of("(0, 1, -1)", result.has(0, 1, -1)),
        True.of("(1, -1, 0)", result.has(1, -1, 0)),
        True.of("(1, 0, -1)", result.has(1, 0, -1)),
        True.of("Length 12", result.size() == 12));
  }

  private static boolean tsBrute2() {
    final List<Integer> input = Arrays.asList(1, -2, -6, 1);
    final ThreeSumResult result = ThreeSum.brute(input);
    log.test("input: " + input);
    // Expected:
    // {(1, -2, 1)},
    // {(1, 1, -2)},
    // {(-2, 1, 1)},
    // {(-2, 1, 1)},
    // {(1, -2, 1)},
    // {(1, 1, -2)}
    return Test.assertTrue(
        "Brute 3Sum",
        True.of("(1, -2, 1)", result.has(1, -2, 1)),
        True.of("(1, 1, -2)", result.has(1, 1, -2)),
        True.of("(-2, 1, 1)", result.has(-2, 1, 1)),
        True.of("Length 6", result.size() == 6));
  }

  private static boolean tsBruteFalse() {
    final List<Integer> input = Arrays.asList(1, 2, 4, 10, -7);
    final ThreeSumResult result = ThreeSum.brute(input);
    log.test("input: " + input);
    return Test.assertTrue(
        "Brute 3Sum Assert Empty",
        True.of("NoSolutionTest", result.isEmpty()));
  }

  private static boolean tsBruteI() {
    final List<Integer> input = Arrays.asList(-1, 0, 1, -1, -4);
    final ThreeSumResult result = ThreeSum.bruteI(input);
    log.test("input: " + input);
    return Test.assertTrue(
        "Brute Improved 3sum",
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(0, 1, -1)", result.has(0, 1, -1)),
        True.of("Length 2", result.size() == 2));
  }

  private static boolean tsBruteMultiple() {
    List<Integer> input = Arrays.asList(-2, -2, -1, -3, 4, 5);
    ThreeSumResult result = ThreeSum.twoP(input);
    log.test("input: " + input);
    log.test("result: " + result);
    log.test("len: " + result.size());
    return Test.assertTrue(
        "testTwoPMultiple",
        True.of("(-2, -3, 5)", result.has(-2, -3, 5)),
        True.of("(-2, -2, 4)", result.has(-2, -2, 4)),
        True.of("(-2, -3, 5)", result.has(-2, -3, 5)),
        True.of("(-2, -2, 4)", result.has(-2, -2, 4)),
        True.of("(-1, -3, 4)", result.has(-1, -3, 4)),
        True.of("(-3, -2, 5)", result.has(-3, -2, 5)),
        True.of("(-3, -1, 4)", result.has(-3, -1, 4)),
        True.of("(4, -3, -1)", result.has(4, -3, -1)),
        True.of("(4, -2, -2)", result.has(4, -2, -2)),
        True.of("(5, -3, -2)", result.has(5, -3, -2)));
  }

  private static boolean tsBruteIFalse() {
    final List<Integer> input = Arrays.asList(1, 2, 4, 10, -7);
    final ThreeSumResult result = ThreeSum.bruteI(input);
    log.test("input: " + input);
    return Test.assertTrue(
        "Brute Improved 3Sum Assert Empty",
        True.of("NoSolutionTest", result.isEmpty()));
  }

  private static boolean tsCache() {
    final List<Integer> input = Arrays.asList(-1, 0, 1, -1, -4);
    final ThreeSumResult result = ThreeSum.cache(input);
    log.test("input: " + input);
    log.test("result: " + result);
    return Test.assertTrue(
        "Cache 3Sum",
        True.of("(0, 1, -1)", result.has(0, 1, -1)),
        True.of("(1, -1, 0)", result.has(1, -1, 0)),
        True.of("(1, 0, -1)", result.has(1, 0, -1)),
        True.of("(1, -1, 0)", result.has(1, -1, 0)),
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(-1, 1, 0)", result.has(-1, 1, 0)),
        True.of("Length 6", result.size() == 6));
  }

  private static boolean tsCache2() {
    final List<Integer> input = Arrays.asList(1, -2, -6, 1);
    final ThreeSumResult result = ThreeSum.cache(input);
    log.test("input: " + input);
    log.test("result: " + result);
    return Test.assertTrue(
        "Cache 3Sum",
        True.of("(-2, 1, 1)", result.has(-2, 1, 1)),
        True.of("(1, 1, -2)", result.has(1, 1, -2)),
        True.of("(1, -2, 1)", result.has(1, -2, 1)),
        True.of("Length 4", result.size() == 4));
  }

  private static boolean tsCacheFalse() {
    final List<Integer> input = Arrays.asList(1, 2, 4, 10, -7);
    final ThreeSumResult result = ThreeSum.cache(input);
    log.test("input: " + input);
    return Test.assertTrue(
        "Cache 3Sum Assert Empty",
        True.of("NoSolutionTest", result.isEmpty()));
  }

  private static boolean tsCacheI() {
    final List<Integer> input = Arrays.asList(-1, 0, 1, -1, -4);
    final ThreeSumResult result = ThreeSum.cacheI(input);
    log.test("input: " + input);
    return Test.assertTrue(
        "Cache Improved 3sum",
        // Expected: [(-1, 0, 1), (0, 1, -1)]
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(0, 1, -1)", result.has(0, 1, -1)));
  }

  private static boolean tsCacheI2() {
    final List<Integer> input = Arrays.asList(1, -2, -6, 1);
    final ThreeSumResult result = ThreeSum.cacheI(input);
    log.test("input: " + input);
    log.test("result: " + result);
    return Test.assertTrue(
        "Cache Improved 3Sum",
        True.of("(-2, 1, 1)", result.has(-2, 1, 1)),
        True.of("(1, -2, 1)", result.has(1, -2, 1)),
        True.of("(1, 1, -2)", result.has(1, 1, -2)),
        True.of("Length = 3", result.size() == 3));
  }

  private static boolean tsCINoSolution() {
    List<Integer> input = Arrays.asList(1, 2, 3, 4);
    ThreeSumResult result = ThreeSum.cacheI(input);
    log.test("input: " + input);
    // Expected: []
    return Test.assertTrue(
        "Cache Improved testNoSolution",
        True.of("NoSolutionTest", result.isEmpty()));
  }

  private static boolean tsCINegNum() {
    List<Integer> input = Arrays.asList(-1, -2, 3, 4);
    ThreeSumResult result = ThreeSum.cacheI(input);
    log.test("input: " + input);
    // Expected: [-1, -2, 3]
    return Test.assertTrue(
        "Cache Improved testWithNegativeNumbers",
        True.of("NegativeTest", result.has(new Triple<>(-1, -2, 3))));
  }

  private static boolean tsCIMultiple() {
    List<Integer> input = Arrays.asList(-2, -2, -1, -3, 4, 5);
    ThreeSumResult result = ThreeSum.cacheI(input);
    log.test("input: " + input);
    // Expected: [(-2, -2, 4), (-2, -1, 3)]
    log.test("result: " + result);
    log.test("len: " + result.size());
    return Test.assertTrue(
        "Cache Improved testMultiple",
        True.of("(-2, -2, 4)", result.has(new Triple<>(-2, -2, 4))),
        True.of("(-1, -3, 4)", result.has(new Triple<>(-1, -3, 4))),
        True.of("(-2, -3, 5)", result.has(new Triple<>(-2, -3, 5))));
  }

  private static boolean tsTwoP() {
    List<Integer> input = Arrays.asList(-1, 0, 1, -1, -4, 2);
    ThreeSumResult result = ThreeSum.twoP(input);
    log.test("input: " + input);
    // Expected: [(-1, 0, 1), (-1, -1, 2)]
    return Test.assertTrue(
        "testTwoP",
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(-1, -1, 2)", result.has(-1, -1, 2)));
  }

  private static boolean tsTwoPNoSolution() {
    List<Integer> input = Arrays.asList(1, 2, 3, 4);
    ThreeSumResult result = ThreeSum.twoP(input);
    log.test("input: " + input);
    // Expected: []
    return Test.assertTrue(
        "testTwoPNoSolution",
        True.of("NoSolutionTest", result.isEmpty()));
  }

  private static boolean tsTwoPMultiple() {
    List<Integer> input = Arrays.asList(-2, -2, -1, -3, 4, 5);
    ThreeSumResult result = ThreeSum.twoP(input);
    log.test("input: " + input);
    /*
     * Expected
     * [(-2, -3, 5),
     * (-2, -2, 4),
     * (-2, -3, 5),
     * (-2, -2, 4),
     * (-1, -3, 4),
     * (-3, -2, 5),
     * (-3, -1, 4),
     * (4, -3, -1),
     * (4, -2, -2),
     * (5, -3, -2)]
     */
    log.test("result: " + result);
    log.test("len: " + result.size());
    return Test.assertTrue(
        "testTwoPMultiple",
        True.of("(-2, -3, 5)", result.has(-2, -3, 5)),
        True.of("(-2, -2, 4)", result.has(-2, -2, 4)),
        True.of("(-2, -3, 5)", result.has(-2, -3, 5)),
        True.of("(-2, -2, 4)", result.has(-2, -2, 4)),
        True.of("(-1, -3, 4)", result.has(-1, -3, 4)),
        True.of("(-3, -2, 5)", result.has(-3, -2, 5)),
        True.of("(-3, -1, 4)", result.has(-3, -1, 4)),
        True.of("(4, -3, -1)", result.has(4, -3, -1)),
        True.of("(4, -2, -2)", result.has(4, -2, -2)),
        True.of("(5, -3, -2)", result.has(5, -3, -2)));
  }
}
