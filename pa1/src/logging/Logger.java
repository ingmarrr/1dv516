package src.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class Logger {
  private String prefix;
  private Optional<FileWriter> fw;

  public Logger(String prefix) {
    this.prefix = prefix;
  }

  public Logger(String prefix, String file) throws IOException {
    this.prefix = prefix;
    this.fw = Optional.of(new FileWriter(file, false));
  }

  public static void println(String s) {
    System.out.println(s);
  }

  public static LogResult fprintln(String file, String s) {
    try (java.io.FileWriter fw = new java.io.FileWriter(file, false)) {
      fw.write(s + "\n");
      fw.close();
    } catch (IOException e) {
      return LogResult.ErrorWritingToFile;
    }
    return LogResult.Ok;
  }

  public LogResult fprintln(String s) {
    if (!this.fw.isPresent()) {
      return LogResult.FileNotSet;
    }
    try {
      fw.get().write(s + "\n");
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
}