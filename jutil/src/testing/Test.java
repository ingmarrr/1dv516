package testing;

import logging.Logger;

public class Test {

  private static final Logger log = Logger.builder()
      .mode(logging.Mode.Test)
      .emoji(true)
      .build();

  public record Case(String msg, boolean left, boolean right) {
  }

  public record Cmp<T extends Comparable<V>, V extends Comparable<T>>(String msg, T left, V right) {
  }

  public static class FailException extends Exception {
    public FailException(String msg) {
      super(msg);
    }
  }

  public static void throwFail(String msg) throws FailException {
    throw new FailException(msg);
  }

  private enum Result {
    Pass, Fail, Error;

    public String toString() {
      return switch (this) {
        case Pass -> "Pass";
        case Fail -> "Fail";
        case Error -> "Error";
      };
    }

    public String toEmoji() {
      return switch (this) {
        case Pass -> "✅";
        case Fail -> "❌";
        case Error -> "🔥";
      };
    }
  }

  private static void print(String msg, Result result) {
    switch (result) {
      case Pass -> log.success("%s - %s", msg, result.toEmoji());
      case Fail -> log.error("%s - %s", msg, result.toEmoji());
      case Error -> log.error("%s - %s", msg, result.toEmoji());
    }
  }

  // Throwing
  public static void throwAssert(String msg, boolean left) throws FailException {
    if (left) {
      throwFail("Fail: " + msg);
    }
    print(msg, Result.Pass);
  }

  public static void throwAssert(String msg, boolean left, boolean right) throws FailException {
    if (left != right) {
      throwFail("Fail: " + msg);
    }
    print(msg, Result.Pass);
  }

  public static void throwAssert(String msg, Case[] cases) throws FailException {
    log.info(msg);
    for (Case c : cases) {
      if (c.left != c.right) {
        throwFail("Fail: " + c.msg);
      }
      print(c.msg, Result.Pass);
    }
  }

  public static void throwAssert(Case[] cases) throws FailException {
    for (Case c : cases) {
      if (c.left != c.right) {
        throwFail("Fail: " + c.msg);
      }
      print(c.msg, Result.Pass);
    }
  }

  public static <T extends Comparable<T>> void throwAssert(String msg, T left, T right)
      throws FailException {
    if (left.compareTo(right) != 0) {
      throwFail("Fail: " + msg);
    }
    print(msg, Result.Pass);
  }

  public static <T extends Comparable<V>, V extends Comparable<T>> void throwAssert(String msg, Cmp<T, V>[] cases,
      String failMsg) throws FailException {
    log.info(msg);
    for (Cmp<T, V> c : cases) {
      if (c.left.compareTo(c.right) != 0) {
        throw new FailException(failMsg);
      }
      print(c.msg, Result.Pass);
    }
  }

  // Non throwing

  public static void Assert(String msg, boolean left) {
    print(msg, left ? Result.Pass : Result.Fail);
  }

  public static void Assert(String msg, boolean left, boolean right) {
    print(msg, left == right ? Result.Pass : Result.Fail);
  }

  public static void Assert(String msg, Case[] cases) {
    log.info(msg);
    for (Case c : cases) {
      Result result = c.left == c.right ? Result.Pass : Result.Fail;
      print(c.msg, result);
    }
  }

  public static void Assert(Case[] cases) {
    for (Case c : cases) {
      Result result = c.left == c.right ? Result.Pass : Result.Fail;
      print(c.msg, result);
    }
  }

  public static <T extends Comparable<T>> void Assert(String msg, T left, T right) {
    print(msg, left.compareTo(right) == 0 ? Result.Pass : Result.Fail);
  }

  public static <T extends Comparable<V>, V extends Comparable<T>> void Assert(String msg, Cmp<T, V>[] cases) {
    log.info(msg);
    for (Cmp<T, V> c : cases) {
      Result result = c.left.compareTo(c.right) == 0 ? Result.Pass : Result.Fail;
      print(c.msg, result);
    }
  }

  public static <T extends Comparable<V>, V extends Comparable<T>> void Assert(Cmp<T, V>[] cases) {
    for (Cmp<T, V> c : cases) {
      Result result = c.left.compareTo(c.right) == 0 ? Result.Pass : Result.Fail;
      print(c.msg, result);
    }
  }

}
