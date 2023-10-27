package main;

import benching.Benchmark;
import logging.Logger;
import logging.Mode;
import sorting.Merge;
import sorting.Quick;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

import static range.Range.range;

public class MergeSortBench {

  private static final Logger log = Logger.builder()
      .mode(Mode.Bench)
      .emoji(true)
      .build();

  public static void main(String[] args) {

    var rand = new Random();
    var bm = new Benchmark("merge_bench");
    var upper = 400_000;
    var reps = 10;
    var step = 1_000;
    var sizes = range(step, upper, step).toList();

    final Function<Integer, int[]> setup = size -> {
      var arr = new int[size];
      for (int ix : range(size)) {
        arr[ix] = rand.nextInt(size);
      }
      return arr;
    };

    final Consumer<int[]> funcIterative = arr -> {
      Merge.sortIterative(arr);
    };
    final Consumer<int[]> funcRecursive = arr -> {
      Merge.sortRecursive(arr);
    };

    bm.bench("Merge Sort - Iterative", "ms_itr_" + upper, setup, funcIterative, sizes, reps);
    bm.bench("Merge Sort - Recursive", "ms_rec_" + upper, setup, funcRecursive, sizes, reps);
  }
}
