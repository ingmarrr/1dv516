package bench;

import benching.Benchmark;
import core.AVLTree;
import logging.Logger;
import logging.Mode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static range.Range.range;

public class AVLBench {
  private static Logger log = Logger.builder()
      .mode(Mode.Bench)
      .emoji(true)
      .build();

  private record State<E extends Comparable<E>>(AVLTree<E> avl, int size) {

  }

  public static void main(String[] args) {
    var bm = new Benchmark("benchmarks");
    var rd = new Random();

    final int upper = 100_000;
    final int reps = 10_000;
    final int step = 100;
    final List<Integer> sizes = Collections.nCopies(100, 100_000);
    final Function<Integer, AVLBench.State<Integer>> setup = sz -> {
      AVLTree<Integer> avl = new AVLTree<>();
      for (int ix : range(sz * 2)) {
        avl.add(rd.nextInt(50000));
      }
      log.print("Before: " + avl.size());
      for (int jx : range(120000)) {
        avl.del(rd.nextInt(sz / 2));
      }
      log.print(" | After : "  + avl.size());
      return new AVLBench.State(avl, sz);
    };
    final Function<AVLBench.State<Integer>, Integer> func = state -> {
      state.avl.get(rd.nextInt(state.size / 2)).isPresent();
      return state.avl.height();
    };

    bm.bench("AVL Bench", "avl_100", setup, func, 10_000, 1000, 1000);
  }
}
