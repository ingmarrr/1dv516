package src.testing;

import java.util.List;

public class TestResult {
  public final List<Case> cases;

  public TestResult(Case[] cases) {
    this.cases = List.of(cases);
  }

  public boolean passed() {
    for (Case c : this.cases) {
      if (!c.passed()) {
        return false;
      }
    }
    return true;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("TestResult: ");
    sb.append(this.passed() ? "✅" : "❌");
    sb.append("\n\n");
    for (Case c : this.cases) {
      sb.append("  ");
      sb.append(c.name);
      sb.append(" :: ");
      sb.append(c.passed() ? "✅" : "❌");
      sb.append("\n");
    }
    return sb.toString();
  }
}
