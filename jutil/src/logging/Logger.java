package logging;

import java.util.Optional;

public class Logger {
  final Mode mode;
  final Optional<String> dir;
  final boolean emoji;
  final boolean modeEmoji;

  public enum Result {
    Ok,
    NoPath,
    Err;
  }

  public static LoggerBuilder builder() {
    return new LoggerBuilder();
  }

  public Logger(Mode mode, Optional<String> dir, boolean emoji, boolean modeEmoji) {
    this.mode = mode;
    this.dir = dir;
    this.emoji = emoji;
    this.modeEmoji = modeEmoji;
  }

  public void print(String msg) {
    System.out.print(msg);
  }

  public void println(Object... os) {
    for (Object o : os) {
      System.out.print(o);
    }
    System.out.println();
  }

  public void println(String msg) {
    System.out.println(msg);
  }

  private Result write(String filename, String msg, boolean append) {
    if (this.dir.isEmpty()) {
      return Result.NoPath;
    }

    final java.io.File dir = new java.io.File(this.dir.get());
    if (!dir.exists()) {
      try {
        boolean res = dir.mkdirs();
        if (!res) {
          return Result.Err;
        }
      } catch (SecurityException e) {
        return Result.Err;
      }
    }

    try (java.io.FileWriter fw = new java.io.FileWriter(this.dir.get() + "/" + filename, append)) {
      fw.write(msg);
    } catch (java.io.IOException e) {
      return Result.Err;
    }
    return Result.Ok;
  }

  public Result fprint(String filename, String msg) {
    return this.write(filename, msg, false);
  }

  public Result fprintln(String filename, String msg) {
    return this.write(filename, msg + "\n", false);
  }

  public Result aprint(String filename, String msg) {
    return this.write(filename, msg, true);
  }

  public Result aprintln(String filename, String msg) {
    return this.write(filename, msg + "\n", true);
  }

  private void log(Level level, String msg) {
    if (this.emoji) {
      if (this.modeEmoji) {
        System.out.printf("[%s/%s] %s\n", this.mode.toEmoji(), level.toEmoji(), msg);
      } else {
        System.out.printf("[%s/%s] %s\n", this.mode.toString(), level.toEmoji(), msg);
      }
    } else {
      if (this.modeEmoji) {
        System.out.printf("[%s/%s] %s\n", this.mode.toEmoji(), level.toString(), msg);
      } else {
        System.out.printf("[%s/%s] %s\n", this.mode.toString(), level.toString(), msg);
      }
    }

  }

  private void log(Level level, Object... args) {
    if (this.emoji) {
      if (this.modeEmoji) {
        System.out.printf("[%s/%s] ", this.mode.toEmoji(), level.toEmoji());
      } else {
        System.out.printf("[%s/%s] ", this.mode.toString(), level.toEmoji());
      }
    } else {
      if (this.modeEmoji) {
        System.out.printf("[%s/%s] ", this.mode.toEmoji(), level.toString());
      } else {
        System.out.printf("[%s/%s] ", this.mode.toString(), level.toString());
      }
    }
    for (Object arg : args) {
      System.out.printf(" " + arg);
    }
    System.out.println();
  }

  public void debug(String msg) {
    this.log(Level.Debug, msg);
  }

  public void debug(Object... args) {
    this.log(Level.Debug, args);
  }

  public void info(String msg) {
    this.log(Level.Info, msg);
  }

  public void info(Object... args) {
    this.log(Level.Info, args);
  }

  public void warn(String msg) {
    this.log(Level.Warn, msg);
  }

  public void warn(Object... args) {
    this.log(Level.Warn, args);
  }

  public void success(String msg) {
    this.log(Level.Success, msg);
  }

  public void success(Object... args) {
    this.log(Level.Success, args);
  }

  public void error(String msg) {
    this.log(Level.Error, msg);
  }

  public void error(Object... args) {
    this.log(Level.Error, args);
  }

}
