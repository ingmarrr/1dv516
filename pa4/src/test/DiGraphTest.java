import core.Problem1.DirectedGraph;
import core.Edge;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

import java.util.List;

public class DiGraphTest {

  public static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testDiGraphAdd() throws Test.FailException {
    var dg = new DirectedGraph(10);
    dg.addEdge(2, 0);
    dg.addEdge(2, 3);
    dg.addEdge(3, 1);
    dg.addEdge(3, 4);
    dg.addEdge(4, 3);
    dg.addEdge(5, 4);
    dg.addEdge(5, 6);
    dg.addEdge(6, 4);
    dg.addEdge(6, 10);
    dg.addEdge(7, 6);
    dg.addEdge(7, 8);
    dg.addEdge(8, 9);
    dg.addEdge(10, 9);
    dg.addEdge(12, 11);
    dg.addEdge(12, 13);
    dg.addEdge(13, 14);
    dg.addEdge(14, 12);

    Test.throwAssertQuiet("Size == 17", dg.countEdges() == 17);
    Test.throwAssertQuiet("2-0, 2-3", dg.adjacent(2).equals(List.of(
        new Edge(2, 0, 1.0),
        new Edge(2, 3, 1.0)
    )));
//    Test.throwAssertQuiet("3-1, 3-4", dg.adjacent(3).equals(List.of(1, 4)));
//    Test.throwAssertQuiet("7-6, 7-8", dg.adjacent(7).equals(List.of(6, 8)));
//    Test.throwAssertQuiet("12-11, 12-13", dg.adjacent(12).equals(List.of(11, 13)));
  }
}
