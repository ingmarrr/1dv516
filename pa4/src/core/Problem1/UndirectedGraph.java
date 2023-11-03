package core.Problem1;

public class UndirectedGraph extends BaseGraph {

  public UndirectedGraph(int init) {
    super(init);
  }

  @Override
  public void addEdge(int from, int to) {
    super.addEdge(from, to);
    super.addEdge(to, from);
  }

  @Override
  public void removeEdge(int from, int to) {
    super.removeEdge(from, to);
    super.removeEdge(to, from);
  }

}
