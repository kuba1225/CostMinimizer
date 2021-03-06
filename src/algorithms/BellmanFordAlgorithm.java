package algorithms;

import static costminimizer.CostMinimizer.*;
import costminimizer.Tools;
import dynamicstructures.*;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;

public class BellmanFordAlgorithm {

    public static final int MAXINT = Integer.MAX_VALUE;
    ArrayList<Integer> negativeCycle = new ArrayList<>();

    private int edges, verticles;                           //liczba krawędzi i wierzchołków grafu
    private verticleElement[] neighboursArray;              //tablica list sąsiedztwa
    private int[] previousElementArray;                     //tablica poprzedników
    private long[] costArray;                               //tablicza kosztów dojścia do wierzchołków

    private class verticleElement {

        verticleElement next;
        int nextVerticle;       //numer następnego węzła
        int cost;               //koszt przewozu
    }

    public boolean bellmanFordAlgorithm() {
        int x, y, w;               //zmienne pomocnicze
        verticleElement pv;

        verticles = matrixConstraints.getColumnNumber() + matrixConstraints.getRowNumber() + 2;
        edges = residualGraph.size();

        neighboursArray = new verticleElement[verticles];             //tablica list sąsiedztwa
        costArray = new long[verticles];                //tablicza kosztów dojścia do wierzchołków
        previousElementArray = new int[verticles];      //tablica poprzedników

        for (int i = 0; i < verticles; i++) {
            costArray[i] = MAXINT;
            previousElementArray[i] = -1;//-1 oznacza, że element nie został jeszcze odwiedzony
            neighboursArray[i] = null;
        }

        //~~~~~~~~~~~~~~~~~~~~~~~~~~WCZYTYWANIE DANYCH~~~~~~~~~~~~~~~~~~~~~~~~~~
        for (int i = 0; i < edges; i++) {
            x = residualGraph.get(i).getFrom();
            y = residualGraph.get(i).getTo();
            w = residualGraph.get(i).getCost();

            pv = new verticleElement();
            pv.nextVerticle = y;
            pv.cost = w;
            pv.next = neighboursArray[x];
            neighboursArray[x] = pv;
        }

        return bellmanFord(0);
    }

    public void usunUjemnyCykl() {
        int x, y, f, c;
        int minFlow;
        int[] cycleFlowTab = new int[negativeCycle.size() - 1];
        int t = 0;

        //1.przeszukiwanie grafu w obrebie cyklu ujemnego w celu znalezienia najmniejszego przepływu
        for (int i = 0; i < negativeCycle.size() - 1; i++) {
            for (int j = 0; j < residualGraph.size(); j++) {
                x = residualGraph.get(j).getFrom();
                y = residualGraph.get(j).getTo();
                if (x == negativeCycle.get(i) && y == negativeCycle.get(i + 1)) {
                    cycleFlowTab[t] = residualGraph.get(j).getFlow();
                    //System.out.println(residualGraph.get(j).getFlow());
                    t++;
                }
            }
        }

        minFlow = findMin(cycleFlowTab);
        //System.out.println("minflow = " + minFlow);
        //2.w zaleznosci w ktora strone cyklu przechodzi się, należy dodać lub odejąć znalezioną najnizszą wartość przepływu
        ArrayList<Graph> tmpResidualGraph = new ArrayList<>();
        int flowConstraint;
        Tools tt = new Tools();
        boolean test = true;

        //writeNegativeCycle();
        //System.out.println("Suma kosztów to : " + tt.returnSumOfCosts());
        //System.out.println("Liczba wysłanych jajek to : " + tt.returnEggNumber());
        if (minFlow > 0) {

            for (int j = 0; j < residualGraph.size(); j++) {
                x = residualGraph.get(j).getFrom();
                y = residualGraph.get(j).getTo();
                f = residualGraph.get(j).getFlow();
                c = residualGraph.get(j).getCost();
                test = true;
                for (int i = 0; i < negativeCycle.size() - 1; i++) {
                    if (negativeCycle.get(i) == x && negativeCycle.get(i + 1) == y) {//przypadek 3
                        for (int xx = j + 1; xx < residualGraph.size(); xx++) {
                            int from = residualGraph.get(j).getFrom();
                            int to = residualGraph.get(j).getTo();

                            if (from == y && to == x) {
                                residualGraph.remove(xx);
                            }
                        }

                        if (y > x) {//przechodzimy w grafie w prawo (dodatnie części cyklu)
                            if (x == 0) {
                                flowConstraint = (int) matrixFermsNumberEggs.get(y - 1, 0);
                            } else if (y == (matrixConstraints.getColumnNumber() + matrixConstraints.getRowNumber() + 1)) {
                                flowConstraint = (int) matrixShopsOrders.get(0, x - matrixConstraints.getRowNumber() - 1);
                            } else {
                                flowConstraint = (int) matrixConstraints.get(x - 1, y - matrixConstraints.getRowNumber() - 1);
                            }

                            if ((f + minFlow) == flowConstraint) {//przypadek 2
                                tmpResidualGraph.add(new Graph(y, x, flowConstraint, -(abs(c))));
                            } else {//przypadek 3
                                if ((flowConstraint - (flowConstraint - f + minFlow)) > 0) {
                                    tmpResidualGraph.add(new Graph(x, y, flowConstraint - (flowConstraint - f + minFlow), abs(c)));
                                }
                                if ((flowConstraint - f + minFlow) > 0) {
                                    tmpResidualGraph.add(new Graph(y, x, (flowConstraint - f + minFlow), -(abs(c))));
                                }
                            }
                        } else if (x > y) {//przechodzimy w grafie w lewo (ujemne części cyklu)
                            if (y == 0) {
                                flowConstraint = (int) matrixFermsNumberEggs.get(x - 1, 0);
                            } else if (x == (matrixConstraints.getColumnNumber() + matrixConstraints.getRowNumber() + 1)) {
                                flowConstraint = (int) matrixShopsOrders.get(0, y - matrixConstraints.getRowNumber() - 1);
                            } else {
                                flowConstraint = (int) matrixConstraints.get(y - 1, x - matrixConstraints.getRowNumber() - 1);
                            }

                            if ((f - minFlow) == 0) {//przypadek 1
                                tmpResidualGraph.add(new Graph(y, x, flowConstraint, (abs(c))));
                            } else {//przypadek 3
                                if ((f - minFlow) > 0) {
                                    tmpResidualGraph.add(new Graph(x, y, (f - minFlow), -(abs(c))));
                                }
                                if ((flowConstraint - (f - minFlow)) > 0) {
                                    tmpResidualGraph.add(new Graph(y, x, flowConstraint - (f - minFlow), abs(c)));
                                }
                            }
                        }
                        test = false;
                        break;
                    } else if (negativeCycle.get(i) == y && negativeCycle.get(i + 1) == x) {//przypadek 2
                        residualGraph.remove(j);
                        j--;
                        test = false;
                        break;
                    }
                }
                if (test) {//przypadek 3
                    tmpResidualGraph.add(residualGraph.get(j));

                }
            }
            //tt.writeResidualGraph();
            //writeNegativeCycle();

            //3.zamiana grafu oryginalnego z tymczasowym
            residualGraph.clear();
            residualGraph = tmpResidualGraph;

            //System.out.println("Suma całkowita kosztów to: " + obliczSume() + " $");
            //System.out.println("Całkowita liczba wysłanych jaj to: " + tt.returnEggNumber());
        }
    }

    private boolean bellmanFord(int startVerticle) {//startVerticle - wierzchołek początkowy
        int i, x;
        boolean noChanges;
        verticleElement pv;

        costArray[startVerticle] = 0;//wyzerowanie kosztu dojścia do wierzchołka początkowego
        for (i = 1; i < verticles; i++) {
            noChanges = true;//brak zmian w tablicach kosztów i poprzedników
            for (x = 0; x < verticles; x++) {
                for (pv = neighboursArray[x]; pv != null; pv = pv.next)//przechodzimy po wszystkich sąsiadach listy wierzchołka x
                {
                    if (costArray[pv.nextVerticle] > costArray[x] + pv.cost)//warunek relaksacji
                    {
                        noChanges = false;//jest zmiana w tablicach kosztów i poprzedników
                        costArray[pv.nextVerticle] = costArray[x] + pv.cost; //relaksacja krawędzi z x do sąsiada
                        previousElementArray[pv.nextVerticle] = x;           //ustawiamy wierzchołek x jako poprzednik sąsiada
                    }
                }
            }
            if (noChanges) {
                return true;         //brak zmian -> koniec
            }
        }

        // Sprawdzamy istnienie ujemnego cyklu
        for (x = 0; x < verticles; x++) {
            for (pv = neighboursArray[x]; pv != null; pv = pv.next) {
                if (costArray[pv.nextVerticle] > costArray[x] + pv.cost) {//sprawdzamy czy istnieją krawędzie nie spełniające warunku relaksacji
                    //wypisz_ujemny_cykl(A, pv, d);
                    findNegativeCycle(x);
                    return false; //false oznacza znalezienie ujemnego cyklu
                }
            }
        }
        return true;
    }

    private void findNegativeCycle(int z) {
        negativeCycle.clear();

        char[] colors = new char[verticles];
        for (int i = 0; i < verticles; i++) {
            colors[i] = 'w';//kolorujemy wszystkie węzły na biały kolor
        }
        int start = z;
        int poprzednik = previousElementArray[start];

        negativeCycle.add(start);

        colors[start] = 'g';//węzeł początkowy kolorujemy na kolor szary
        while (colors[poprzednik] != 'g' && poprzednik >= 0) {//przeszukiwanie trwa do czasu gdy dojdziemy do węzła szarego
            colors[poprzednik] = 'g';

            negativeCycle.add(poprzednik);
            poprzednik = previousElementArray[poprzednik];
        }
        negativeCycle.add(poprzednik);
        Collections.reverse(negativeCycle);

        ArrayList<Integer> tmpnegativeCycle = new ArrayList<Integer>();

        int st = negativeCycle.get(0);
        tmpnegativeCycle.add(st);
        int x;

        for (int i = 1; i < negativeCycle.size(); i++) {
            x = negativeCycle.get(i);
            if (x == st) {
                break;//upewniamy się że cykl ujemny jest zamknięty
            } else {
                tmpnegativeCycle.add(x);
            }
        }
        tmpnegativeCycle.add(st);

        negativeCycle = tmpnegativeCycle;
    }

    private int findMin(int[] x) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < x.length; i++) {
            if (x[i] < min && x[i] > 0) {
                min = x[i];
            }
        }
        //System.out.println("MinFlow = " + minFlow);
        return min;
    }

    private void writeNegativeCycle() {
        int j = 0;
        System.out.println("~~~~~~NegativeCycle~~~~~~~~");
        for (int i : negativeCycle) {
            j++;
            System.out.print(i);
            if (j < negativeCycle.size()) {
                System.out.print(" -> ");
            }
        }
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
