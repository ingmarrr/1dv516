import core.Edge;
import core.Problem1.UndirectedGraph;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

import java.util.List;

public class UndiGraphTest {

  public static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testUnDiGraphAdd() throws Test.FailException {
    var dg = new UndirectedGraph(10);
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

    Test.throwAssertQuiet("Size == 17", dg.countEdges() == 34);

    Test.throwAssertQuiet("6-5,4,10,7", dg.adjacent(6).equals(List.of(
        new Edge(6, 5, 1.0),
        new Edge(6, 4, 1.0),
        new Edge(6, 10, 1.0),
        new Edge(6, 7, 1.0)
    )));
  }

}
