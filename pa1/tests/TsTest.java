package tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import src.core.Triple;
import src.core.Ts;
import src.core.TsResult;
import src.testing.Test;
import src.testing.True;

public class TsTest {
  public static void main(String[] args) {
    final int[] l = new int[] { -1, 0, 1, -1, -4 };
    List<Integer> list = Arrays.stream(l).boxed().collect(Collectors.toList());
    final TsResult resBrute = Ts.brute(list);
    final TsResult resBruteI = Ts.bruteI(list);
    final TsResult resCache = Ts.cache(list);
    final TsResult resCacheI = Ts.cacheI(list);

    final boolean[] results = {
        tsBrute(resBrute),
        tsBruteI(resBruteI),
        tsCache(resCache),
        tsCacheI(resCacheI),
        tsCINegNum(),
        tsCINoSolution(),
        tsMultiple(),
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

  private static boolean tsBrute(TsResult result) {
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

  private static boolean tsBruteI(TsResult result) {
    return Test.assertTrue(
        "Brute Improved 3sum",
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(0, 1, -1)", result.has(0, 1, -1)));
  }

  private static boolean tsCache(TsResult result) {
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

  private static boolean tsCacheI(TsResult result) {
    return Test.assertTrue(
        "Cache Improved 3sum",
        // Expected: [(-1, 0, 1), (0, 1, -1)]
        True.of("(-1, 0, 1)", result.has(-1, 0, 1)),
        True.of("(0, 1, -1)", result.has(0, 1, -1)));
  }

  private static boolean tsCINoSolution() {
    List<Integer> input = Arrays.asList(1, 2, 3, 4);
    TsResult result = Ts.cacheI(input);
    // Expected: []
    return Test.assertTrue(
        "testNoSolution",
        True.of("NoSolutionTest", result.isEmpty()));
  }

  private static boolean tsCINegNum() {
    List<Integer> input = Arrays.asList(-1, -2, 3, 4);
    TsResult result = Ts.cacheI(input);
    // Expected: [-1, -2, 3]
    return Test.assertTrue(
        "testWithNegativeNumbers",
        True.of("NegativeTest", result.has(new Triple<>(-1, -2, 3))));
  }

  private static boolean tsMultiple() {
    List<Integer> input = Arrays.asList(-2, -2, -1, -3, 4, 5);
    TsResult result = Ts.cacheI(input);
    System.out.println(result);
    // Expected: [(-2, -2, 4), (-2, -1, 3)]
    return Test.assertTrue(
        "testMultiple",
        True.of("(-2, -2, 4)", result.has(new Triple<>(-2, -2, 4))),
        True.of("(-1, -3, 4)", result.has(new Triple<>(-1, -3, 4))),
        True.of("(-2, -3, 5)", result.has(new Triple<>(-2, -3, 5))));
  }
}
