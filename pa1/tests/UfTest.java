package tests;

import java.util.stream.IntStream;

import src.core.QUnionFind;
import src.core.WPCQUnionFind;
import src.testing.False;
import src.testing.Test;
import src.testing.True;

public class UfTest {

  public static void main(String[] args) {
    final QUnionFind qf = new QUnionFind(20);
    qf.union(4, 3);
    qf.union(3, 8);
    qf.union(8, 17);
    qf.union(10, 15);

    final WPCQUnionFind wqf = new WPCQUnionFind(20);
    wqf.union(4, 3);
    wqf.union(3, 8);
    wqf.union(8, 17);
    wqf.union(10, 15);

    final boolean[] results = {
        qfTestConnected(qf),
        qfTestNotConnected(qf),
        wqfTestConnected(wqf),
        wqfTestNotConnected(wqf)
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

  private static boolean qfTestConnected(QUnionFind qf) {
    return Test.assertTrue(
        "QF connected",
        True.of("(4, 3)", qf.connected(4, 3)),
        True.of("(3, 8)", qf.connected(3, 8)),
        True.of("(4, 8)", qf.connected(4, 8)),
        True.of("(8, 17)", qf.connected(8, 17)),
        True.of("(3, 17)", qf.connected(3, 17)),
        True.of("(4, 17)", qf.connected(4, 17)));
  }

  private static boolean qfTestNotConnected(QUnionFind qf) {
    return Test.assertFalse(
        "QF not connected",
        False.of("(0, 7)", qf.connected(0, 7)),
        False.of("(1, 2)", qf.connected(1, 2)),
        False.of("(1, 9)", qf.connected(1, 9)));
  }

  private static boolean wqfTestConnected(WPCQUnionFind wqf) {
    return Test.assertTrue(
        "WQF connected",
        True.of("(4, 3)", wqf.connected(4, 3)),
        True.of("(3, 8)", wqf.connected(3, 8)),
        True.of("(4, 8)", wqf.connected(4, 8)),
        True.of("(8, 17)", wqf.connected(8, 17)),
        True.of("(3, 17)", wqf.connected(3, 17)),
        True.of("(4, 17)", wqf.connected(4, 17)));
  }

  private static boolean wqfTestNotConnected(WPCQUnionFind wqf) {
    return Test.assertFalse(
        "WQF not connected",
        False.of("(0, 7)", wqf.connected(0, 7)),
        False.of("(1, 2)", wqf.connected(1, 2)),
        False.of("(1, 9)", wqf.connected(1, 9)));
  }
}
