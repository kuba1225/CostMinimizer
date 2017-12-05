package costminimizer;

/**
 *
 * @author Kuba
 */
public class UserInterface {

    public void showMenu() {
        System.out.println("\nMenu");
        System.out.println("1.Wyznacz kursy dostaw.");
        System.out.println("2.Wybierz lokalizacje danych wyjsciowych.\n");
    }

    public void choseOutput() {
        System.out.println("\n1.Wyswietl dane wyjsciowe w kompilatorze.");
        System.out.println("2.Zapisz dane wyjsiowe w pliku.");
        System.out.println("3.Wyswietl dane wyjsciowe w kompilatorze oraz zapisz dane wyjsciowe w pliku.\n");
    }
}
