package benchmarks;

import core.QuadHashMap;
import core.Vehicle;
import logging.Logger;
import logging.Mode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static range.Range.range;

public class VehicleQuadHashMapBench {

  private static Logger log = Logger.builder()
      .mode(Mode.Bench)
      .emoji(true)
      .build();

  public static void main(String[] args) {
    var rand = new Random();
    var upper = 10_000;
    var qhm = new ArrayList<ArrayList<Vehicle>>(upper);
    for (int ix : range(upper)) qhm.add(null);

    log.info(qhm.size());

    for (int ignored : range(upper)) {
      var car = Vehicle.random(rand);
      var hash = car.hashCode() % upper;
      var bucket = qhm.get(hash);
      if (bucket == null) {
        var l = new ArrayList<Vehicle>();
        l.add(car);
        qhm.set(hash, l);
      } else {
        bucket.add(car);
      }
    }

    for (int ix : range(qhm.size())) {
      var bucket = qhm.get(ix);
      if (bucket == null) continue;
      for (var car : bucket) {
        log.print(car.toString());
        log.println("\t" + bucket.size() + "\t" + ix);
      }
    }

  }

}
