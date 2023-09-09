package src.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import src.core.IUnionFind;
import src.core.QUnionFind;
import src.core.Ts;
import src.core.WQUnionFind;
import src.logging.LogResult;
import src.logging.Logger;
import static src.benchmark.Range.range;

public class Bench {
  public static void main(String[] args) {
    final Benchmark bm = new Benchmark();
    final Logger log = new Logger("BENCHMARK");
    final Random rand = new Random();
    // runUF(bm, log, rand);
    runTs(bm, log, rand);
  }

  private static void runTs(Benchmark bm, Logger log, Random rand) {
    final int upper = 5_000;
    final int step = 100;

    final List<Integer> sizes = range(step, upper, step).toList();
    final int reps = 20;
    final BiConsumer<List<Integer>, Integer> setup = (l, sz) -> {
      for (int i = 0; i < sz; i++) {
        l.add(rand.nextInt(-10, 10));
      }
    };

    // final Map<Integer, Snapshot> bruteRes = bm.run("Brute", new ArrayList<>(),
    // setup, Ts::brute, sizes, reps);
    // final Map<Integer, Snapshot> bruteIRes = bm.run("BruteI", new ArrayList<>(),
    // setup, Ts::bruteI, sizes, reps);
    final Map<Integer, Snapshot> cacheRes = bm.run("Cache", new ArrayList<>(), setup, Ts::cache, sizes, reps);
    final Map<Integer, Snapshot> cacheIRes = bm.run("Cache", new ArrayList<>(), setup, Ts::cacheI, sizes, reps);
    final Map<Integer, Snapshot> twoPRes = bm.run("Sort", new ArrayList<>(), setup, Ts::twoP, sizes, reps);

    // final LogResult bruteResLog = log.fprintln("bruteRes.csv", "size; duration;
    // mean");
    // final LogResult bruteIResLog = log.fprintln("bruteIRes.csv", "size; duration;
    // mean");
    final LogResult cacheResLog = log.fprintln("cacheRes.csv", "size; duration; mean; repeats");
    final LogResult cacheIResLog = log.fprintln("cacheIRes.csv", "size; duration; mean; repeats");
    final LogResult twoPResLog = log.fprintln("twoPRes.csv", "size; duration; mean; repeats");

    if (cacheResLog != LogResult.Ok
        // || bruteResLog != LogResult.Ok
        // || bruteIResLog != LogResult.Ok
        || cacheIResLog != LogResult.Ok
        || twoPResLog != LogResult.Ok) {
      System.out.println("Error writing to file");
    }

    for (int sz : sizes) {
      // log.aprintln("bruteRes.csv", sz + "; " + bruteRes.get(sz).toString());
      // log.aprintln("bruteIRes.csv", sz + "; " + bruteIRes.get(sz).toString());
      log.aprintln("cacheRes.csv", sz + "; " + cacheRes.get(sz).toString());
      log.aprintln("cacheIRes.csv", sz + "; " + cacheIRes.get(sz).toString());
      log.aprintln("twoPRes.csv", sz + "; " + twoPRes.get(sz).toString());
    }
  }

  private static void runUF(Benchmark bm, Logger log, Random rand) {
    final int elems = 1_000_000;
    final int step = 2_000;
    final int upper = 100_000;
    final QUnionFind qf = new QUnionFind(elems);
    final WQUnionFind wqf = new WQUnionFind(elems);

    final List<Integer> sizes = range(step, upper, step).toList();
    final int reps = 10;
    final BiConsumer<IUnionFind, Integer> setup = (uf, sz) -> {
      for (int i = 0; i < sz; i++) {
        uf.union(rand.nextInt(sz), rand.nextInt(sz));
      }
    };
    final BiConsumer<IUnionFind, Integer> fn = (uf, sz) -> {
      uf.connected(rand.nextInt(sz), rand.nextInt(sz));
    };

    final Map<Integer, Snapshot> qfres = bm.run("QUnionFind", qf, setup, fn, sizes, reps);
    final Map<Integer, Snapshot> wqfres = bm.run("WQUnionFind", wqf, setup, fn, sizes, reps);

    final LogResult qfresLog = log.fprintln("qfres.csv", "size; duration; mean");
    final LogResult wqfresLog = log.fprintln("wqfres.csv", "size; duration; mean");
    if (qfresLog != LogResult.Ok || wqfresLog != LogResult.Ok) {
      System.out.println("Error writing to file");
    }

    for (int sz : sizes) {
      log.aprintln("qfres.csv", sz + "; " + qfres.get(sz).toString());
      log.aprintln("wqfres.csv", sz + "; " + wqfres.get(sz).toString());
    }
  }
}
