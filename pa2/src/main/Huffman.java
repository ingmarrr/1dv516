package main;

import core.HuffmanNode;
import core.HuffmanTree;
import logging.Logger;
import logging.Mode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class Huffman {

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
      log.error("Invalid arguments, requires: gradle huffman --args=\"<path>\"");
      return;
    }

    var hm = new HashMap<Character, Integer>();

    try(BufferedReader rx = new BufferedReader(new FileReader(path.get()))) {
      int ch;
      while ((ch = rx.read()) != -1) {
        var cnt = hm.get((char) ch);
        if (cnt == null) {
          hm.put((char) ch, 1);
        } else {
          hm.put((char) ch, cnt + 1);
        }
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }

    var nodes = hm.entrySet().stream().map(e -> new HuffmanNode(e.getKey(), e.getValue())).collect(Collectors.toList());
    var ht = new HuffmanTree(nodes);
    ht.print();
  }
}
