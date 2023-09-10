package src.utils;

public record Triple<T>(T first, T second, T third) implements Comparable<Triple<T>> {

  public String toString() {
    return String.format("(%s, %s, %s)", first, second, third);
  }

  public int compareTo(Triple<T> other) {
    return this.toString().compareTo(other.toString());
  }
}
