package costminimizer;

import algorithms.*;
import dynamicstructures.*;
import iocommunication.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

    public void minimizeCost(String filename) throws FileNotFoundException, IOException, NumberFormatException {
        Parser r = new Parser(filename);

        r.readFile();

        EdmondsKarpAlgorithm e = new EdmondsKarpAlgorithm();
        e.edmondsKarp();

        BellmanFordAlgorithm bf = new BellmanFordAlgorithm();
        //bf.bellmanFordAlgorithm();

        while (!bf.bellmanFordAlgorithm()) {
            bf.usunUjemnyCykl();
        }
    }

    public static void main(String[] argv) {
        CostMinimizer c;
        UserInterface ui = new UserInterface();
        Writer w = new Writer();
        int destination = 1;
        String destinationFileName = null;
        System.out.println("CostMinimizer");
        System.out.println("Wpisz \"menu\" - aby wyswietlic menu, \"quit\" - aby wyjsc");
        ui.showMenu();

        Scanner sc = new Scanner(System.in);
        String s;
        System.out.printf("<CostMinimizer> ");
        while (!(s = sc.nextLine()).equals("quit")) {
            if (s.equals("menu")) {
                ui.showMenu();
            } else if (s.equals("1")) {
                System.out.printf("Podaj lokalizacje pliku z danymi wejściowymi: ");
                s = sc.nextLine();
                try {
                    c = new CostMinimizer();
                    c.minimizeCost(s);

                    if (destination == 1) {
                        w.writeResultsToStdOut();
                    } else if (destination == 2) {
                        try {
                            w.writeResultsToFile(destinationFileName);
                        } catch (FileNotFoundException ex) {
                            System.err.println("NIEUDANA PRÓBA OTWARCIA PLIKU \"" + destinationFileName + "\"");
                        }
                    } else if (destination == 3) {
                        w.writeResultsToStdOut();
                        try {
                            w.writeResultsToFile(destinationFileName);
                        } catch (FileNotFoundException ex) {
                            System.err.println("NIEUDANA PRÓBA OTWARCIA PLIKU \"" + destinationFileName + "\"");
                        }
                    }
                } catch (FileNotFoundException ex) {
                    System.err.println("NIEUDANA PRÓBA OTWARCIA PLIKU \"" + s + "\"");
                } catch (IOException ex) {
                    System.err.println("NIEZNANY BŁĄD ZWIĄZANY Z OBSŁUGĄ PLIKU \"" + s + "\"");
                } catch (NumberFormatException ex) {
                    System.err.println(ex.getMessage());
                }
            } else if (s.equals("2")) {
                while (true) {
                    ui.choseOutput();
                    System.out.printf("<CostMinimizer> ");
                    s = sc.nextLine();

                    if (s.equals("1")) {
                        destination = 1;
                        break;
                    } else if (s.equals("2")) {
                        destination = 2;
                        System.out.print("Podaj nazwe pliku: ");
                        destinationFileName = sc.nextLine();
                        break;
                    } else if (s.equals("3")) {
                        destination = 3;
                        System.out.print("Podaj nazwe pliku: ");
                        destinationFileName = sc.nextLine();
                        break;
                    } else if (s.equals("quit")) {
                        break;
                    }
                }
            }
            System.out.printf("<CostMinimizer> ");
        }
    }
}
