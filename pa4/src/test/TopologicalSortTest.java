import core.Problem1.DirectedGraph;
import core.Problem1.Graph;
import core.Problem5.TopologicalSort;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

public class TopologicalSortTest {

  public static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testTopologicalSort() throws Test.FailException {
    Graph graph = new DirectedGraph(6);
    graph.addEdge(5, 2);
    graph.addEdge(5, 0);
    graph.addEdge(4, 0);
    graph.addEdge(4, 1);
    graph.addEdge(2, 3);
    graph.addEdge(3, 1);

    var sorted = new TopologicalSort(graph).order();
  }

  @Unit
  public void testTopologicalSortBig() throws Test.FailException {
    Graph graph = new DirectedGraph(7);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(0, 5);
    graph.addEdge(1, 4);
    graph.addEdge(3, 2);
    graph.addEdge(3, 4);
    graph.addEdge(3, 5);
    graph.addEdge(3, 6);
    graph.addEdge(5, 2);
    graph.addEdge(6, 0);
    graph.addEdge(6, 4);

    var sorted = new TopologicalSort(graph).order();
  }
}
