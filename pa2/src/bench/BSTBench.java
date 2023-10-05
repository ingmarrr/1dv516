package bench;

import benching.Benchmark;
import core.BSTree;
import logging.Logger;
import logging.Mode;

import java.util.ArrayList;
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

  public static void main(String[] args) {
    var bm = new Benchmark();
    var rd = new Random();

    final int upper = 100;
    final int reps = 1;
    final int step = 10;
    final List<Integer> randNums = range(upper).toList().stream().map(i -> rd.nextInt(upper * 2)).collect(Collectors.toList());
    final List<Integer> choices = new ArrayList<>();
    final List<Integer> deleted = new ArrayList<>();
    final List<Integer> sizes = range(step, upper, step).toList();
    BSTree<Integer> bst = new BSTree<>();
    for (int ix : range(upper)) {
      final int choice = randNums.get(rd.nextInt(upper));
      choices.add(choice);
      bst.add(choice);
    }
    for (int jx :range(choices.size() / 2)) {
      var del = choices.get(rd.nextInt(choices.size()));
      deleted.add(del);
      bst.remove(del);
    }
    for (Integer d : deleted) {
      var removed = choices.remove(d);
    }
    for (int i: range(10)) {
      var choice = choices.get(rd.nextInt(choices.size()));
      var res = bst.contains(choice);
      log.info(choice, " \t", res);
    }
    log.info("Height\t", bst.height());
//    bm.bench("BST Bench", "bst", setup, fn, sizes, reps);
  }
}
