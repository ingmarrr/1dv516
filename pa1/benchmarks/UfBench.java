package benchmarks;

import static src.utils.Range.range;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;

import src.benchmark.Benchmark;
import src.core.IUnionFind;
import src.core.QUnionFind;
import src.core.WQUnionFind;

public class UfBench {

    record State(IUnionFind uf, int size) {
    }

    public static void main(String[] args) {
        final int step = 500;
        final int upper = 30_000;
        final int reps = 20;
        final QUnionFind qf = new QUnionFind(upper);
        final WQUnionFind wqf = new WQUnionFind(upper);
        final Benchmark bm = new Benchmark();
        final Function<Integer, State> qfSetup = setup(qf);
        final Function<Integer, State> wqfSetup = setup(wqf);
        final Consumer<State> fn = (state) -> {
            final int first = ThreadLocalRandom.current().nextInt(state.size);
            final int second = ThreadLocalRandom.current().nextInt(state.size);
            state.uf.connected(first, second);
        };
        final List<Integer> sizes = range(step, upper, step).toList();

        bm.bench("QUnionFind", "qf", qfSetup, fn, sizes, reps);
        bm.bench("WQUnionFind", "wqf", wqfSetup, fn, sizes, reps);
    }

    private static Function<Integer, State> setup(IUnionFind uf) {
        return (sz) -> {
            for (int ignored : range(sz)) {
                final int first = ThreadLocalRandom.current().nextInt(sz);
                final int second = ThreadLocalRandom.current().nextInt(sz);
                uf.union(first, second);
            }
            return new State(uf.clone(), sz);
        };
    }
}
