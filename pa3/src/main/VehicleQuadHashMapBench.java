package main;

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
    try {
      run();
    } catch (Exception E) {
      E.printStackTrace();
    }
  }

  public static void run() {
    var rand = new Random();
    var upper = 50_000;
    var qhm = new QuadHashMap<Vehicle, Integer>();
    for (int ignored : range(upper)) {
      var car = Vehicle.random(rand);
      qhm.put(car, qhm.get(car).orElse(1));
    }

    var avg = 0;
    var cnt = 0;
    var buckets = qhm.getBuckets();
    for (int ix : range(buckets.length)) {
      var entry = buckets[ix];
      log.print("" + ix + " - ");
      if (entry == null) {
        log.println("None");
        continue;
      }
      log.print(entry.key.toString());
      log.print(" Destination: " + entry.destIx + " Actual: " + entry.actualIx);
      log.print(" Collisions: " + entry.collisions);
      log.println(" Offset: " + Math.abs(entry.destIx - entry.actualIx));
      avg += Math.abs(entry.destIx - entry.actualIx);
      cnt++;
    }
    avg = avg / cnt;

    int avgCollision = Arrays.stream(buckets)
        .map(e -> e == null ? 0 : e.collisions)
        .reduce(0, (a, b) -> a + b);

    log.info("Average Offset: ", avg);
    log.info("Average Collisions: ", (double) avgCollision / buckets.length);
    log.info("Nr Buckets: ", buckets.length);
    log.info("Load Factor: ", (double) qhm.getSize() / buckets.length);
  }

  public static void arr() {
    var rand = new Random();
    var upper = 100_000;
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
