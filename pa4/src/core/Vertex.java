package core;

public record Vertex(double distance, int p) implements Comparable<Vertex> {

  @Override
  public int compareTo(Vertex other) {
    return ((Double) distance).compareTo(other.distance);
  }

}
