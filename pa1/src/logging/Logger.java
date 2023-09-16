package src.logging;

import java.io.File;
import java.io.IOException;

public class Logger {
  final private String prefix;
  private static final String OUT_DIR = "bench_results";

  public Logger(String prefix) {
    this.prefix = prefix;
  }

  public void println(String s) {
    System.out.println(s);
  }

  public void print(String s) {
    System.out.print(s);
  }

  public LogResult fprintln(String file, String s) {
    File logDir = new File(OUT_DIR);
    if (!logDir.exists()) {
      try {
        if (!logDir.mkdir()) {
          return LogResult.ErrorCreatingDirectory;
        }
      } catch (SecurityException e) {
        return LogResult.ErrorCreatingDirectory;
      }
    }

    try (java.io.FileWriter fw = new java.io.FileWriter(OUT_DIR + "/" + file, false)) {
      fw.write(s + "\n");
    } catch (IOException e) {
      return LogResult.ErrorWritingToFile;
    }
    return LogResult.Ok;
  }

  public LogResult aprintln(String file, String s) {
    File logDir = new File(OUT_DIR);
    if (!logDir.exists()) {
      try {
        if (!logDir.mkdir()) {
          return LogResult.ErrorCreatingDirectory;
        }
      } catch (SecurityException e) {
        return LogResult.ErrorCreatingDirectory;
      }
    }

    try (java.io.FileWriter fw = new java.io.FileWriter(OUT_DIR + "/" + file, true)) {
      fw.write(s + "\n");
    } catch (IOException e) {
      return LogResult.ErrorWritingToFile;
    }
    return LogResult.Ok;
  }

  public void log(Level level, String s) {
    System.out.println(level.toString() + "\t:: " + this.prefix + " :: " + s);
  }

  public void log(Level level, Object... os) {
    System.out.println(level.toString() + "\t:: " + this.prefix + " :: ");
    for (Object o : os) {
      System.out.print(o.toString() + " ");
    }
    System.out.println();
  }

  public void debug(String s) {
    log(Level.DEBUG, s);
  }

  public void debug(Object... os) {
    log(Level.DEBUG, os);
  }

  public void info(String s) {
    log(Level.INFO, s);
  }

  public void info(Object... os) {
    log(Level.INFO, os);
  }

  public void warn(String s) {
    log(Level.WARN, s);
  }

  public void warn(Object... os) {
    log(Level.WARN, os);
  }

  public void error(String s) {
    log(Level.ERROR, s);
  }

  public void error(Object... os) {
    log(Level.ERROR, os);
  }

  public void test(String s) {
    log(Level.TEST, s);
  }

  public void test(Object... os) {
    log(Level.TEST, os);
  }

  public void bench(String s) {
    log(Level.BENCH, s);
  }

  public void bench(Object... os) {
    log(Level.BENCH, os);
  }
}