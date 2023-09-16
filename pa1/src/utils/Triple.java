package src.utils;

public record Triple<T>(T first, T second, T third) {

  public String toString() {
    return String.format("(%s, %s, %s)", first, second, third);
  }

  public boolean equals(Triple<T> other) {
    if (other == null) {
      return false;
    }

    return first.equals(other.first) && second.equals(other.second) && third.equals(other.third);
  }
}
