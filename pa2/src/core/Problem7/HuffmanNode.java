package core.Problem7;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class HuffmanNode implements Comparable<HuffmanNode> {

  private final Optional<Character> ch;
  public int freq;
  public Optional<HuffmanNode> left;
  public Optional<HuffmanNode> right;


  public Optional<Character> getCh() {
    return ch;
  }

  public HuffmanNode(char ch) {
    this.ch = of(ch);
    this.freq = 0;
    left = empty();
    right = empty();
  }

  public HuffmanNode(char ch, int freq) {
    this.ch = of(ch);
    this.freq = freq;
    left = empty();
    right = empty();
  }

  public HuffmanNode(HuffmanNode left, HuffmanNode right, int freq) {
    this.ch = empty();
    this.freq = freq;
    this.left = of(left);
    this.right = of(right);
  }

  public void incr() {
    freq++;
  }

  public void print() {
    if (ch.isPresent()) System.out.println("`" + ch.get().toString().replace("\n", "\\n") + "` :: " + freq);
  }

  @Override
  public int compareTo(HuffmanNode o) {
    return Integer.compare(freq, o.freq);
  }

  @Override
  public String toString() {
    if (ch.isEmpty()) return "None ::" + freq;
    return ch.get().toString().replace("\n", "\\n") + " :: " + freq;
  }
}
