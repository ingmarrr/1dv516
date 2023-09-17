package benchmarks;

import src.core.Percolation;
import src.logging.Logger;

import static src.utils.Range.range;

import java.util.List;
import java.util.Random;

public class PercolationBench {

  public static void main(String[] args) {

    final Logger log = new Logger("PercolationBench");
    final Random rand = new Random();

    final int reps = 100;
    final int sizes = 500;

    log.fprintln("percolation.csv", "size;pStar;stdDev");

    for (int n : range(2, sizes, 2)) {
      final int trials = n * n;
      final List<Double> estimations = range(reps).toList().stream().map(i -> {
        final Percolation p = new Percolation(n);
        for (int t : range(trials)) {
          p.open(rand.nextInt(n), rand.nextInt(n));
          if (p.percolates()) {
            break;
          }
        }
        return (double) p.getOpenSites() / trials;
      }).toList();
      final double pStar = estimations.stream().reduce(0d, (acc, f) -> acc + f) / reps;

      final double stdDev = Math.sqrt(estimations.stream().map(f -> (f - pStar) * (f - pStar))
          .reduce(0d, (acc, f) -> acc + f)
          .floatValue() / reps);
      log.bench("pStar ::", pStar);
      log.bench("stdDev ::", stdDev);
      log.aprintln("percolation.csv", String.format("%d;%f;%f", n, pStar, stdDev));
    }
  }
}
