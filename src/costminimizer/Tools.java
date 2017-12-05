package costminimizer;

import static costminimizer.CostMinimizer.matrixConstraints;
import static costminimizer.CostMinimizer.residualGraph;
import dynamicstructures.Graph;
import static java.lang.Math.abs;

/**
 *
 * @author Kuba
 */
public class Tools {

    public int getNumberOfEdgeInResidualGraph(int from, int to) {
        int x, y;
        for (int j = 0; j < residualGraph.size(); j++) {
            x = residualGraph.get(j).getFrom();
            y = residualGraph.get(j).getTo();

            if (x == from && y == to) {
                return j;//numer krawędzi w liście reprezentującej graf rezydualny
            }
        }
        return -1;//w grafie nie istnieje krawęd łącząca dwa punkty
    }

    public int returnSumOfCosts() {
        int liczbaFerm = matrixConstraints.getRowNumber();
        int licbaSklepow = matrixConstraints.getColumnNumber();
        int suma = 0;

        for (int i = liczbaFerm + 1; i <= licbaSklepow + liczbaFerm; i++) {
            for (int j = 1; j <= liczbaFerm; j++) {
                if (getNumberOfEdgeInResidualGraph(i, j) != -1) {
                    suma += (abs(residualGraph.get(getNumberOfEdgeInResidualGraph(i, j)).getCost()) * abs(residualGraph.get(getNumberOfEdgeInResidualGraph(i, j)).getFlow()));
                }
            }
        }
        return suma;
    }

    public void writeResidualGraph() {
        for (Graph g : residualGraph) {
            System.out.println(g.getFrom() + " -> " + g.getTo() + "  przepływ:" + g.getFlow() + "  koszt:" + g.getCost());
        }
    }

    public String returnStringResidualGraph() {
        StringBuilder sb = new StringBuilder();
        for (Graph g : residualGraph) {
            sb.append(g.getFrom() + " -> " + g.getTo() + "  przepływ:" + g.getFlow() + "  koszt:" + g.getCost() + "\n");
        }
        return sb.toString();
    }
}
