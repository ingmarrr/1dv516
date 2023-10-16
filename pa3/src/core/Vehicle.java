package core;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static range.Range.range;

public final class Vehicle {

    public final int year;
    public final Color color;
    public final String make;
    public final Plate plate;

    public Vehicle(int year, Color color, String make, Plate plate) {
      this.year = year;
      this.color = color;
      this.make = make;
      this.plate = plate;
    }

    public enum Color {
      Yellow, Orange, Red, Pink, Violet, Blue, Green, Brown, Gray
    }

    private enum Allowed {
      Letter, Digit, Either, Space;

      public String regex() {
        return switch (this) {
          case Letter -> "[a-zA-Z]";
          case Digit -> "\\d";
          case Either -> "[a-zA-Z\\d]";
          case Space -> "\\s";
        };
      }
    }

    public static final class Plate {
      private final String lt1;
      private final String lt2;
      private final String lt3;
      private final int digit1;
      private final int digit2;
      private final String strOrLetter;

      public Plate(
          String lt1,
          String lt2,
          String lt3,
          int digit1,
          int digit2,
          String strOrLetter
      ) {
        this.lt1 = lt1;
        this.lt2 = lt2;
        this.lt3 = lt3;
        this.digit1 = digit1;
        this.digit2 = digit2;
        this.strOrLetter = strOrLetter;
      }

      @Override
      public int hashCode() {
        return lt1.hashCode() + lt2.hashCode() + lt3.hashCode() + digit1 + digit2 + strOrLetter.hashCode();
      }

      public static Optional<Plate> parse(String plate) {
        var allowed = new Allowed[]{
            Allowed.Letter,
            Allowed.Letter,
            Allowed.Letter,
            Allowed.Space,
            Allowed.Digit,
            Allowed.Digit,
            Allowed.Either
        };
        String regex = Arrays.stream(allowed)
            .map(Allowed::regex)
            .collect(Collectors.joining());

        if (plate.matches(regex)) {
          return of(new Plate(
              "" + plate.charAt(0),
              "" + plate.charAt(1),
              "" + plate.charAt(2),
              plate.charAt(4) - '0',
              plate.charAt(5) - '0',
              "" + plate.charAt(6)
          ));
        }

        return empty();
      }

      private boolean matches(String ch, Allowed allowed) {
        return ch.matches(allowed.regex());
      }
    }

    @Override
    public int hashCode() {
      return year + color.hashCode() + make.hashCode();
    }
}
