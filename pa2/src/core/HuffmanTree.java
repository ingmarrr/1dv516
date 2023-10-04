package core;

import logging.Logger;
import logging.Mode;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static range.Range.range;

public class HuffmanTree implements Iterable<HuffmanNode> {

  private static final Logger log = Logger.builder()
      .mode(Mode.Main)
      .emoji(true)
      .modeEmoji(true)
      .build();

  private List<HuffmanNode> forest;


  public Optional<HuffmanNode> root;

  public HuffmanTree(String inp) {
    var hm = new HashMap<Character, Integer>();
    inp.chars().forEach(
        c -> {
          var cnt = hm.get((char) c);
          if (cnt == null) hm.put((char) c, 1);
          else hm.put((char) c, cnt + 1);
        }
    );
    var nodes = hm.entrySet().stream().map(e -> new HuffmanNode(e.getKey(), e.getValue())).collect(Collectors.toList());
    this.forest = nodes;
    this.root = forest.get(0) == null ? empty() : of(forest.get(0));
    makeTree();
  }

  public HuffmanTree(List<HuffmanNode> forest) {
    this.forest = forest;
    this.root = empty();
    makeTree();
  }

  private int height(Optional<HuffmanNode> node) {
    if (node.isEmpty()) return 0;
    return 1 + Math.max(height(node.get().left), height(node.get().right));
  }

  public void print() {
    var height = height(root);
    log.info(height);
    StringBuilder sb = new StringBuilder();
    printLvl(sb, "", "", root, root.get().right.isPresent());
    log.info(sb.toString());
    log.info(root.get().right.get().toString());
  }

  public void printLvl(
      StringBuilder sb,
      String padding,
      String pointer,
      Optional<HuffmanNode> node,
      boolean hasRightSibling
  ) {
    if (node.isPresent()) {
      sb.append("\n");
      sb.append(padding);
      sb.append(pointer);
      if (node.get().getCh().isPresent()) {
        sb.append(node.get().toString() + " :: " + code(node.get().getCh().get()));
      } else {
        sb.append("None :: " + node.get().freq);
      }

      StringBuilder paddingBuilder = new StringBuilder(padding);
      if (hasRightSibling) {
        paddingBuilder.append("│  ");
      } else {
        paddingBuilder.append("   ");
      }

      String paddingForBoth = paddingBuilder.toString();
      String pointerRight = "└──";
      String pointerLeft = (node.get().right.isPresent()) ? "├──" : "└──";

      printLvl(sb, paddingForBoth, pointerLeft, node.get().left, node.get().right.isPresent());
      printLvl(sb, paddingForBoth, pointerRight, node.get().right, false);
    }
  }


  public Optional<String> code(char ch) {
    return code(ch, "", root);
  }

  private Optional<String> code(char ch, String acc, Optional<HuffmanNode> node) {
    if (node.isEmpty()) return empty();
    if (node.get().getCh().isPresent() && node.get().getCh().get().equals(ch)) return of(acc);
    var left = code(ch, acc + "0", node.get().left);
    var right = code(ch, acc + "1", node.get().right);
    if (left.isPresent()) return left;
    return right;
  }

  private void makeTree() {
      Collections.sort(this.forest);
      while (forest.size() > 1) {
        var sm = forest.remove(0);
        var hi = forest.remove(0);
        var node = new HuffmanNode(sm, hi, sm.freq + hi.freq);
        if (forest.size() > 0) {
          boolean inserted = false;
          for (int ix : range(forest.size())) {
            if (node.freq < forest.get(ix).freq) {
              forest.add(ix, node);
              inserted = true;
              break;
            }
          }
          if (!inserted) forest.add(node);
        } else {
          forest.add(node);
        }
      }
      if (forest.size() == 1) root = of(forest.get(0));
  }


  private List<HuffmanNode> inorder(List<HuffmanNode> acc, Optional<HuffmanNode> node) {
    if (node.isEmpty()) return acc;
    if (node.get().left.isPresent()) inorder(acc, node.get().left);
    acc.add(node.get());
    if (node.get().right.isPresent()) inorder(acc, node.get().right);
    return acc;
  }

  private List<HuffmanNode> preorder(List<HuffmanNode> acc, Optional<HuffmanNode> node) {
    if (node.isEmpty()) return acc;
    acc.add(node.get());
    if (node.get().right.isPresent()) preorder(acc, node.get().right);
    if (node.get().left.isPresent()) preorder(acc, node.get().left);
    return acc;
  }

  @Override
  public Iterator<HuffmanNode> iterator() {
    return new HuffmanTreeIterator();
  }

  private class HuffmanTreeIterator implements Iterator<HuffmanNode> {
    private List<HuffmanNode> sorted = preorder(new ArrayList<>(), root);
    private int ix = 0;

    @Override
    public boolean hasNext() {
      return ix < sorted.size();
    }

    @Override
    public HuffmanNode next() {
      return sorted.get(ix++);
    }
  }
}
