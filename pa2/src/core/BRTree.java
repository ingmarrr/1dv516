package core;

import jdk.jshell.spi.ExecutionControl;
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
          var newN = new BRNode<>(val, of(parent), BRNode.RelToParent.Left);
          parent.left = of(newN);
          yield newN;
        } else {
          yield add(val, parent.left.get());
        }
      }
      case -1 -> {
        if (parent.right.isEmpty()) {
          var newN = new BRNode<>(val, of(parent), BRNode.RelToParent.Right);
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

  private void swap(Optional<BRNode<E>> left, Optional<BRNode<E>> right) {
    var tmp = left;
    left = right;
    right = tmp;
  }

  public Optional<BRNode<E>> del(E val) {
    var node = del(val, root);
    if (node.isPresent() && node.get().getColor().isRed()) fixDel(node.get());
    return node;
  }

  private Optional<BRNode<E>> del(E val, Optional<BRNode<E>> node) {
      if (node.isEmpty()) return empty();
      return switch (Integer.signum(node.get().getVal().compareTo(val))) {
        case 1 -> del(val, node.get().left);
        case -1 -> del(val, node.get().right);
        default -> {
          var no = node.get();
          Optional<BRNode<E>> toFix;

          yield  switch (no.getColor()) {
            case Red -> delRed(no);
            case Black -> delBlack(no);
            default -> empty();
          };
        }
      };
  }

  private Optional<BRNode<E>> delBlack(BRNode<E> node) {
    var noChildren = node.left.isEmpty() && node.right.isEmpty();
    var both       = node.left.isPresent() && node.right.isPresent();
    var hasRight   = node.right.isPresent();
    var hasLeft    = node.left.isPresent();

    if (root.get().equals(node)) {
      if (noChildren) {
        root = empty();
        return empty();
      }
      if (both) {
        var clone = successor(node.left.get());
        clone.parent = empty();
        clone.left = node.left;
        clone.right = node.right;
        clone.setRel(BRNode.RelToParent.None);
        clone.setColor(BRNode.Color.Black);
        if (clone.left.isPresent()) clone.left.get().parent = of(clone);
        if (clone.right.isPresent()) clone.right.get().parent = of(clone);
        root = of(clone);
        return del(clone.getVal(), node.left);
      }
      var child = node.left.isPresent() ? node.left.get() : node.right.get();
      root = of(child);
      child.parent = empty();
      child.setColor(BRNode.Color.Black);
      child.setRel(BRNode.RelToParent.None);
      return empty();
    }

    var parent = node.parent.get();

    if (noChildren) {
      switch (node.getRel()) {
        case Left -> {
          parent.left = empty();
          return doubleBlack(BRNode.nil(parent, BRNode.RelToParent.Left));
        }
        case Right -> {
          parent.right = empty();
          return doubleBlack(BRNode.nil(parent, BRNode.RelToParent.Right));
        }
      }
    }

    if (both) {
      var clone = successor(node.left.get());
      clone.parent = of(parent);
      clone.left = node.left;
      clone.right = node.right;
      clone.setRel(node.getRel());
      if (node.getRel() == BRNode.RelToParent.Left) {
        parent.left = of(clone);
      } else {
        parent.right = of(clone);
      }
      return del(clone.getVal(), node.left);
    }

    switch (node.getRel()) {
      case Left -> {
        if (hasLeft) {
          if (node.left.get().getColor().isRed()) {
            node.left.get().setColor(BRNode.Color.Black);
            parent.left = node.left;
            node.left.get().parent = of(parent);
          } else {
            parent.left = node.left;
            node.left.get().parent = of(parent);
            return doubleBlack(node.left.get());
          }
        }
        if (hasRight) {
          if (node.right.get().getColor().isRed()) {
            node.right.get().setColor(BRNode.Color.Black);
            parent.left = node.right;
            node.right.get().parent = of(parent);
          } else {
            parent.left = node.right;
            node.right.get().parent = of(parent);
            return doubleBlack(node.right.get());
          }
        }
      }
      case Right -> {
        if (hasLeft) {
          if (node.left.get().getColor().isRed()) {
            node.left.get().setColor(BRNode.Color.Black);
            parent.right = node.left;
            node.left.get().parent = of(parent);
          } else {
            parent.right = node.left;
            node.left.get().parent = of(parent);
            return doubleBlack(node.left.get());
          }
        }
        if (hasRight) {
          if (node.right.get().getColor().isRed()) {
            node.right.get().setColor(BRNode.Color.Black);
            parent.right = node.right;
            node.right.get().parent = of(parent);
          } else {
            parent.right = node.right;
            node.right.get().parent = of(parent);
            log.error("Del", node);
            return doubleBlack(node.right.get());
          }
        }
      }
    }
    return empty();
  }

  private BRNode<E> successor(BRNode<E> node) {
    var s = node.clone();
    while (s.left.isPresent()) {
      s = s.left.get().clone();
    }
    return s;
  }

  private Optional<BRNode<E>> sibling(BRNode<E> node) {
    if (node.getRel() == BRNode.RelToParent.Left) {
      return node.parent.get().right;
    } else {
      return node.parent.get().left;
    }
  }

  private boolean redChild(BRNode<E> node) {
    return node.left.isPresent() && node.left.get().getColor().isRed() ||
        node.right.isPresent() && node.right.get().getColor().isRed();
  }

  private Optional<BRNode<E>> doubleBlack(BRNode<E> node) {
    if (root.get().equals(node)) return empty();
    var sib = sibling(node);
    if (sib.isEmpty()) return doubleBlack(node.parent.get());
    switch (sib.get().getColor()) {
      case Red -> {
        node.parent.get().setColor(BRNode.Color.Red);
        sibling(node).get().setColor(BRNode.Color.Black);
        switch (node.getRel()) {
          case Right -> rotateRight(node.parent.get());
          case Left -> rotateLeft(node.parent.get());
        }
        return doubleBlack(node);
      }
      case Black -> {
        if (redChild(sib.get())) {
          if (sib.get().left.isPresent() && sib.get().left.get().getColor().isRed()) {
            sibling(node).get().left.get().setColor(BRNode.Color.Black);
            sibling(node).get().setColor(node.parent.get().getColor());
            rotateRight(sibling(node).get());
          }
          sibling(node).get().right.get().setColor(BRNode.Color.Black);
          sibling(node).get().setColor(node.parent.get().getColor());
          rotateLeft(node.parent.get());
        } else {
          sibling(node).get().setColor(BRNode.Color.Red);
          if (node.parent.get().getColor().isBlack()) {
            return doubleBlack(node.parent.get());
          } else {
            node.parent.get().setColor(BRNode.Color.Black);
          }
        }
      }
    }

    return empty();
  }

  private Optional<BRNode<E>> delRed(BRNode<E> node) {
    var noChildren = node.left.isEmpty() && node.right.isEmpty();
    var both       = node.left.isPresent() && node.right.isPresent();
    var noRight    = node.right.isEmpty();
    var noLeft     = node.left.isEmpty();
    var parent     = node.parent.get();

    switch (node.getRel()) {
      case Left -> {
        parent.left.get().parent = of(parent);
        if (noChildren) parent.left = empty();
        if (noRight)    parent.left = node.left;
        if (noLeft)     parent.left = node.right;
        if (both)       parent.left = node.right;
        if (both) return del(node.right.get().getVal(), node.right);
      }
      case Right -> {
        parent.right.get().parent = of(parent);
        if (noChildren) parent.right = empty();
        if (noRight)    parent.right = node.left;
        if (noLeft)     parent.right = node.right;
        if (both)       parent.right = node.right;
        if (both) return del(node.right.get().getVal(), node.right);

      }
    }

    return empty();
  }

  private void fixDel(BRNode<E> node) {
    if (root.isPresent() && root.get().equals(node)) return;

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
    node.setRel(BRNode.RelToParent.Left);
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
    node.setRel(BRNode.RelToParent.Right);
  }

  private void fixParents(BRNode<E> node, Optional<BRNode<E>> tmp) {
    tmp.get().parent = node.parent;
    if (node.parent.isEmpty()) {
      root = tmp;
      root.get().setRel(BRNode.RelToParent.None);
    } else {
      switch (node.getRel()) {
        case Left -> node.parent.get().left = tmp;
        case Right -> node.parent.get().right = tmp;
      }
    }
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
}
