package iocommunication;

import static costminimizer.CostMinimizer.*;
import dynamicstructures.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import dynamicstructures.Matrix;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Kuba
 */
public class Parser {

    private int cn = 0;
    private int rn = 0;
    private String filename;

    public Parser(String filename) {
        this.filename = filename;
    }

    public void readFile() throws FileNotFoundException, IOException, NumberFormatException {

        Scanner sc = null;
        BufferedReader br = null;

        validateFile();

        br = new BufferedReader(new FileReader(filename));
        String line;

        line = br.readLine();

        findMatrixSize();

        matrixShopsOrders = new Matrix(1, cn);
        matrixFermsNumberEggs = new Matrix(rn, 1);

        int fermKey = 0;
        int idFerm = 0;
        String nameFerm = "";
        int dailyProduction = 0;

        //# Fermy drobiu (id | nazwa | dzienna produkcja)
        while (((line = br.readLine()) != null) && (!(line.replaceAll("\\s", "").charAt(0) == '#'))) {
            idFerm = 0;
            nameFerm = "";
            dailyProduction = 0;

            sc = new Scanner(line);
            if (sc.hasNextInt()) {
                idFerm = sc.nextInt();
            }
            while (!sc.hasNextInt()) {
                nameFerm += sc.next() + " ";
            }
            if (sc.hasNextInt()) {
                dailyProduction = sc.nextInt();
            }
            farm.setValue(fermKey, new Farm(idFerm, nameFerm, dailyProduction));
            matrixFermsNumberEggs.add(dailyProduction, fermKey, 0);
            fermKey++;
        }

        int x = 0;
        int idShop = 0;
        String nameShop = "";
        int dailyDemand = 0;

        //# Sklep (id | nazwa | dzienne zapotrzebowanie)
        while (((line = br.readLine()) != null) && (!(line.replaceAll("\\s", "").charAt(0) == '#'))) {
            idShop = 0;
            nameShop = "";
            dailyDemand = 0;

            sc = new Scanner(line);
            if (sc.hasNextInt()) {
                idShop = sc.nextInt();
            }
            while (!sc.hasNextInt()) {
                nameShop += sc.next() + " ";
            }
            if (sc.hasNextInt()) {
                dailyDemand = sc.nextInt();
            }
            shop.setValue(x, new Shop(idShop, nameShop, dailyDemand));
            matrixShopsOrders.add(dailyDemand, 0, x);
            x++;
        }

        matrixConstraints = new Matrix(rn, cn);
        matrixCosts = new Matrix(rn, cn);

        //# Połączenia ferm i sklepów (id_fermy | id_sklepu | dzienna_maksymalna_liczba_przewożonych_jaj | koszt_przewozu_jednego_jaja)
        while (((line = br.readLine()) != null) && (!(line.replaceAll("\\s", "").charAt(0) == '#'))) {
            String[] wrds = line.split("\\s+");
            matrixConstraints.put(Integer.parseInt(wrds[2]), Integer.parseInt(wrds[0]), Integer.parseInt(wrds[1]));
            matrixCosts.put(Integer.parseInt(wrds[3]), Integer.parseInt(wrds[0]), Integer.parseInt(wrds[1]));
        }

        br.close();
        sc.close();


        /*System.out.println("matrixFermsNumberEggs");
        matrixFermsNumberEggs.writeMatrix();
        System.out.println("matrixShopsOrders");
        matrixShopsOrders.writeMatrix();
        System.out.println("matrixConstraints");
        matrixConstraints.writeMatrix();
        System.out.println("matrixCosts");
        matrixCosts.writeMatrix();*/
    }

    public void findMatrixSize() throws FileNotFoundException, IOException {
        int row = 0;
        int column = 0;
        BufferedReader r = new BufferedReader(new FileReader(filename));
        String line;

        r.readLine();
        while (((line = r.readLine()) != null) && (!(line.replaceAll("\\s", "").charAt(0) == '#'))) {
            row++;
        }
        while (((line = r.readLine()) != null) && (!(line.replaceAll("\\s", "").charAt(0) == '#'))) {
            column++;
        }

        rn = row;//liczba ferm == liczba rzedow
        cn = column;//liczba sklepów == liczba kolumn
        r.close();
    }

    public boolean validateFile() throws FileNotFoundException, IOException {
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));

        Scanner sc = null;
        int linenumber = 0;

        line = br.readLine();
        linenumber++;
        line = line.replaceAll("\\s", "");
        if (!(line.charAt(1) == '#')) {
            throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii " + linenumber + " > Pierwsza linia pliku nie zaczyna się od znaku \"#\"!");
        }

        int fermNumber = 0;
        int shopNumber = 0;
        int constraintsNumber = 0;

        int idFerm = 0;
        String nameFerm = "";
        int dailyProduction = 0;
        //# Fermy drobiu (id | nazwa | dzienna produkcja)
        while (((line = br.readLine()) != null) && (!(line.replaceAll("\\s", "").charAt(0) == '#'))) {
            //System.out.println(line);
            linenumber++;
            idFerm = 0;
            nameFerm = "";
            dailyProduction = 0;

            String[] wrds = line.split("\\s+");
            try {
                idFerm = Integer.parseInt(wrds[0]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii " + linenumber + " > Nie podano numeru id fermy");
            }

            try {
                dailyProduction = Integer.parseInt(wrds[wrds.length - 1]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii " + linenumber + " > Nie podano dziennej produkcji fermy");
            }

            for (int i = 1; i < wrds.length - 1; i++) {
                nameFerm += wrds[i];
            }

            if (nameFerm.equals("")) {
                throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii " + linenumber + " > Nie podano nazwy fermy");
            }

            if (dailyProduction < 0) {
                throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii " + linenumber + " > Podano ujemna dzienną produkcję");
            }

            fermNumber++;
        }

        linenumber++;

        int idShop = 0;
        String nameShop = "";
        int dailyDemand = 0;
        //# Sklep (id | nazwa | dzienne zapotrzebowanie)
        while (((line = br.readLine()) != null) && (!(line.replaceAll("\\s", "").charAt(0) == '#'))) {
            linenumber++;
            idShop = 0;
            nameShop = "";
            dailyDemand = 0;

            String[] wrds = line.split("\\s+");
            try {
                idShop = Integer.parseInt(wrds[0]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii " + linenumber + " > Nie podano numeru id sklepu");
            }

            try {
                dailyDemand = Integer.parseInt(wrds[wrds.length - 1]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii " + linenumber + " > Nie podano dziennego zapotrzebowania sklepu");
            }

            for (int i = 1; i < wrds.length - 1; i++) {
                nameShop += wrds[i];
            }

            if (nameShop.equals("")) {
                throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii " + linenumber + " > Nie podano nazwy sklepu");
            }

            if (dailyDemand < 0) {
                throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii " + linenumber + " > Podano ujemne dzienne zapotrzebowanie");
            }

            shopNumber++;
        }

        linenumber++;

        while (((line = br.readLine()) != null) && (!(line.replaceAll("\\s", "").charAt(0) == '#'))) {
            linenumber++;
            String[] wrds = line.split("\\s+");
            if (Integer.parseInt(wrds[2]) < 0) {
                throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii " + linenumber + " > Podano ujemną maksymalną liczbę jaj");
            }
            if (Integer.parseInt(wrds[3]) < 0) {
                throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii " + linenumber + " > Podano ujemny koszt przewozu jednego jaja");
            }
            constraintsNumber++;
        }

        if (constraintsNumber != (fermNumber * shopNumber)) {
            throw new NumberFormatException("NIEUDANA WALIDACJA PLIKU: Nie uwzględniono wszystkich połączeń pomiędzy fermami a sklepami");
        }

        br.close();

        return true;
    }
}
