package src.benchmark;

import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

import src.logging.LogResult;
import src.logging.Logger;
import src.p1.QUnionFind;
import src.p1.UnionFind;
import src.p1.WQUnionFind;

public class Bench {
  public static void main(String[] args) {
    final int elems = 1_000_000;
    final int nrSnaps = elems / 5;
    final Benchmark bm = new Benchmark();
    final QUnionFind qf = new QUnionFind(elems);
    final WQUnionFind wqf = new WQUnionFind(elems);
    final Logger log = new Logger("BENCHMARK");
    final Random rand = new Random();

    final int[] sizes = new int[] {
        10_000, 20_000, 30_000, 40_000, 50_000, 60_000, 70_000, 80_000, 90_000,
        100_000, 110_000, 120_000, 130_000, 140_000, 150_000, 160_000, 170_000, 180_000, 190_000, 200_000,
    };
    final int reps = 10;

    final BiConsumer<UnionFind, Integer> setup = (uf, sz) -> {
      for (int i = 0; i < sz; i++) {
        uf.union(rand.nextInt(sz), rand.nextInt(sz));
      }
    };

    final BiConsumer<UnionFind, Integer> fn = (uf, sz) -> {
      uf.connected(rand.nextInt(sz), rand.nextInt(sz));
    };

    final Map<Integer, Float> qfres = bm.run("QUnionFind", qf, setup, fn, sizes, reps);
    final Map<Integer, Float> qwfres = bm.run("WQUnionFind", wqf, setup, fn, sizes, reps);

    final LogResult qfresLog = log.fprintln("qfres.csv", "size, duration");
    final LogResult qwfresLog = log.fprintln("qwfres.csv", "size, duration");
    if (qfresLog != LogResult.Ok || qwfresLog != LogResult.Ok) {
      System.out.println("Error writing to file");
    }

    for (int sz : sizes) {
      log.aprintln("qfres.csv", sz + "; " + qfres.get(sz));
      log.aprintln("qwfres.csv", sz + "; " + qwfres.get(sz));
    }
  }
}
