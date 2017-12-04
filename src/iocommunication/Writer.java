/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iocommunication;

import algorithms.BellmanFordAlgorithm;
import static costminimizer.CostMinimizer.*;
import static java.lang.Math.abs;

/**
 *
 * @author Kuba
 */
public class Writer {
    public void wypiszOptymalneRozwiazanie() {
        int id, from, to, cost, flow;
        int liczbaFerm = matrixConstraints.getRowNumber();
        int licbaSklepow = matrixConstraints.getColumnNumber();
        BellmanFordAlgorithm bf = new BellmanFordAlgorithm();

        for (int i = liczbaFerm + 1; i <= licbaSklepow + liczbaFerm; i++) {
            for (int j = 1; j <= liczbaFerm; j++) {
                if ((id = bf.getNumberOfEdgeInResidualGraph(i, j)) != -1) {
                    from = residualGraph.get(id).getFrom();
                    to = residualGraph.get(id).getTo();
                    cost = residualGraph.get(id).getCost();
                    flow = residualGraph.get(id).getFlow();

                    farm.getValue(from);
                    shop.getValue(to);

                    System.out.println(farm.getValue(to - 1).getNazwa() + "\t\t\t\t>\t" + shop.getValue(from - liczbaFerm - 1).getNazwa() + "\t\t\t\t[Suma = " + flow + " * " + abs(cost) + " = " + (abs(flow * cost)) + "]");
                }
            }
        }
    }
}
