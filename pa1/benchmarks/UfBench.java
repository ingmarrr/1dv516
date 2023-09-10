package benchmarks;

import static src.utils.Range.range;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

import src.benchmark.Benchmark;
import src.core.IUnionFind;
import src.core.QUnionFind;
import src.core.WQUnionFind;

public class UfBench {
    private final static Random rand = new Random();

    record State(IUnionFind uf, int size) {}
    public static void main(String[] args) {
        final int step = 500;
        final int upper = 50_000;
        final int reps = 10;
        final QUnionFind qf = new QUnionFind(upper);
        final WQUnionFind wqf = new WQUnionFind(upper);
        final Benchmark bm = new Benchmark();
        final Function<Integer, State> qfSetup = setup(qf);
        final Function<Integer, State> wqfSetup = setup(wqf);
        final Consumer<State> fn = (state) -> state.uf.connected(rand.nextInt(state.size), rand.nextInt(state.size));
        final List<Integer> sizes = range(step, upper, step).toList();

        bm.bench(
                "QUnionFind", "qfbench",
                qfSetup, fn, sizes, reps
        );
        bm.bench(
                "WQUnionFind", "wqfbench",
                wqfSetup, fn, sizes, reps
        );
    }

    private static Function<Integer, State> setup(IUnionFind uf) {
        return (sz) -> {
            for (int ignored : range(sz)) {
                uf.union(rand.nextInt(sz), rand.nextInt(sz));
            }
            return new State(uf, sz);
        };
    }
}
