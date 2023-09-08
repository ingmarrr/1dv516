package src.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class Logger {
  private String prefix;
  private static final String LOG_DIR = "logs";

  public Logger(String prefix) {
    this.prefix = prefix;
  }

  public Logger(String prefix, String file) throws IOException {
    this.prefix = prefix;
  }

  public void println(String s) {
    System.out.println(s);
  }

  public LogResult fprintln(String file, String s) {
    File logDir = new File(LOG_DIR);
    if (!logDir.exists()) {
      try {
        logDir.mkdir();
      } catch (SecurityException e) {
        return LogResult.ErrorCreatingDirectory;
      }
    }

    try (java.io.FileWriter fw = new java.io.FileWriter(LOG_DIR + "/" + file, false)) {
      fw.write(s + "\n");
      fw.close();
    } catch (IOException e) {
      return LogResult.ErrorWritingToFile;
    }
    return LogResult.Ok;
  }

  public LogResult aprintln(String file, String s) {
    File logDir = new File(LOG_DIR);
    if (!logDir.exists()) {
      try {
        logDir.mkdir();
      } catch (SecurityException e) {
        return LogResult.ErrorCreatingDirectory;
      }
    }

    try (java.io.FileWriter fw = new java.io.FileWriter(LOG_DIR + "/" + file, true)) {
      fw.write(s + "\n");
      fw.close();
    } catch (IOException e) {
      return LogResult.ErrorWritingToFile;
    }
    return LogResult.Ok;
  }

  public void debug(String s) {
    System.out.println("DEBUG\t:: " + this.prefix + " :: " + s);
  }

  public void debug(Object... os) {
    System.out.print("DEBUG\t:: " + this.prefix + " :: ");
    for (Object o : os) {
      System.out.print(o.toString() + " ");
    }
    System.out.println();
  }

  public void info(String s) {
    System.out.println("INFO\t:: " + this.prefix + " :: " + s);
  }

  public void info(Object... os) {
    System.out.print("INFO\t:: " + this.prefix + " :: ");
    for (Object o : os) {
      System.out.print(o.toString() + " ");
    }
    System.out.println();
  }

  public void warn(String s) {
    System.out.println("WARN\t:: " + this.prefix + " :: " + s);
  }

  public void warn(Object... os) {
    System.out.print("WARN\t:: " + this.prefix + " :: ");
    for (Object o : os) {
      System.out.print(o.toString() + " ");
    }
    System.out.println();
  }

  public void error(String s) {
    System.out.println("ERROR\t:: " + this.prefix + " :: " + s);
  }

  public void error(Object... os) {
    System.out.print("ERROR\t:: " + this.prefix + " :: ");
    for (Object o : os) {
      System.out.print(o.toString() + " ");
    }
    System.out.println();
  }

  public void test(String s) {
    System.out.println("TEST\t:: " + this.prefix + " :: " + s);
  }

  public void test(Object... os) {
    System.out.print("TEST\t:: " + this.prefix + " :: ");
    for (Object o : os) {
      System.out.print(o.toString() + " ");
    }
    System.out.println();
  }

  public void bench(String s) {
    System.out.println("BENCH\t:: " + this.prefix + " :: " + s);
  }

  public void bench(Object... os) {
    System.out.print("BENCH\t:: " + this.prefix + " :: ");
    for (Object o : os) {
      System.out.print(o.toString() + " ");
    }
    System.out.println();
  }
}