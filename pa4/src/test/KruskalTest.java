import core.Edge;
import core.Problem1.Graph;
import core.Problem3.Kruskal;
import core.Problem1.UndirectedGraph;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

import java.util.ArrayList;

public class KruskalTest {

  public static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testSmallGraphWithKnownMST() throws Test.FailException {
    Graph graph = new UndirectedGraph(3);
    graph.addEdge(0, 1, 1);
    graph.addEdge(1, 2, 2);
    graph.addEdge(0, 2, 3);

    Kruskal kruskal = new Kruskal(graph);
    ArrayList<Edge> mst = kruskal.getMst();

    Test.throwAssertQuiet("Contains Edge(0-1, 1)", mst.contains(new Edge(0, 1, 1)));
    Test.throwAssertQuiet("Contains Edge(1-2, 2)", mst.contains(new Edge(1, 2, 2)));
    Test.throwAssertQuiet("Does Not Contain Edge(0-2, 3)", !mst.contains(new Edge(0, 2, 3)));
  }

  @Unit
  public void testDisconnectedGraph() throws Test.FailException {
    Graph graph = new UndirectedGraph(4);
    graph.addEdge(0, 1, 1);
    graph.addEdge(2, 3, 2);

    Kruskal kruskal = new Kruskal(graph);
    ArrayList<Edge> mst = kruskal.getMst();

    Test.throwAssertQuiet("Size == 2", mst.size() == 2);
  }

  @Unit
  public void testGraphWithParallelEdges() throws Test.FailException {
    Graph graph = new UndirectedGraph(2);
    graph.addEdge(0, 1, 1);
    graph.addEdge(0, 1, 2);

    Kruskal kruskal = new Kruskal(graph);
    ArrayList<Edge> mst = kruskal.getMst();

    Test.throwAssertQuiet("Contains Edge(0-1, 1)", mst.contains(new Edge(0, 1, 1)));
    Test.throwAssertQuiet("Does Not Contain Edge(0-1, 2)", !mst.contains(new Edge(0, 1, 2)));
  }

  @Unit
  public void testBigGraph() throws Test.FailException {
    var graph = new UndirectedGraph(7);
    graph.addEdge(0, 1, 7);
    graph.addEdge(0, 3, 6);
    graph.addEdge(0, 6, 9);
    graph.addEdge(0, 6, 9);
  }

}
