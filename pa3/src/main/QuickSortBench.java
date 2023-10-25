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

  public static void main(String[] args) {
    var rand = new Random();
    var bm = new Benchmark("qs_bench_results");

    for (var upper : range(10_000, 210_000, 10_000)) {
      var arr = new int[upper];
      for (int ix : range(upper)) {
        arr[ix] = rand.nextInt(upper);
      }
      var depths = range(40).toList();
      var reps = 1;

      final Function<Integer, Integer> setup = size -> {
        for (int ix : range(upper)) {
          arr[ix] = rand.nextInt(upper);
        }
        return size;
      };

      final Consumer<Integer> funcInsert = depth -> {
        Quick.sort(arr, depth, Quick.Fallback.Insert);
      };
      final Consumer<Integer> funcHeap = depth -> {
        Quick.sort(arr, depth, Quick.Fallback.Heap);
      };

      bm.bench("Quick Sort - Fallback Insert", "qs_insert_" + upper, setup, funcInsert, depths, reps);
      bm.bench("Quick Sort - Fallback Heap", "qs_heap_" + upper, setup, funcHeap, depths, reps);
    }

  }
}
