package core;

import logging.Logger;
import logging.Mode;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class BRTree<E extends Comparable<E>> {

  private Logger log = Logger.builder()
      .mode(Mode.Main)
      .emoji(true)
      .modeEmoji(true)
      .build();
  private Optional<BRNode<E>> root;

  public BRTree() {
    root = empty();
  }

  public void add(E val) {
    if (root.isEmpty()) {
      root = of(new BRNode<>(val, BRNode.Color.Black, BRNode.RelToParent.None));
    } else {
      var node = add(val, root.get());
      fix(node);
    }
  }

  private BRNode<E> add( E val, BRNode<E> parent ) {
    return switch (Integer.signum(parent.getVal().compareTo(val))) {
      case 1 -> {
        if (parent.left.isEmpty()) {
          var newN = new BRNode<>(val, parent, BRNode.RelToParent.Left);
          parent.left = of(newN);
          yield newN;
        } else {
          yield add(val, parent.left.get());
        }
      }
      case -1 -> {
        if (parent.right.isEmpty()) {
          var newN = new BRNode<>(val, parent, BRNode.RelToParent.Right);
          parent.right = of(newN);
          yield newN;
        } else {
          yield add(val, parent.right.get());
        }
      }
      default -> {
        yield parent;
      }
    };
  }

  private void fix(BRNode<E> node) {
    if (root.isEmpty()) return;
    if (node == root.get() || !(node.getColor().isRed() && node.parent.get().getColor().isRed())) return;
    var par   = node.parent.get();
    var gpar  = par.parent.get();
    var uncle = par.getRel() == BRNode.RelToParent.Left ? gpar.right : gpar.left;

    if (uncle.isPresent() && uncle.get().getColor().isRed()) {
      uncle.get().setColor(BRNode.Color.Black);
      par.setColor(BRNode.Color.Black);
      gpar.setColor(BRNode.Color.Red);
      root.get().setColor(BRNode.Color.Black);
      fix(gpar);
      return;
    }

    switch (par.getRel()) {
      case Left -> {
        if (node.getRel() == BRNode.RelToParent.Right) {
          rotateLeft(par);
          node = par;
          par = node.parent.get();
        }
        rotateRight(gpar);
        swapCols(par, gpar);
        fix(par);
      }
      case Right -> {
        if (node.getRel() == BRNode.RelToParent.Left) {
          rotateRight(par);
          node = par;
          par = node.parent.get();
        }
        rotateLeft(gpar);
        swapCols(par, gpar);
        fix(par);
      }
    }
    root.get().setColor(BRNode.Color.Black);
  }

  private void swapCols(BRNode<E> left, BRNode<E> right) {
    var tmp = left.getColor();
    left.setColor(right.getColor());
    right.setColor(tmp);
  }


  public void print() {
    print(root);
  }

  private void print(Optional<BRNode<E>> node) {
    if (node.isEmpty()) return;
    var no = node.get();
    print(no.left);
    log.debug(no.toString());
    print(no.right);
  }

  public Optional<BRNode<E>> get(E val) {
    return get(val, root);
  }

  private Optional<BRNode<E>> get(E val, Optional<BRNode<E>> node) {
    if (node.isEmpty()) return empty();
    var no = node.get();
    return switch (Integer.signum(no.getVal().compareTo(val))) {
      case 1 -> get(val, no.left);
      case -1 -> get(val, no.right);
      default -> of(no);
    };

  }

  public BRNode.Result del(E val) {
    return BRNode.Result.Err;
  }

  private void rotateLeft(BRNode<E> node) {
    var tmp = node.right;
    node.right = tmp.map(n -> n.left).orElse(empty());
    if (node.right.isPresent()) {
      node.right.get().parent = of(node);
    }
    fixParents(node, tmp);
    tmp.get().left = of(node);
    node.parent = tmp;
  }

  private void rotateRight(BRNode<E> node) {
    var tmp = node.left;
    node.left = tmp.map(n -> n.right).orElse(empty());
    if (node.left.isPresent()) {
      node.left.get().parent = of(node);
    }
    fixParents(node, tmp);
    tmp.get().right = of(node);
    node.parent = tmp;
  }

  private void fixParents(BRNode<E> node, Optional<BRNode<E>> tmp) {
    tmp.get().parent = node.parent;
    if (node.parent.isEmpty()) {
      root = tmp;
    } else {
      switch (node.getRel()) {
        case Left -> node.parent.get().left = tmp;
        case Right -> node.parent.get().right = tmp;
      }
    }
  }

}
