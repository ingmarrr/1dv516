package src.benchmark;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

import src.core.IUnionFind;
import src.core.QUnionFind;
import src.core.WQUnionFind;
import src.logging.LogResult;
import src.logging.Logger;
import static src.benchmark.Range.range;

public class Bench {
  public static void main(String[] args) {
    final int elems = 1_000_000;
    final Benchmark bm = new Benchmark();
    final QUnionFind qf = new QUnionFind(elems);
    final WQUnionFind wqf = new WQUnionFind(elems);
    final Logger log = new Logger("BENCHMARK");
    final Random rand = new Random();

    final List<Integer> sizes = range(2_000, 100_000, 2_000).toList();
    final int reps = 10;

    final BiConsumer<IUnionFind, Integer> setup = (uf, sz) -> {
      for (int i = 0; i < sz; i++) {
        uf.union(rand.nextInt(sz), rand.nextInt(sz));
      }
    };
    final BiConsumer<IUnionFind, Integer> fn = (uf, sz) -> {
      uf.connected(rand.nextInt(sz), rand.nextInt(sz));
    };

    final Map<Integer, Float> qfres = bm.run("QUnionFind", qf, setup, fn, sizes, reps);
    final Map<Integer, Float> wqfres = bm.run("WQUnionFind", wqf, setup, fn, sizes, reps);

    final LogResult qfresLog = log.fprintln("qfres.csv", "size, duration");
    final LogResult wqfresLog = log.fprintln("wqfres.csv", "size, duration");
    if (qfresLog != LogResult.Ok || wqfresLog != LogResult.Ok) {
      System.out.println("Error writing to file");
    }

    for (int sz : sizes) {
      log.aprintln("qfres.csv", sz + "; " + qfres.get(sz));
      log.aprintln("wqfres.csv", sz + "; " + wqfres.get(sz));
    }
  }
}
