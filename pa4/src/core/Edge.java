package core;

public record Edge(int from, int to, double weight) implements Comparable<Edge> {

  @Override
  public int compareTo(Edge other) {
    return ((Double) weight).compareTo(other.weight);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;

    Edge edge = (Edge) other;
    return from == edge.from && to == edge.to && Double.compare(edge.weight, weight) == 0;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = from;
    result = 31 * result + to;
    temp = Double.doubleToLongBits(weight);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  public int length() {
    return to - from;
  }
}
