package benching;

public class Timer {
  private long start = 0;
  private long end = 0;

  public void start() {
    start = System.nanoTime();
  }

  public void end() {
    end = System.nanoTime();
  }

  public long duration() {
    return start - end;
  }

  public double durationMs() {
    return (start - end) / 1000;
  }
}
