package dynamicstructures;

/**
 *
 * @author Kuba
 */
public class Graph {

    private int from = 0;
    private int to = 0;
    private int flow = 0;
    private int cost = 0;

    public Graph(int from, int to, int flow, int cost) {
        this.from = from;
        this.to = to;
        this.flow = flow;
        this.cost = cost;
    }

    public Graph(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getFlow() {
        return flow;
    }

    public int getCost() {
        return cost;
    }
}
