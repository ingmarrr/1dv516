package main;

import core.Problem3.LCRSNode;
import logging.Logger;
import logging.Mode;

import java.io.File;
import java.util.*;

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
    if (!rootFi.exists()) {
      log.error("File does not exist.");
      return;
    }
    var tree = new LCRSNode<>(path.get());
    var level = 0;
    readDir(rootFi, tree, level);
    log.print("\033[H\033[2J");
    System.out.flush();
    tree.print();

    try {
      Scanner sc = new Scanner(System.in);
      log.println("Enter a file to find (enter 'q' to quit)");
      System.out.flush();
      String userInput = "";


      while (true) {
        log.print("> ");
        userInput = sc.nextLine();
        if (userInput == null) userInput = "";
        if (userInput.equalsIgnoreCase("q")) break;
        var res = tree.find(userInput);
        if (res.isPresent()) {
          log.success("Found:", res.get().getVal());
        } else {
          log.error("Did not find:", userInput);
        }
        System.out.flush();
      }

      System.out.println("You entered 'q'. Exiting...");
      sc.close();
    } catch (Exception e) {
      log.error("Error");
      e.printStackTrace();
    }

  }

  public static void readDir(File parentFi, LCRSNode<String> parentNode, int level) {
    for (File fi : Objects.requireNonNull(parentFi.listFiles())) {
      final String str = String.join("", Collections.nCopies(level, "| "));
      final LCRSNode<String> cur = parentNode.add(fi.getName(), str);
      if (fi.isDirectory()) {
        readDir(fi, cur, level+1);
      }
    }
  }

}
