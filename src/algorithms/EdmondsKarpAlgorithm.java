package algorithms;

import static costminimizer.CostMinimizer.*;
import dynamicstructures.*;

/**
 *
 * @author Kuba
 */
public class EdmondsKarpAlgorithm {

    public static final int MAXINT = Integer.MAX_VALUE;
    int edges, verticles;                   //liczba krawędzi i wierzchołków
    verticleElement[] neighboursArray;      //tablica list sąsiedztwa
    Queue queue;
    int startVerticle, finalVerticle;       //wierzchołki źródła i ujścia
    int[] previousElementArray;             //tablica poprzedników
    int[] capacityResidualFlowArray;        //tablica przepustowości rezydualnych

    private class verticleElement {

        verticleElement next;
        int nextVerticle;//numer następnego węzła
        int constraints;//ograniczenia przepływu
        int flow;//przepływ
    };

    public void edmondsKarp() {
        queue = new Queue();
        int cp, u, v;
        verticleElement x, z;
        boolean testEdge = false;

        loadData();

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        //~~~~~~~~~~~~~~~~~~~~~Tworzenie grafu rezydualnego~~~~~~~~~~~~~~~~~~~~~
        for (int i = 0; i < verticles; i++) {
            for (x = neighboursArray[i]; x != null; x = x.next) {

                testEdge = false;
                for (z = neighboursArray[x.nextVerticle]; z != null; z = z.next) {
                    if (z.nextVerticle == i) {
                        testEdge = true;
                        break;
                    }
                }
                if (testEdge) {
                    continue;
                }

                z = new verticleElement();
                z.nextVerticle = i;
                z.constraints = z.flow = 0;
                z.next = neighboursArray[x.nextVerticle];
                neighboursArray[x.nextVerticle] = z;
            }
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        while (true) {
            for (int i = 0; i < verticles; i++) {
                previousElementArray[i] = -1;   //wyzerowanie tablicy poprzedników (-1 oznacza, że ten element nie został jeszcze odwiedzony)
            }
            capacityResidualFlowArray[startVerticle] = MAXINT;  //ustawienie nieskończonej przepustowości źródła

            while (!queue.empty()) {
                queue.pop();    //wyzerowanie kolejki
            }
            queue.push(startVerticle);//umieszczenie źródła w kolejce

            /*poszukiwanie ścieżki w sieci rezydualnej od s do t*/
            while (!queue.empty()) {
                testEdge = false;
                u = queue.pop();   //pobranie wierzchołka

                for (x = neighboursArray[u]; x != null; x = x.next) {//przegląznie listy sąsiadów wierzchołka u
                    cp = x.constraints - x.flow;//przepustowość rezydualna

                    if (cp != 0 && (previousElementArray[x.nextVerticle] == -1)) {
                        previousElementArray[x.nextVerticle] = u;

                        capacityResidualFlowArray[x.nextVerticle] = cp < capacityResidualFlowArray[u] ? cp : capacityResidualFlowArray[u];//przepustowość rezydualna krawędzi x->nextVerticle

                        if (x.nextVerticle == finalVerticle) {//czy sciezka siega do ujscia
                            testEdge = true;      //znaleziono sciezke
                            break;
                        } else {
                            queue.push(x.nextVerticle);      // Inaczej umieszczamy w kolejce wierzchołek x->v
                        }
                    }
                }

                if (testEdge) {
                    break;
                }           // znaleziono ścieżkę -> wychodzimy z pętli while
            }

            if (!testEdge) {
                break;//brak ścieżki -> koniec algorytmu
            }

            // Przechodzimy wstecz grafu (od finalVerticle do startVerticle)
            for (v = finalVerticle; v != startVerticle; v = u) {
                u = previousElementArray[v];                   // u to poprzednik v

                // Szukamy na liście sąsiadów u krawędzi prowadzącej do v.
                for (z = neighboursArray[u]; z != null; z = z.next) {
                    if (z.nextVerticle == v) {
                        z.flow += capacityResidualFlowArray[finalVerticle];         //zwiększenie prezpływu w kierunku zgodnym
                        break;
                    }
                }

                // Szukamy na liście sąsiadów v krawędzi prowadzącej do u.
                for (z = neighboursArray[v]; z != null; z = z.next) {
                    if (z.nextVerticle == u) {
                        z.flow -= capacityResidualFlowArray[finalVerticle];         //zmniejszenie przepływu w kierunku przeciwnm
                        break;
                    }
                }
            }
        }

        constructResidualGraph(neighboursArray, verticles);

    }

    public void loadData() {
        verticleElement x;
        //pobranie liczby wierzchołków i krawędzi
        verticles = matrixConstraints.getColumnNumber() + matrixConstraints.getRowNumber() + 2;
        edges = matrixConstraints.getColumnNumber() * matrixConstraints.getRowNumber() + (matrixConstraints.getColumnNumber() + matrixConstraints.getRowNumber());

        startVerticle = 0;              //wierzchołek źródła
        finalVerticle = verticles - 1;  //wierzchołek ujścia

        neighboursArray = new verticleElement[verticles];
        for (int i = 0; i < verticles; i++) {
            neighboursArray[i] = null;          //tablicę sąsiedztwa na początku należy wypełnić pustymi listami
        }
        previousElementArray = new int[verticles];
        capacityResidualFlowArray = new int[verticles];

        //~~~~~~~~~~~~~~~~~~~~~~~~~~WCZYTYWANIE DANYCH~~~~~~~~~~~~~~~~~~~~~~~~~~
        for (int it = 0; it < matrixConstraints.getRowNumber(); it++) {
            for (int jt = 0; jt < matrixConstraints.getColumnNumber(); jt++) {
                x = new verticleElement();
                x.nextVerticle = jt + matrixConstraints.getRowNumber() + 1;
                x.constraints = (int) matrixConstraints.get(it, jt);
                x.flow = 0;
                x.next = neighboursArray[it + 1];
                neighboursArray[it + 1] = x;
            }
        }

        for (int it = 0; it < matrixConstraints.getRowNumber(); it++) {
            x = new verticleElement();
            x.nextVerticle = it + 1;
            x.constraints = (int) matrixFermsNumberEggs.get(it, 0);
            x.flow = 0;
            x.next = neighboursArray[0];
            neighboursArray[0] = x;

        }

        for (int it = 0; it < matrixConstraints.getColumnNumber(); it++) {
            x = new verticleElement();
            x.nextVerticle = matrixConstraints.getColumnNumber() + matrixConstraints.getRowNumber() + 1;
            x.constraints = (int) matrixShopsOrders.get(0, it);
            x.flow = 0;
            x.next = neighboursArray[it + matrixConstraints.getRowNumber() + 1];
            neighboursArray[it + matrixConstraints.getRowNumber() + 1] = x;

        }
    }

    public void constructResidualGraph(verticleElement[] neighboursArray, int n) {
        Graph g;
        verticleElement x, z;
        int from, to;

        for (int i = 0; i < n; i++) {
            for (z = neighboursArray[i]; z != null; z = z.next) {
                if (z.constraints != 0) {
                    if (i == 0 || i > matrixCosts.getRowNumber()) {//gdy zaczynamy od pierwszego węzła lub gdy dochodzimy do połączeń z ostatnim węzłem
                        if (z.flow == 0) {//Przypadek nr 1
                            g = new Graph(i, z.nextVerticle, z.flow, 0);
                            residualGraph.add(g);
                        }
                        if (z.flow == z.constraints) {//Przypadek nr 2
                            g = new Graph(z.nextVerticle, i, z.flow, 0);
                            residualGraph.add(g);
                        }
                        if (z.flow < z.constraints && z.flow != 0) {//Przypadek nr 3
                            g = new Graph(i, z.nextVerticle, z.constraints - z.flow, 0);
                            residualGraph.add(g);

                            g = new Graph(z.nextVerticle, i, z.flow, 0);
                            residualGraph.add(g);
                        }
                    } else {//gdy zaczynamy od jednego z węzłów który jest fermą
                        if (z.flow == 0) {//Przypadek nr 1
                            from = i - 1;
                            to = z.nextVerticle - matrixCosts.getRowNumber() - 1;

                            g = new Graph(i, z.nextVerticle, z.constraints, (int) matrixCosts.get(from, to));
                            residualGraph.add(g);
                        }
                        if (z.flow == z.constraints) {//Przypadek nr 2
                            from = i - 1;
                            to = z.nextVerticle - matrixCosts.getRowNumber() - 1;
                            g = new Graph(z.nextVerticle, i, z.constraints, -(int) matrixCosts.get(from, to));
                            residualGraph.add(g);
                        }
                        if (z.flow < z.constraints && z.flow != 0) {//Przypadek nr 3
                            from = i - 1;
                            to = z.nextVerticle - matrixCosts.getRowNumber() - 1;

                            g = new Graph(z.nextVerticle, i, z.flow, -(int) matrixCosts.get(from, to));
                            residualGraph.add(g);

                            g = new Graph(i, z.nextVerticle, z.constraints - z.flow, (int) matrixCosts.get(from, to));
                            residualGraph.add(g);
                        }
                    }
                }
            }
        }
    }
}
