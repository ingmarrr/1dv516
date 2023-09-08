package src.p1;

import src.testing.Case;
import src.testing.False;
import src.testing.Test;
import src.testing.True;

public class P1 {

  public static void main(String[] args) {
    final QUnionFind qf = new QUnionFind(10);
    final WQUnionFind wqf = new WQUnionFind(10);

    // ------------------
    // Test QUnionFind

    // Setup
    qf.union(4, 3);
    qf.union(3, 8);
    qf.union(6, 5);

    // Testing
    final boolean qfa = Test.assertEq("QF connected",
        Case.of("connected (4, 3)", qf.connected(4, 3), true),
        Case.of("connected (3, 8)", qf.connected(3, 8), true),
        Case.of("connected (4, 8)", qf.connected(4, 8), true),
        Case.of("connected (6, 5)", qf.connected(6, 5), true),
        Case.of("not connected (0, 7)", qf.connected(0, 7), false),
        Case.of("not connected (1, 2)", qf.connected(1, 2), false),
        Case.of("not connected (1, 9)", qf.connected(1, 9), false));

    assert qfa;
    // ------------------

    // ------------------
    // Test WQUnionFind

    // Setup
    wqf.union(4, 3);
    wqf.union(3, 8);
    wqf.union(6, 5);

    // Testing
    final boolean wqfa = Test.assertEq("QF connected",
        Case.of("connected (4, 3)", wqf.connected(4, 3), true),
        Case.of("connected (3, 8)", wqf.connected(3, 8), true),
        Case.of("connected (4, 8)", wqf.connected(4, 8), true),
        Case.of("connected (6, 5)", wqf.connected(6, 5), true),
        Case.of("not connected (0, 7)", wqf.connected(0, 7), false),
        Case.of("not connected (1, 2)", wqf.connected(1, 2), false),
        Case.of("not connected (1, 9)", wqf.connected(1, 9), false));

    assert wqfa;

    // ------------------
    System.out.println("All tests passed!");
  }
}