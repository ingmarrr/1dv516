package src.benchmark;

import java.util.Random;

import src.logging.Logger;
import src.p1.QUnionFind;
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

    bm.run(qf, (state, ix) -> {
      state.union(rand.nextInt(elems), rand.nextInt(elems));
    }, elems - 1, nrSnaps);
    final float qfDurationSetup = bm.getDurationS();
    // final float qfMeanSetup = bm.getMean();
    log.info("QF SETUP :: duration ::" + qfDurationSetup);

    bm.run(qf, (state, ix) -> {
      state.root(ix);
    }, elems, nrSnaps);
    final float qfDuration = bm.getDurationS();
    // final float qfMean = bm.getMean();
    log.info("QF ROOT :: duration ::" + qfDuration);

    bm.reset();

    bm.run(wqf, (state, ix) -> {
      state.union(rand.nextInt(elems), rand.nextInt(elems));
    }, elems - 1, nrSnaps);
    final float wqfDurationSetup = bm.getDurationS();
    // final float wqfMeanSetup = bm.getMean();
    log.info("WQF SETUP :: duration ::" + wqfDurationSetup);

    bm.run(wqf, (state, ix) -> {
      state.root(ix);
    }, elems, nrSnaps);
    final float wqfDuration = bm.getDurationS();
    log.info("WQF ROOT :: duration ::" + wqfDuration);
    // log.info("WQF ROOT :: mean ::" + wqfMean);
  }
}
