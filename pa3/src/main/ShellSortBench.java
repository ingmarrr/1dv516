package main;

import benching.Benchmark;
import logging.Logger;
import logging.Mode;
import sorting.Shell;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

import static range.Range.range;

public class ShellSortBench {

  public static final Logger log = Logger.builder()
      .mode(Mode.Bench)
      .emoji(true)
      .build();

  public static void main(String[] args) {

    var bm = new Benchmark("shell_bench");
    var rand = new Random();
    var upper = 300_000;
    var reps = 10;
    var step = 1_000;
    var sizes = range(step, upper, step).toList();

    final Function<Integer, Integer[]> setup = size -> {
      var arr = new Integer[size];
      for (int ix : range(size)) {
        arr[ix] = rand.nextInt(size);
      }
      return arr;
    };

    final Consumer<Integer[]> funcStd = arr -> {
      Shell.sortStd(arr);
    };
    final Consumer<Integer[]> funcHibbard = arr -> {
      Shell.sortHibbard(arr);
    };
    final Consumer<Integer[]> funcKnuth = arr -> {
      Shell.sortKnuth(arr);
    };

    bm.bench("Shell Sort - Std", "ss_std_" + upper, setup, funcStd, sizes, reps);
    bm.bench("Shell Sort - Hibbard", "ss_hibbard_" + upper, setup, funcHibbard, sizes, reps);
    bm.bench("Shell Sort - Knut", "ss_knuth_" + upper, setup, funcKnuth, sizes, reps);
  }

}
