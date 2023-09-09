package src.benchmark;

public class Snapshot {
  private float duration;
  private float mean;
  private int repeats;

  public Snapshot(float duration, float mean, int repeats) {
    this.duration = duration;
    this.mean = mean;
    this.repeats = repeats;
  }

  public float getDuration() {
    return duration;
  }

  public float getMean() {
    return mean;
  }

  public int getRepeats() {
    return repeats;
  }

  @Override
  public String toString() {
    return duration + ";" + mean + ";" + repeats;
  }
}
