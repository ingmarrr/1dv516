package tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import src.core.ThreeSum;
import src.testing.Test;
import src.testing.True;
import src.utils.Triple;
import src.utils.ThreeSumResult;

public class TsTest {
  public static void main(String[] args) {
    final int[] l = new int[] { -1, 0, 1, -1, -4 };
    List<Integer> list = Arrays.stream(l).boxed().collect(Collectors.toList());
    final ThreeSumResult resBrute = ThreeSum.brute(list);
    final ThreeSumResult resBruteI = ThreeSum.bruteI(list);
    final ThreeSumResult resCache = ThreeSum.cache(list);
    final ThreeSumResult resCacheI = ThreeSum.cacheI(list);

    final boolean[] results = {
        tsBrute(resBrute),
        tsBruteI(resBruteI),
        tsCache(resCache),
        tsCacheI(resCacheI),
        tsCINegNum(),
        tsCINoSolution(),
        tsMultiple(),
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

  private static boolean tsBrute(ThreeSumResult result) {
    return Test.assertTrue(
        "Brute 3Sum",
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(-1, 1, 0)", result.has(-1, 1, 0)),
        True.of("(0, -1, 1)", result.has(0, -1, 1)),
        True.of("(0, 1, -1)", result.has(0, 1, -1)),
        True.of("(0, 1, -1)", result.has(0, 1, -1)),
        True.of("(0, -1, 1)", result.has(0, -1, 1)),
        True.of("(1, -1, 0)", result.has(1, -1, 0)),
        True.of("(1, 0, -1)", result.has(1, 0, -1)),
        True.of("(1, 0, -1)", result.has(1, 0, -1)),
        True.of("(1, -1, 0)", result.has(1, -1, 0)),
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(-1, 1, 0)", result.has(-1, 1, 0)));
  }

  private static boolean tsBruteI(ThreeSumResult result) {
    return Test.assertTrue(
        "Brute Improved 3sum",
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(0, 1, -1)", result.has(0, 1, -1)));
  }

  private static boolean tsCache(ThreeSumResult result) {
    return Test.assertTrue(
        "Cache 3Sum",
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(-1, 1, 0)", result.has(-1, 1, 0)),
        True.of("(0, -1, 1)", result.has(0, -1, 1)),
        True.of("(0, 1, -1)", result.has(0, 1, -1)),
        True.of("(0, 1, -1)", result.has(0, 1, -1)),
        True.of("(0, -1, 1)", result.has(0, -1, 1)),
        True.of("(1, -1, 0)", result.has(1, -1, 0)),
        True.of("(1, 0, -1)", result.has(1, 0, -1)),
        True.of("(1, 0, -1)", result.has(1, 0, -1)),
        True.of("(1, -1, 0)", result.has(1, -1, 0)),
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(-1, 1, 0)", result.has(-1, 1, 0)));
  }

  private static boolean tsCacheI(ThreeSumResult result) {
    return Test.assertTrue(
        "Cache Improved 3sum",
        // Expected: [(-1, 0, 1), (0, 1, -1)]
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(0, 1, -1)", result.has(0, 1, -1)));
  }

  private static boolean tsCINoSolution() {
    List<Integer> input = Arrays.asList(1, 2, 3, 4);
    ThreeSumResult result = ThreeSum.cacheI(input);
    // Expected: []
    return Test.assertTrue(
        "testNoSolution",
        True.of("NoSolutionTest", result.isEmpty()));
  }

  private static boolean tsCINegNum() {
    List<Integer> input = Arrays.asList(-1, -2, 3, 4);
    ThreeSumResult result = ThreeSum.cacheI(input);
    // Expected: [-1, -2, 3]
    return Test.assertTrue(
        "testWithNegativeNumbers",
        True.of("NegativeTest", result.has(new Triple<>(-1, -2, 3))));
  }

  private static boolean tsMultiple() {
    List<Integer> input = Arrays.asList(-2, -2, -1, -3, 4, 5);
    ThreeSumResult result = ThreeSum.cacheI(input);
    // Expected: [(-2, -2, 4), (-2, -1, 3)]
    return Test.assertTrue(
        "testMultiple",
        True.of("(-2, -2, 4)", result.has(new Triple<>(-2, -2, 4))),
        True.of("(-1, -3, 4)", result.has(new Triple<>(-1, -3, 4))),
        True.of("(-2, -3, 5)", result.has(new Triple<>(-2, -3, 5))));
  }

  private static boolean tsTwoP() {
    List<Integer> input = Arrays.asList(-1, 0, 1, -1, -4, 2);
    ThreeSumResult result = ThreeSum.twoP(input);
    // Expected: [(-1, 0, 1), (-1, -1, 2)]
    return Test.assertTrue(
        "testTwoP",
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(-1, -1, 2)", result.has(-1, -1, 2)));
  }

  private static boolean tsTwoPNoSolution() {
    List<Integer> input = Arrays.asList(1, 2, 3, 4);
    ThreeSumResult result = ThreeSum.twoP(input);
    // Expected: []
    return Test.assertTrue(
        "testTwoPNoSolution",
        True.of("NoSolutionTest", result.isEmpty()));
  }

  private static boolean tsTwoPMultiple() {
    List<Integer> input = Arrays.asList(-2, -2, -1, -3, 4, 5);
    ThreeSumResult result = ThreeSum.twoP(input);
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
