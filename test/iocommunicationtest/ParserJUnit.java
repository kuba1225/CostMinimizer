package iocommunicationtest;

import costminimizer.CostMinimizer;
import iocommunication.Parser;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kuba
 */
public class ParserJUnit {

    public ParserJUnit() {
    }

    @Test
    public void firstLineValidationTest() throws IOException {
        CostMinimizer cm = new CostMinimizer();
        Parser p = new Parser("test\\iocommunicationtest\\testfiles\\firstlinetest.txt");
        try {
            p.readFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii 1 > Pierwsza linia pliku nie zaczyna się od znaku \"#\"!", e.getMessage());
        }
    }

    @Test
    public void lackOfSomeConnectionValidationTest() throws IOException {
        CostMinimizer cm = new CostMinimizer();
        Parser pd = new Parser("test\\iocommunicationtest\\testfiles\\lackofsomeconnectiontest.txt");
        try {
            pd.validateFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nie uwzględniono wszystkich połączeń pomiędzy fermami a sklepami", e.getMessage());
        }
    }

    @Test
    public void negativeCostValidationTest() throws IOException {
        CostMinimizer cm = new CostMinimizer();
        Parser p = new Parser("test\\iocommunicationtest\\testfiles\\negativecosttest.txt");
        try {
            p.readFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii 13 > Podano ujemny koszt przewozu jednego jaja", e.getMessage());
        }
    }

    @Test
    public void negativeMaxEggNumberTestValidationTest() throws IOException {
        Parser p = new Parser("test\\iocommunicationtest\\testfiles\\negativemaxeggnumbertest.txt");
        try {
            p.readFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii 13 > Podano ujemną maksymalną liczbę jaj", e.getMessage());
        }
    }

    @Test
    public void negativeFermDailyProductionTestValidationTest() throws IOException {
        Parser p = new Parser("test\\iocommunicationtest\\testfiles\\negativefermdailyproductiontest.txt");
        try {
            p.readFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii 3 > Podano ujemna dzienną produkcję", e.getMessage());
        }
    }

    @Test
    public void negativeShopDailyDemandTestValidationTest() throws IOException {
        Parser p = new Parser("test\\iocommunicationtest\\testfiles\\negativeshopdailydemandtest.txt");
        try {
            p.readFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii 7 > Podano ujemne dzienne zapotrzebowanie", e.getMessage());
        }
    }

    @Test
    public void noFermDailyProductionTestValidationTest() throws IOException {
        Parser p = new Parser("test\\iocommunicationtest\\testfiles\\nofermdailyproductiontest.txt");
        try {
            p.readFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii 3 > Nie podano dziennej produkcji fermy", e.getMessage());
        }
    }

    @Test
    public void noFermIdTestValidationTest() throws IOException {
        Parser p = new Parser("test\\iocommunicationtest\\testfiles\\nofermidtest.txt");
        try {
            p.readFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii 3 > Nie podano numeru id fermy", e.getMessage());
        }
    }

    @Test
    public void noFermNameTestValidationTest() throws IOException {
        Parser p = new Parser("test\\iocommunicationtest\\testfiles\\nofermnametest.txt");
        try {
            p.readFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii 3 > Nie podano nazwy fermy", e.getMessage());
        }
    }

    @Test
    public void noShopDailyDemandTestValidationTest() throws IOException {
        Parser p = new Parser("test\\iocommunicationtest\\testfiles\\noshopdailydemandtest.txt");
        try {
            p.readFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii 7 > Nie podano dziennego zapotrzebowania sklepu", e.getMessage());
        }
    }

    @Test
    public void noShopIdTestValidationTest() throws IOException {
        Parser p = new Parser("test\\iocommunicationtest\\testfiles\\noshopidtest.txt");
        try {
            p.readFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii 7 > Nie podano numeru id sklepu", e.getMessage());
        }
    }

    @Test
    public void noShopNameTestValidationTest() throws IOException {
        Parser p = new Parser("test\\iocommunicationtest\\testfiles\\noshopnametest.txt");
        try {
            p.readFile();
        } catch (NumberFormatException e) {
            assertEquals("NIEUDANA WALIDACJA PLIKU: Nieudana próba odczytu w linii 8 > Nie podano nazwy sklepu", e.getMessage());
        }
    }

}
