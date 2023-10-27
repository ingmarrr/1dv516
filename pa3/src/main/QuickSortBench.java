package main;

import benching.Benchmark;
import logging.Logger;
import logging.Mode;
import sorting.Quick;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

import static range.Range.range;

public class QuickSortBench {

  private static final Logger log = Logger.builder()
      .mode(Mode.Bench)
      .emoji(true)
      .build();

  record State(int[] arr, int depth) {}

  public static void main(String[] args) {
    var rand = new Random();
    var bm = new Benchmark("qs_bench");
    var upper = 400_000;
    var reps = 1;
    var depths = range(1, 40).toList();

    final Function<Integer, State> setup = size -> {
      int[] arr = new int[upper];
      for (int ix : range(upper)) {
        arr[ix] = rand.nextInt(size);
      }
      return new State(arr, size);
    };

    final Consumer<State> funcInsert = state -> {
      Quick.sort(state.arr, state.depth, Quick.Fallback.Insert);
    };
    final Consumer<State> funcHeap = state -> {
      Quick.sort(state.arr, state.depth, Quick.Fallback.Heap);
    };
    final Consumer<State> funcNone = state -> {
      Quick.sort(state.arr);
    };

    bm.bench("Quick Sort - Fallback Insert", "qs_insert_" + upper, setup, funcInsert, depths, reps);
    bm.bench("Quick Sort - Fallback Heap", "qs_heap_" + upper, setup, funcHeap, depths, reps);
    bm.bench("Quick Sort - Fallback None", "qs_none_" + upper, setup, funcNone, depths, reps);
  }

}
