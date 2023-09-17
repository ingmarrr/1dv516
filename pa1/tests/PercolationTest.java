package tests;

import java.util.stream.IntStream;

import src.core.Percolation;
import src.testing.False;
import src.testing.Test;
import src.testing.True;

public class PercolationTest {
  public static void main(String[] args) {
    System.out.println("PercolationTest");

    final boolean[] results = {
        firstColOpen(),
        diagonalOpen(),
        allClosed(),
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

  private static boolean firstColOpen() {
    final Percolation p = new Percolation(4);

    p.open(0, 0);
    p.open(1, 0);
    p.open(2, 0);
    p.open(3, 0);

    return Test.assertTrue(
        "Should percolate",
        True.of("[[1, 0, 0, 0],\n   [1, 0, 0, 0],\n   [1, 0, 0, 0],\n   [1, 0, 0, 0]]", p.percolates()));
  }

  private static boolean diagonalOpen() {
    final Percolation p = new Percolation(4);

    p.open(0, 0);
    p.open(1, 0);
    p.open(1, 1);
    p.open(2, 1);
    p.open(2, 2);
    p.open(3, 2);

    return Test.assertTrue(
        "Should percolate",
        True.of("[[1, 0, 0, 0],\n   [1, 1, 0, 0],\n   [0, 1, 1, 0],\n   [0, 0, 1, 0]]", p.percolates()));
  }

  private static boolean allClosed() {
    final Percolation p = new Percolation(4);

    return Test.assertFalse(
        "Should not percolate",
        False.of("[[0, 0, 0, 0],\n   [0, 0, 0, 0],\n   [0, 0, 0, 0],\n   [0, 0, 0, 0]]", p.percolates()));
  }
}
