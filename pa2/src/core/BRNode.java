package core;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class BRNode<E extends Comparable<E>> {

  private E val;
  public Optional<BRNode<E>> parent;
  public Optional<BRNode<E>> left;
  public Optional<BRNode<E>> right;
  private Color color;
  private RelToParent rel;
  private boolean doubleBlack;

  public E getVal() {
    return val;
  }
  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
  public RelToParent getRel() {
    return rel;
  }

  @Override
  public String toString() {
    return "(Val: " + val.toString() + ", Left: " + left.map(BRNode::toString).orElse("None") + ", Right: " + right.map(BRNode::toString).orElse("None") + ", Parent: " + parent.map(p -> p.getVal().toString()).orElse("None") + ", Rel: " + rel.toString() + ", Color: " + color.toString() + ")";
//    return "(Val: " + val.toString() + ", Color: " + color.toString() + ", Rel: " + rel.toString() + ")";
  }

  public void setRel(RelToParent rel) {
    this.rel = rel;
  }

  public BRNode(
      E val,
      Optional<BRNode<E>> parent,
      RelToParent rel,
      Optional<BRNode<E>> left,
      Optional<BRNode<E>> right,
      Color color
  ) {
    this.val = val;
    this.parent = parent;
    this.rel = rel;
    this.left = left;
    this.right = right;
    this.color = color;
  }

  public BRNode(E val, Optional<BRNode<E>> parent, RelToParent rel) {
    this.val = val;
    this.rel = rel;
    this.parent = parent;
    left = empty();
    right = empty();
    color = Color.Red;
  }

  BRNode(E val, Color color, RelToParent rel) {
    this.val = val;
    this.color = color;
    this.rel = rel;
    left = empty();
    right = empty();
    parent = empty();
  }

  public BRNode<E> clone() {
    var brn = new BRNode<>(val, color, rel);
    brn.left = left;
    brn.right = right;
    brn.parent = parent;
    return brn;
  }

  public static BRNode nil(BRNode parent, RelToParent rel) {
    var brn = new BRNode<>(null, Color.Black, rel);
    brn.parent = of(parent);
    brn.doubleBlack = true;
    return brn;
  }

  public enum Color { Red, Black;

    public boolean isBlack() {
      return this == Black;
    }

    public boolean isRed() {
      return this == Red;
    }

    @Override
    public String toString() {
      return switch (this) {
        case Red -> "Red";
        case Black -> "Black";
      };
    }
  };
  public enum Result { Ok, Err };
  public enum RelToParent { Left, Right, None;

    @Override
    public String toString() {
      return switch (this) {
        case Left -> "Left";
        case Right -> "Right";
        case None -> "None";
      };
    }
  }

}
