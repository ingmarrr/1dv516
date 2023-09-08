package src.benchmark;

public class Snapshot {
  private long time;
  private int iterartion;

  /**
   * @param time
   * @param data
   */
  public Snapshot(long time, int iteration) {
    this.time = time;
    this.iterartion = iteration;
  }

  /**
   * @return the time
   */
  public long getTime() {
    return time;
  }

  /**
   * @return the data
   */
  public int getIterartion() {
    return iterartion;
  }

}
