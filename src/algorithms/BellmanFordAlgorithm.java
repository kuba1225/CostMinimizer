package algorithms;

import static costminimizer.CostMinimizer.*;
import dynamicstructures.*;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;

public class BellmanFordAlgorithm {

    public static final int MAXINT = Integer.MAX_VALUE;
    ArrayList<Integer> negativeCycle = new ArrayList<>();

    private int edges, verticles;                           //liczba krawędzi i wierzchołków grafu
    private verticleElement[] A;                            //tablica list sąsiedztwa
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

        A = new verticleElement[verticles];             //tablica list sąsiedztwa
        costArray = new long[verticles];                //tablicza kosztów dojścia do wierzchołków
        previousElementArray = new int[verticles];      //tablica poprzedników

        for (int i = 0; i < verticles; i++) {
            costArray[i] = MAXINT;
            previousElementArray[i] = -1;//-1 oznacza, że element nie został jeszcze odwiedzony
            A[i] = null;
        }

        //~~~~~~~~~~~~~~~~~~~~~~~~~~WCZYTYWANIE DANYCH~~~~~~~~~~~~~~~~~~~~~~~~~~
        for (int i = 0; i < edges; i++) {
            x = residualGraph.get(i).getFrom();
            y = residualGraph.get(i).getTo();
            w = residualGraph.get(i).getCost();

            pv = new verticleElement();
            pv.nextVerticle = y;
            pv.cost = w;
            pv.next = A[x];
            A[x] = pv;
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
            for (int j = 0; j < edges; j++) {
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

        //2.w zaleznosci w ktora strone cyklu przechodzi się, należy dodać lub odejąć znalezioną najnizszą wartość przepływu
        ArrayList<Graph> tmpResidualGraph = new ArrayList<>();
        int flowConstraint;
        boolean wstawionoUjemyCykl;
        boolean test = true;

        if (minFlow > 0) {
            for (int j = 0; j < residualGraph.size(); j++) {
                x = residualGraph.get(j).getFrom();
                y = residualGraph.get(j).getTo();
                f = residualGraph.get(j).getFlow();
                c = residualGraph.get(j).getCost();
                wstawionoUjemyCykl = false;

                for (int i = 0; i < negativeCycle.size() - 1; i++) {
                    if (negativeCycle.get(i) == x && negativeCycle.get(i + 1) == y) {
                        wstawionoUjemyCykl = true;
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
                            if (f != 0 && f != flowConstraint && test) {
                                j++;
                            }
                            test = true;
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
                            if (f != 0 && f != flowConstraint && test) {
                                j++;
                            }
                            test = true;
                        }
                    } else if (negativeCycle.get(i) == y && negativeCycle.get(i + 1) == x) {
                        wstawionoUjemyCykl = true;
                        test = false;
                    }
                }
                if (!wstawionoUjemyCykl) {
                    tmpResidualGraph.add(residualGraph.get(j));
                }

            }

            //3.zamiana grafu oryginalnego z tymczasowym
            residualGraph.clear();
            residualGraph = tmpResidualGraph;

            //System.out.println("Suma całkowita kosztów to: " + obliczSume() + " $");
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
                for (pv = A[x]; pv != null; pv = pv.next)//przechodzimy po wszystkich sąsiadach listy wierzchołka x
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
            for (pv = A[x]; pv != null; pv = pv.next) {
                if (costArray[pv.nextVerticle] > costArray[x] + pv.cost) {//sprawdzamy czy istnieją krawędzie nie spełniające warunku relaksacji
                    //wypisz_ujemny_cykl(A, pv, d);
                    findNegativeCycle(pv.nextVerticle);
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

        int st = negativeCycle.get(0);

        int rozmiar;

        for (rozmiar = 1; rozmiar < negativeCycle.size(); rozmiar++) {
            if (negativeCycle.get(rozmiar) == st) {
                break;//upewniamy się że cykl ujemny jest zamknięty
            }
        }

        rozmiar++;

        for (; rozmiar < negativeCycle.size(); rozmiar++) {
            negativeCycle.remove(rozmiar);//usuwamy z cyklu wszystkie krawędzie które są poza jego ,,zamknięciem''
        }
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
