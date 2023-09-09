package src.core;

public class Triple<T> implements Comparable<Triple<T>> {
  public T first;
  public T second;
  public T third;

  public Triple(T first, T second, T third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }

  public String toString() {
    return String.format("(%s, %s, %s)", first, second, third);
  }

  public int compareTo(Triple<T> other) {
    return this.toString().compareTo(other.toString());
  }
}
