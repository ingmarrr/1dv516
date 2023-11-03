package core.Problem6;

import core.Problem6.AVLNode;
import logging.Logger;
import logging.Mode;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class AVLTree<E extends Comparable<E>> {

  private static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();
  private Optional<AVLNode<E>> root;

  public AVLTree() {
    root = empty();
  }

  public Optional<AVLNode<E>> getRoot() {
    return root;
  }

  public int size() {
    return size(root);
  }

  private int size(Optional<AVLNode<E>> node) {
    if (node.isEmpty()) return 0;
    return size(node.get().left) + size(node.get().right) + 1;
  }


  public Optional<AVLNode<E>> get(E val) {
    return get(val, root);
  }

  private Optional<AVLNode<E>> get(E val, Optional<AVLNode<E>> node) {
    if (node.isEmpty()) return empty();
    var no = node.get();
    return switch (Integer.signum(no.getVal().compareTo(val))) {
      case 1 -> get(val, no.left);
      case -1 -> get(val, no.right);
      default -> of(no);
    };
  }

  public void add(E val) {
    root = add(val, root);
  }

  private Optional<AVLNode<E>> add(E val, Optional<AVLNode<E>> node) {
    if (node.isEmpty()) return of(new AVLNode<>(val));
    var no = node.get();

    switch (Integer.signum(no.getVal().compareTo(val))) {
      case 1 -> no.left = add(val, no.left);
      case -1 -> no.right = add(val, no.right);
    }

    no.height = 1 + Math.max(
        no.left.map(l -> l.height).orElse(0),
        no.right.map(r -> r.height).orElse(0)
    );

    return balance(of(no));
  }

  public void del(E val) {
    root = del(val, root);
  }

  private Optional<AVLNode<E>> del(E val, Optional<AVLNode<E>> node) {
    if (node.isEmpty()) return node;
    var no = node.get();

    switch (Integer.signum(no.getVal().compareTo(val))) {
      case 1 -> no.left = del(val, no.left);
      case -1 -> no.right = del(val, no.right);
      case 0 -> {
        if (no.left.isEmpty()) return no.right;
        if (no.right.isEmpty()) return no.left;
        node = min(no.right);
        node.get().right = delMin(no.right);
        node.get().left = no.left;
        node.get().height = Math.max(height(node.get().left), height(node.get().right)) + 1;
        return balance(node);
      }
    }
    return balance(of(no));
  }

  private Optional<AVLNode<E>> min(Optional<AVLNode<E>> node) {
    if (node.get().left.isEmpty()) return node;
    return min(node.get().left);
  }

  private Optional<AVLNode<E>> delMin(Optional<AVLNode<E>> node) {
    if (node.get().left.isEmpty()) return node.get().right;
    node.get().left = delMin(node.get().left);
    return node;
  }

  private Optional<AVLNode<E>> balance(Optional<AVLNode<E>> node) {
    if (node.isEmpty()) return empty();
    final AVLNode<E> no = node.get();
    final int diff = no.diff();
    if (diff < - 1) {
      if (no.right.get().right.map(r -> r.height).orElse(0) < no.right.get().left.map(l -> l.height).orElse(0)) {
        no.right = rotateRight(no.right.get());
      }
      return rotateLeft(no);
    }
    if (diff > 1) {
      if (no.left.get().left.map(l -> l.height).orElse(0) < no.left.get().right.map(r -> r.height).orElse(0)) {
        no.left = rotateLeft(no.left.get());
      }
      return rotateRight(no);
    }

    return of(no);
  }

  private Optional<AVLNode<E>> rotateRight(AVLNode<E> node) {
    final Optional<AVLNode<E>> tmp = node.left;
    node.left = tmp.map(n -> n.right).orElse(empty());
    if (tmp.isPresent()) {
      tmp.get().right = of(node);
    }

    node.height = Math.max(height(node.left), height(node.right)) + 1;
    if (tmp.isPresent()) {
      tmp.get().height = Math.max(height(tmp.get().left), height(tmp.get().right)) + 1;
    }

    return tmp;
  }

  private Optional<AVLNode<E>> rotateLeft(AVLNode<E> node) {
    final Optional<AVLNode<E>> tmp = node.right;
    node.right = tmp.map(n -> n.left).orElse(empty());

    if (tmp.isPresent()) {
      tmp.get().left = of(node);
    }

    node.height = Math.max(height(node.left), height(node.right)) + 1;
    if (tmp.isPresent()) {
      tmp.get().height = Math.max(height(tmp.get().left), height(tmp.get().right)) + 1;
    }

    return tmp;
  }

  public int height() {
    return root.map(r -> r.height).orElse(0);
  }

  private int height(Optional<AVLNode<E>> node) {
    if (node.isEmpty()) return 0;
    var hleft = height(node.get().left);
    var hright = height(node.get().right);
    return 1 + Math.max(hleft, hright);
  }

  public void print() {
    print(root);
  }

  private void print(Optional<AVLNode<E>> node) {
    if (node.isEmpty()) return;
    var no = node.get();
    print(no.left);
    System.out.println(no.toString());
    print(no.right);
  }


  public void printLvl() {
    StringBuilder sb = new StringBuilder();
    printLvl(sb, "", "", root, root.get().right.isPresent());
    log.println(sb.toString());
  }

  public void printLvl(
      StringBuilder sb,
      String padding,
      String pointer,
      Optional<AVLNode<E>> node,
      boolean hasRightSibling
  ) {
    if (node.isPresent()) {
      sb.append("\n");
      sb.append(padding);
      sb.append(pointer);
      sb.append(node.get().getVal());

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
}
