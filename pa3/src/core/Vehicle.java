package core;

import jdk.jfr.Percentage;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static range.Range.range;

public final class Vehicle implements Comparable<Vehicle> {
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

  public static Vehicle random(Random rand) {

    var makes = new String[] {
        "Ford", "Chevrolet", "Toyota", "Honda", "Nissan", "Jeep", "Hyundai", "Subaru", "Kia", "GMC",
        "Dodge", "BMW", "Mercedes-Benz", "Lexus", "Volkswagen", "Mazda", "Audi", "Buick", "Cadillac",
        // "Acura", "Lincoln", "Infiniti", "Mitsubishi", "Chrysler", "Volvo", "Land
        // Rover", "Porsche",
        // "Jaguar", "Tesla", "Fiat", "Mini", "Scion", "Ram", "Maserati", "Bentley",
        // "Ferrari", "Smart",
        // "Lamborghini", "Aston Martin", "Rolls-Royce", "Lotus", "McLaren", "Alfa
        // Romeo", "Bugatti",
        // "Genesis", "Karma", "Hummer", "Maybach", "Saab", "Saturn", "Pontiac",
        // "Suzuki", "Oldsmobile",
        // "Plymouth", "Mercury", "Aston Martin", "Ferrari", "Lamborghini", "Maserati",
        // "McLaren", "Alfa"
    };

    return new Vehicle(
        rand.nextInt(100) + 1920,
        Color.values()[rand.nextInt(Color.values().length)],
        makes[rand.nextInt(makes.length)],
        new Plate(
            "" + (char) (rand.nextInt(26) + 'A'),
            "" + (char) (rand.nextInt(26) + 'A'),
            "" + (char) (rand.nextInt(26) + 'A'),
            rand.nextInt(10),
            rand.nextInt(10),
            rand.nextBoolean() ? "" + (char) (rand.nextInt(26) + 'A') : "" + rand.nextInt(10)));
  }

  @Override
  public int compareTo(Vehicle o) {
    if (year != o.year) {
      return year - o.year;
    }

    if (!make.equals(o.make)) {
      return make.compareTo(o.make);
    }

    if (!plate.toString().equals(o.plate.toString())) {
      return plate.toString().compareTo(o.plate.toString());
    }

    return color.compareTo(o.color);
  }

  public enum Color {
    Yellow, Orange, Pink, Violet, Blue, Green, Brown, Gray
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
    private final String letter1;
    private final String letter2;
    private final String letter3;
    private final int digit1;
    private final int digit2;
    private final String digitOrLetter;

    public Plate(
        String letter1,
        String letter2,
        String letter3,
        int digit1,
        int digit2,
        String digitOrLetter) {
      this.letter1 = letter1;
      this.letter2 = letter2;
      this.letter3 = letter3;
      this.digit1 = digit1;
      this.digit2 = digit2;
      this.digitOrLetter = digitOrLetter;
    }

    @Override
    public int hashCode() {
      return letter1.hashCode() + letter2.hashCode() + letter3.hashCode() + digit1 + digit2 + digitOrLetter.hashCode();
    }

    public static Optional<Plate> parse(String plate) {
      var allowed = new Allowed[] {
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
            "" + plate.charAt(6)));
      }

      return empty();
    }

    private boolean matches(String ch, Allowed allowed) {
      return ch.matches(allowed.regex());
    }

    @Override
    public String toString() {
      return letter1 + letter2 + letter3 + digit1 + digit2 + digitOrLetter;
    }
  }

  @Override
  public int hashCode() {
    return Math.abs(
        year
        + color.hashCode()
        + make.hashCode()
        + plate.hashCode()
    );
  }

  @Override
  public String toString() {
    return String.format("%d\t%s\t%-20s\t%s", year, color, make, plate);
  }
}
