package iocommunication;

import static costminimizer.CostMinimizer.*;
import costminimizer.Tools;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import static java.lang.Math.abs;

/**
 *
 * @author Kuba
 */
public class Writer {

    public void writeResultsToStdOut() {
        int id, from, to, cost, flow;
        int liczbaFerm = matrixConstraints.getRowNumber();
        int licbaSklepow = matrixConstraints.getColumnNumber();
        Tools t = new Tools();

        System.out.println();
        for (int i = liczbaFerm + 1; i <= licbaSklepow + liczbaFerm; i++) {
            for (int j = 1; j <= liczbaFerm; j++) {
                if ((id = t.getNumberOfEdgeInResidualGraph(i, j)) != -1) {
                    from = residualGraph.get(id).getFrom();
                    to = residualGraph.get(id).getTo();
                    cost = residualGraph.get(id).getCost();
                    flow = residualGraph.get(id).getFlow();

                    farm.getValue(from);
                    shop.getValue(to);

                    System.out.printf("%-50s -> \t%-50s [Suma = %d * %d = %d]\n", farm.getValue(to - 1).getNazwa(), shop.getValue(from - liczbaFerm - 1).getNazwa(), abs(flow), abs(cost), (abs(flow * cost)));
                }
            }
        }
        System.out.println("\nCałkowita suma kosztów: " + t.returnSumOfCosts() + " $\n");
    }

    public void writeResultsToFile(String nazwaPliku) throws FileNotFoundException {
        int id, from, to, cost, flow;
        int liczbaFerm = matrixConstraints.getRowNumber();
        int licbaSklepow = matrixConstraints.getColumnNumber();
        Tools t = new Tools();

        PrintWriter p = new PrintWriter(nazwaPliku);

        for (int i = liczbaFerm + 1; i <= licbaSklepow + liczbaFerm; i++) {
            for (int j = 1; j <= liczbaFerm; j++) {
                if ((id = t.getNumberOfEdgeInResidualGraph(i, j)) != -1) {
                    from = residualGraph.get(id).getFrom();
                    to = residualGraph.get(id).getTo();
                    cost = residualGraph.get(id).getCost();
                    flow = residualGraph.get(id).getFlow();

                    farm.getValue(from);
                    shop.getValue(to);

                    p.printf("%-50s -> \t%-50s [Suma = %d * %d = %d]\n", farm.getValue(to - 1).getNazwa(), shop.getValue(from - liczbaFerm - 1).getNazwa(), abs(flow), abs(cost), (abs(flow * cost)));
                }
            }
        }
        p.println("\nCałkowita suma kosztów: " + t.returnSumOfCosts() + " $\n");
        p.close();
    }

}
