package costminimizer;

import algorithms.BellmanFordAlgorithm;
import algorithms.EdmondsKarpAlgorithm;
import dynamicstructures.*;
import iocommunication.Parser;
import iocommunication.Writer;
import java.util.ArrayList;

/**
 *
 * @author Kuba
 */
public class CostMinimizer {

    public static RedBlackTree<Integer, Farm> farm;
    public static RedBlackTree<Integer, Shop> shop;
    public static Matrix matrixConstraints;
    public static Matrix matrixCosts;
    public static Matrix matrixShopsOrders;
    public static Matrix matrixFermsNumberEggs;
    public static ArrayList<Graph> residualGraph;

    public CostMinimizer() {
        farm = new RedBlackTree<>();
        shop = new RedBlackTree<>();
        residualGraph = new ArrayList<>();
    }

    public void minimizeCost() {
        CostMinimizer c = new CostMinimizer();
        Parser r = new Parser("test.txt");
        r.readFile();
        EdmondsKarpAlgorithm e = new EdmondsKarpAlgorithm();
        System.out.println("Przetwarzanie grafu algorytmem Edmondsa-Karpa...");
        e.edmondsKarp();
        BellmanFordAlgorithm bf = new BellmanFordAlgorithm();
        System.out.println("Przetwarzanie grafu algorytmem Bellmana-Forda...");
        //bf.bellmanFordAlgorithm();
        System.out.println("Suma całkowita kosztów przed usuwaniem ujemnych cykli to: " + bf.obliczSume() + " $");
        while (!bf.bellmanFordAlgorithm()) {
            bf.usunUjemnyCykl();
        }
        Writer w = new Writer();
        w.wypiszOptymalneRozwiazanie();
        System.out.println("Suma całkowita kosztów po usunięciu ujemych cykli to: " + bf.obliczSume() + " $");
    }

    public static void main(String[] argv) {
        CostMinimizer c = new CostMinimizer();
        c.minimizeCost();
    }

}
