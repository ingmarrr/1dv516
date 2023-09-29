package main;

import core.LCRSNode;
import logging.Logger;
import logging.Mode;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class FTree {
  private static final Logger log = Logger.builder()
      .mode(Mode.Main)
      .emoji(true)
      .modeEmoji(true)
      .build();

  public static void main(String[] args) {
    final Optional<String> path = switch (args.length) {
      case 1 -> of(args[0]);
      default -> empty();
    };

    if (path.isEmpty()) {
      log.error("Invalid arguments, requires: ./gradlew run --args=\"<path>\"");
      return;
    }

    var rootFi = new File(path.get());
    var tree = new LCRSNode<>(path.get());
    var level = 0;
    readDir(rootFi, tree, level);
    tree.print();
  }

  public static void readDir(File parentFi, LCRSNode<String> parentNode, int level) {
    for (File fi : Objects.requireNonNull(parentFi.listFiles())) {
      final String str = String.join("", Collections.nCopies(level, "| "));
      final LCRSNode<String> cur = parentNode.add(str + fi.getName());
      if (fi.isDirectory()) {
        readDir(fi, cur, level + 1);
      }
    }
  }

}
