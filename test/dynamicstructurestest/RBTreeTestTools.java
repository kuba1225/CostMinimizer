
import java.util.Random;

public class RBTreeTestTools {

    /**
     * Metoda służąca do generowania losowych łańcuchów znaków o długości 10
     * liter.
     *
     * @return wygenerowany łańcuch znaków
     */
    public String wordsBuilder() {
        Random r = new Random();
        StringBuilder b = new StringBuilder();
        b.append((char) ('A' + r.nextInt(26)));
        for (int i = 1; i < 10; i++) {
            b.append((char) ('a' + r.nextInt(26)));
        }
        return b.toString();
    }

    /**
     * Metoda służąca do generowania losowych łańcuchów znaków o podanej
     * długości.
     *
     * @param n długość łańcucha znaków do wygenerowania
     * @return wygenerowany łańcuch znaków
     */
    public String wordsBuilder(int n) {
        Random r = new Random();
        StringBuilder b = new StringBuilder();
        b.append((char) ('A' + r.nextInt(26)));
        for (int i = 1; i < n; i++) {
            b.append((char) ('a' + r.nextInt(26)));
        }
        return b.toString();
    }

    /**
     * Metoda służąca do generowania losowych łańcuchów znaków o podanej
     * długości zaczynających się od podanej jako argument litery.
     *
     * @param x pierwszy znak danego Stringa
     * @param n długość łańcucha znaków do wygenerowania
     * @return wygenerowany łańcuch znaków
     */
    public String wordsBuilder(int n, char s) {
        Random r = new Random();
        StringBuilder b = new StringBuilder();
        b.append(s);
        for (int i = 1; i < n; i++) {
            b.append((char) ('a' + r.nextInt(26)));
        }
        return b.toString();
    }
}
