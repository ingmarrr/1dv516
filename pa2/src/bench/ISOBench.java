package bench;

import logging.Logger;
import logging.Mode;

public class ISOBench {

  private final static Logger log = Logger.builder()
      .mode(Mode.Bench)
      .emoji(true)
      .build();

  public static void main(String[] args) {
    log.info("Benching Isomorphic Thingies");

  }
}
