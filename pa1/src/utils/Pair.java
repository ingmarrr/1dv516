package src.utils;

public record Pair<T>(T first, T second) implements Comparable<Pair<T>> {

  public String toString() {
    return String.format("(%s, %s)", first, second);
  }

  public int compareTo(Pair<T> other) {
    return this.toString().compareTo(other.toString());
  }
}
