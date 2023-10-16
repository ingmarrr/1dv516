package bench;

import benching.Benchmark;
import core.AVLTree;
import core.BSTree;
import logging.Logger;
import logging.Mode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static range.Range.range;

public class BSTBench {

  private static Logger log = Logger.builder()
      .mode(Mode.Bench)
      .emoji(true)
      .build();

  private record State<E extends Comparable<E>>(BSTree<E> bst, int size) {

  }

  public static void main(String[] args) {
    var bm = new Benchmark("benchmarks");
    var rd = new Random();

    final int upper = 100_000;
    final int reps = 10_000;
    final int step = 1000;
    final Function<Integer, BSTBench.State<Integer>> setup = sz -> {
      BSTree<Integer> bst = new BSTree<>();
      for (int ix : range(sz * 2)) {
        bst.add(rd.nextInt(50000));
      }
      log.print("Before: " + bst.size());
      for (int jx : range(110000)) {
        bst.del(rd.nextInt(sz / 2));
      }
      log.print(" | After : "  + bst.size());
      return new BSTBench.State(bst, sz);
    };
    final Function<BSTBench.State<Integer>, Integer> func = state -> {
      state.bst.contains(rd.nextInt(state.size / 2));
      return state.bst.height();
    };

    bm.bench("BST Bench", "bst_100", setup, func, 10_000, 1000, 1000);
  }
}
