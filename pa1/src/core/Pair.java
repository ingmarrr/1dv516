package src.core;

public class Pair<T> implements Comparable<Pair<T>> {
  public T first;
  public T second;

  public Pair(T first, T second) {
    this.first = first;
    this.second = second;
  }

  public String toString() {
    return String.format("(%s, %s)", first, second);
  }

  public int compareTo(Pair<T> other) {
    return this.toString().compareTo(other.toString());
  }
}
