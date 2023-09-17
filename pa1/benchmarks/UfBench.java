package benchmarks;

import static src.utils.Range.range;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;

import src.benchmark.Benchmark;
import src.core.IUnionFind;
import src.core.QFind;
import src.core.QUnionFind;
import src.core.WPCQUnionFind;

public class UfBench {

    record State(IUnionFind uf, int size) {
    }

    public static void main(String[] args) {
        final Benchmark bm = new Benchmark();

        final int qfUpper = 1_000_000;
        final int qfReps = 1000;
        final int qfStep = 10000;
        final List<Integer> qfSizes = range(qfStep, qfUpper, qfStep).toList();
        final Function<Integer, State> qfSetupUnion = sz -> new State(new QFind(sz),
                sz);
        final Consumer<State> qfFnUnion = state -> state.uf.union(state.size - 1,
                state.size - 2);

        bm.benchST("QFind", "qf_union", qfSetupUnion, qfFnUnion, qfSizes, qfReps);

        final int qufUpper = 1_000_000;
        final int qufReps = 100_000;
        final int qufStep = 10000;
        final List<Integer> qufSizes = range(qufStep, qufUpper, qufStep).toList();
        final Function<Integer, State> qufSetupUnion = sz -> new State(new QUnionFind(sz), sz);
        final Consumer<State> qufFnUnion = state -> state.uf.union(state.size - 1, state.size - 2);

        bm.benchST("QUnionFind", "quf_union", qufSetupUnion, qufFnUnion, qufSizes, qufReps);
    }

}
