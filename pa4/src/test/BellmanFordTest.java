import core.Problem1.DirectedGraph;
import core.Problem4.BellmanFord;
import core.Problem4.Dijkstra;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

import java.util.Arrays;

public class BellmanFordTest {
  public static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testBellmanFord() throws Test.FailException {
    var digraph = new DirectedGraph(8);
    digraph.addEdge(0, 1, 5);
    digraph.addEdge(0, 3, 8);
    digraph.addEdge(0, 6, 9);
    digraph.addEdge(1, 2, 15);
    digraph.addEdge(1, 3, 4);
    digraph.addEdge(2, 7, 9);
    digraph.addEdge(3, 4, 7);
    digraph.addEdge(3, 5, 6);
    digraph.addEdge(4, 2, 3);
    digraph.addEdge(4, 7, 11);
    digraph.addEdge(5, 4, 1);
    digraph.addEdge(5, 7, 13);
    digraph.addEdge(6, 3, 5);
    digraph.addEdge(6, 5, 4);
    digraph.addEdge(6, 7, 20);

    var bellmanFord = new BellmanFord(digraph, 0);
    Test.throwAssertQuiet("Distance to 1 == 5", bellmanFord.distanceTo(1) == 5);
    Test.throwAssertQuiet("Distance to 3 == 8", bellmanFord.distanceTo(3) == 8);
    Test.throwAssertQuiet("Distance to 6 == 9", bellmanFord.distanceTo(6) == 9);
    Test.throwAssertQuiet("Distance to 5 == 13", bellmanFord.distanceTo(5) == 13);
    Test.throwAssertQuiet("Distance to 2 == 17", bellmanFord.distanceTo(2) == 17);
    Test.throwAssertQuiet("Distance to 7 == 25", bellmanFord.distanceTo(7) == 25);
  }

}
