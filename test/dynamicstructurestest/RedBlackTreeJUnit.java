
import java.io.IOException;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import dynamicstructures.RedBlackTree;

public class RedBlackTreeJUnit {

    Random r;
    RBTreeTestTools t;

    public RedBlackTreeJUnit() {
    }

    @Before
    public void setUp() {
        r = new Random();
        t = new RBTreeTestTools();
    }

    /**
     * Metoda ta sprawdza czy po próbie wstawienia do słownika węzła o takim
     * samym kluczu ulegnie zmianie jedynie jego zawartość i nie zostanie on
     * dodany do drzewa jako nowy węzeł
     */
    @Test
    public void changeNodeValue() {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<Integer, String>();

        rbt.setValue(6, "test");
        rbt.setValue(3, "test");
        rbt.setValue(8, "test");
        rbt.setValue(1, "test");
        rbt.setValue(5, "test");
        rbt.setValue(7, "test");
        rbt.setValue(9, "test");
        rbt.setValue(4, "test");

        assertEquals(rbt.getValue(6), "test");
        assertEquals(rbt.getValue(8), "test");
        assertEquals(rbt.getValue(9), "test");

        int numberOfNodes = rbt.getNumberOfAllNodes();

        rbt.setValue(6, "rabarbar");
        rbt.setValue(8, "rabarbar");
        rbt.setValue(9, "rabarbar");

        assertEquals(rbt.getValue(6), "rabarbar");
        assertEquals(rbt.getValue(8), "rabarbar");
        assertEquals(rbt.getValue(9), "rabarbar");
        assertEquals(numberOfNodes, rbt.getNumberOfAllNodes());
    }

    /**
     * Metoda ta sprawdza czy węzły skonstruowanego drzewa mają odpowiednie
     * kolory. Sprawdzenie odbywa się na podstawie porównania kolorów węzłów
     * utworzonego drzewa z kolorami węzłów drzewa o znanej strukturze.
     */
    @Test
    public void checkTreeColors() {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<Integer, String>();

        rbt.setValue(11, "test");
        rbt.setValue(2, "test");
        rbt.setValue(14, "test");
        rbt.setValue(1, "test");
        rbt.setValue(7, "test");
        rbt.setValue(15, "test");
        rbt.setValue(5, "test");
        rbt.setValue(8, "test");

        assertEquals(rbt.getColor(11), "czarny");
        assertEquals(rbt.getColor(2), "czerwony");
        assertEquals(rbt.getColor(14), "czarny");
        assertEquals(rbt.getColor(1), "czarny");
        assertEquals(rbt.getColor(7), "czarny");
        assertEquals(rbt.getColor(15), "czerwony");
        assertEquals(rbt.getColor(5), "czerwony");
        assertEquals(rbt.getColor(8), "czerwony");
    }

    /**
     * Metoda ta sprawdza czy numer węzłów drzewa zwrócony przez funkcję równy
     * jest ilości wstawionych węzłów
     */
    @Test
    public void bigAmountOfRandomData() {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<Integer, String>();
        for (int i = 0; i < 1000; i++) {
            rbt.setValue(i, t.wordsBuilder());
        }
        assertEquals(rbt.getNumberOfAllNodes(), 1000);
    }

    /**
     * Metoda ta testuje zachowanie drzewa wobec próby wstawienia dużej ilości
     * węzłów o takim samym kluczu
     */
    @Test
    public void thisSameKeyTest() {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<Integer, String>();
        for (int i = 0; i < 1000; i++) {
            rbt.setValue(1, "test");
        }
        assertEquals(rbt.getNumberOfAllNodes(), 1);
        assertEquals(rbt.getColor(1), "czarny");
        assertEquals(rbt.getValue(1), "test");
    }

    /**
     * Metoda ta testuje zachowanie drzewa wobec próby pobrania z drzewa węzła o
     * kluczu który nie został podany
     */
    @Test
    public void getValueOfNotExistingNode() {
        RedBlackTree<String, String> rbt = new RedBlackTree<String, String>();
        rbt.setValue("test1", t.wordsBuilder());
        rbt.setValue("test2", t.wordsBuilder());

        assertEquals(rbt.getValue("test3"), null);
    }

    /**
     * Metoda ta testuje zachowanie drzewa po dodaniu do jego węzłów danych typu
     * String
     */
    @Test
    public void insertStringAsData() {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<Integer, String>();
        String[] data = new String[8];
        /**
         * klucze drzewa o znanej strukturze
         */
        int[] treeKeys = {11, 2, 14, 1, 7, 15, 5, 8};

        /**
         * tworzenie danych typu string
         */
        for (int i = 0; i < data.length; i++) {
            data[i] = t.wordsBuilder(r.nextInt(10) + 1);
        }

        /**
         * wstawianie danych do drzewa
         */
        for (int i = 0; i < treeKeys.length; i++) {
            rbt.setValue(treeKeys[i], data[i]);
        }

        /**
         * test koloru poszczególnych węzłów na podstawie znajomości struktury
         * drzewa czerwono czarnego o wstawionych wcześniej kluczach
         */
        assertEquals(rbt.getColor(11), "czarny");
        assertEquals(rbt.getColor(2), "czerwony");
        assertEquals(rbt.getColor(14), "czarny");
        assertEquals(rbt.getColor(1), "czarny");
        assertEquals(rbt.getColor(7), "czarny");
        assertEquals(rbt.getColor(15), "czerwony");
        assertEquals(rbt.getColor(5), "czerwony");
        assertEquals(rbt.getColor(8), "czerwony");

        /**
         * test zwracanej wartości z drzewa na podstawie klucza
         */
        for (int i = 0; i < treeKeys.length; i++) {
            assertEquals(rbt.getValue(treeKeys[i]), data[i]);
        }
    }

    /**
     * Metoda ta testuje zachowanie drzewa po dodaniu do jego węzłów długich
     * łańcuchów danych typu String
     */
    @Test
    public void insertLongStringAsData() {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<Integer, String>();
        String[] data = new String[8];
        /**
         * klucze drzewa o znanej strukturze
         */
        int[] treeKeys = {11, 2, 14, 1, 7, 15, 5, 8};

        /**
         * tworzenie danych typu string
         */
        for (int i = 0; i < data.length; i++) {
            data[i] = t.wordsBuilder(100) + t.wordsBuilder(100);
        }

        /**
         * wstawianie danych do drzewa
         */
        for (int i = 0; i < treeKeys.length; i++) {
            rbt.setValue(treeKeys[i], data[i]);
        }

        /**
         * test koloru poszczególnych węzłów na podstawie znajomości struktury
         * drzewa czerwono czarnego o wstawionych wcześniej kluczach
         */
        assertEquals(rbt.getColor(11), "czarny");
        assertEquals(rbt.getColor(2), "czerwony");
        assertEquals(rbt.getColor(14), "czarny");
        assertEquals(rbt.getColor(1), "czarny");
        assertEquals(rbt.getColor(7), "czarny");
        assertEquals(rbt.getColor(15), "czerwony");
        assertEquals(rbt.getColor(5), "czerwony");
        assertEquals(rbt.getColor(8), "czerwony");

        /**
         * test zwracanej wartości z drzewa na podstawie klucza
         */
        for (int i = 0; i < treeKeys.length; i++) {
            assertEquals(rbt.getValue(treeKeys[i]), data[i]);
        }
    }

    /**
     * Metoda ta testuje zachowanie drzewa po dodaniu do jego węzłów danych typu
     * Double
     */
    @Test
    public void insertDoubleAsData() {
        RedBlackTree<Integer, Double> rbt = new RedBlackTree<Integer, Double>();

        Double[] data = new Double[8];
        /**
         * klucze drzewa o znanej strukturze
         */
        int[] treeKeys = {11, 2, 14, 1, 7, 15, 5, 8};

        /**
         * tworzenie danych typu string
         */
        for (int i = 0; i < data.length; i++) {
            data[i] = r.nextDouble();
        }

        /**
         * wstawianie danych do drzewa
         */
        for (int i = 0; i < treeKeys.length; i++) {
            rbt.setValue(treeKeys[i], data[i]);
        }

        /**
         * test koloru poszczególnych węzłów na podstawie znajomości struktury
         * drzewa czerwono czarnego o wstawionych wcześniej kluczach
         */
        assertEquals(rbt.getColor(11), "czarny");
        assertEquals(rbt.getColor(2), "czerwony");
        assertEquals(rbt.getColor(14), "czarny");
        assertEquals(rbt.getColor(1), "czarny");
        assertEquals(rbt.getColor(7), "czarny");
        assertEquals(rbt.getColor(15), "czerwony");
        assertEquals(rbt.getColor(5), "czerwony");
        assertEquals(rbt.getColor(8), "czerwony");

        /**
         * test zwracanej wartości z drzewa na podstawie klucza
         */
        for (int i = 0; i < treeKeys.length; i++) {
            assertEquals(rbt.getValue(treeKeys[i]), data[i]);
        }
    }

    /**
     * Metoda ta testuje zachowanie drzewa po dodaniu do jego węzłów danych typu
     * Char
     */
    @Test
    public void insertCharAsData() {
        RedBlackTree<Integer, Character> rbt = new RedBlackTree<Integer, Character>();

        Character[] data = new Character[8];
        /**
         * klucze drzewa o znanej strukturze
         */
        int[] treeKeys = {11, 2, 14, 1, 7, 15, 5, 8};

        /**
         * tworzenie danych typu string
         */
        for (int i = 0; i < data.length; i++) {
            data[i] = (char) (r.nextInt(26) + 65);
        }

        /**
         * wstawianie danych do drzewa
         */
        for (int i = 0; i < treeKeys.length; i++) {
            rbt.setValue(treeKeys[i], data[i]);
        }

        /**
         * test koloru poszczególnych węzłów na podstawie znajomości struktury
         * drzewa czerwono czarnego o wstawionych wcześniej kluczach
         */
        assertEquals(rbt.getColor(11), "czarny");
        assertEquals(rbt.getColor(2), "czerwony");
        assertEquals(rbt.getColor(14), "czarny");
        assertEquals(rbt.getColor(1), "czarny");
        assertEquals(rbt.getColor(7), "czarny");
        assertEquals(rbt.getColor(15), "czerwony");
        assertEquals(rbt.getColor(5), "czerwony");
        assertEquals(rbt.getColor(8), "czerwony");

        /**
         * test zwracanej wartości z drzewa na podstawie klucza
         */
        for (int i = 0; i < treeKeys.length; i++) {
            assertEquals(rbt.getValue(treeKeys[i]), data[i]);
        }
    }

    /**
     * Metoda ta testuje zachowanie drzewa po dodaniu węzłów o kluczach typu
     * Integer
     */
    @Test
    public void insertIntAsKey() {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<Integer, String>();
        String[] data = new String[8];
        /**
         * Klucze:
         */
        int[] treeKeys = {11, 2, 14, 1, 7, 15, 5, 8};

        /**
         * wstawianie danych do drzewa
         */
        for (int i = 0; i < treeKeys.length; i++) {
            data[i] = t.wordsBuilder();
            rbt.setValue(treeKeys[i], data[i]);
        }

        /**
         * Sprawdzamy czy poszczególne węzły drzewa mają odpowiednie kolory:
         */
        assertEquals(rbt.getColor(treeKeys[0]), "czarny");
        assertEquals(rbt.getColor(treeKeys[1]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[2]), "czarny");
        assertEquals(rbt.getColor(treeKeys[3]), "czarny");
        assertEquals(rbt.getColor(treeKeys[4]), "czarny");
        assertEquals(rbt.getColor(treeKeys[5]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[6]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[7]), "czerwony");

        /**
         * Sprawdzamy czy klucze zwracają odpowiednie dane:
         */
        for (int i = 0; i < treeKeys.length; i++) {
            assertEquals(rbt.getValue(treeKeys[i]), data[i]);
            rbt.setValue(treeKeys[i], data[i]);
        }
    }

    /**
     * Metoda ta testuje zachowanie drzewa po dodaniu węzłów o kluczach typu
     * Character
     */
    @Test
    public void insertCharAsKey() {
        RedBlackTree<Character, String> rbt = new RedBlackTree<Character, String>();
        String[] data = new String[8];
        /**
         * Klucze:
         */
        char[] treeKeys = new char[8];
        treeKeys[0] = (char) (65 + 11);
        treeKeys[1] = (char) (65 + 2);
        treeKeys[2] = (char) (65 + 14);
        treeKeys[3] = (char) (65 + 1);
        treeKeys[4] = (char) (65 + 7);
        treeKeys[5] = (char) (65 + 15);
        treeKeys[6] = (char) (65 + 5);
        treeKeys[7] = (char) (65 + 8);

        /**
         * wstawianie danych do drzewa
         */
        for (int i = 0; i < treeKeys.length; i++) {
            data[i] = t.wordsBuilder();
            rbt.setValue(treeKeys[i], data[i]);
        }

        /**
         * Sprawdzamy czy poszczególne węzły drzewa mają odpowiednie kolory:
         */
        assertEquals(rbt.getColor(treeKeys[0]), "czarny");
        assertEquals(rbt.getColor(treeKeys[1]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[2]), "czarny");
        assertEquals(rbt.getColor(treeKeys[3]), "czarny");
        assertEquals(rbt.getColor(treeKeys[4]), "czarny");
        assertEquals(rbt.getColor(treeKeys[5]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[6]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[7]), "czerwony");

        /**
         * Sprawdzamy czy klucze zwracają odpowiednie dane:
         */
        for (int i = 0; i < treeKeys.length; i++) {
            assertEquals(rbt.getValue(treeKeys[i]), data[i]);
            rbt.setValue(treeKeys[i], data[i]);
        }
    }

    /**
     * Metoda ta testuje zachowanie drzewa po dodaniu węzłów o kluczach typu
     * String
     */
    @Test
    public void insertStringAsKey() {
        RedBlackTree<String, String> rbt = new RedBlackTree<String, String>();
        String[] data = new String[8];
        /**
         * Klucze:
         */
        String[] treeKeys = new String[8];
        treeKeys[0] = t.wordsBuilder(10, (char) (65 + 11));
        treeKeys[1] = t.wordsBuilder(10, (char) (65 + 2));
        treeKeys[2] = t.wordsBuilder(10, (char) (65 + 14));
        treeKeys[3] = t.wordsBuilder(10, (char) (65 + 1));
        treeKeys[4] = t.wordsBuilder(10, (char) (65 + 7));
        treeKeys[5] = t.wordsBuilder(10, (char) (65 + 15));
        treeKeys[6] = t.wordsBuilder(10, (char) (65 + 5));
        treeKeys[7] = t.wordsBuilder(10, (char) (65 + 8));

        /**
         * wstawianie danych do drzewa
         */
        for (int i = 0; i < treeKeys.length; i++) {
            data[i] = t.wordsBuilder();
            rbt.setValue(treeKeys[i], data[i]);
        }

        /**
         * Sprawdzamy czy poszczególne węzły drzewa mają odpowiednie kolory:
         */
        assertEquals(rbt.getColor(treeKeys[0]), "czarny");
        assertEquals(rbt.getColor(treeKeys[1]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[2]), "czarny");
        assertEquals(rbt.getColor(treeKeys[3]), "czarny");
        assertEquals(rbt.getColor(treeKeys[4]), "czarny");
        assertEquals(rbt.getColor(treeKeys[5]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[6]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[7]), "czerwony");

        /**
         * Sprawdzamy czy klucze zwracają odpowiednie dane:
         */
        for (int i = 0; i < treeKeys.length; i++) {
            assertEquals(rbt.getValue(treeKeys[i]), data[i]);
            rbt.setValue(treeKeys[i], data[i]);
        }
    }

    /**
     * Metoda ta testuje zachowanie drzewa po dodaniu węzłów o kluczach typu
     * Double
     */
    @Test
    public void insertDoubleAsKey() {
        RedBlackTree<Double, String> rbt = new RedBlackTree<Double, String>();
        String[] data = new String[8];
        /**
         * Klucze:
         */
        double[] treeKeys = new double[8];
        treeKeys[0] = 11 + r.nextDouble();
        treeKeys[1] = 2 + r.nextDouble();
        treeKeys[2] = 14 + r.nextDouble();
        treeKeys[3] = 1 + r.nextDouble();
        treeKeys[4] = 7 + r.nextDouble();
        treeKeys[5] = 15 + r.nextDouble();
        treeKeys[6] = 5 + r.nextDouble();
        treeKeys[7] = 8 + r.nextDouble();

        /**
         * wstawianie danych do drzewa
         */
        for (int i = 0; i < treeKeys.length; i++) {
            data[i] = t.wordsBuilder();
            rbt.setValue(treeKeys[i], data[i]);
        }

        /**
         * Sprawdzamy czy poszczególne węzły drzewa mają odpowiednie kolory:
         */
        assertEquals(rbt.getColor(treeKeys[0]), "czarny");
        assertEquals(rbt.getColor(treeKeys[1]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[2]), "czarny");
        assertEquals(rbt.getColor(treeKeys[3]), "czarny");
        assertEquals(rbt.getColor(treeKeys[4]), "czarny");
        assertEquals(rbt.getColor(treeKeys[5]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[6]), "czerwony");
        assertEquals(rbt.getColor(treeKeys[7]), "czerwony");

        /**
         * Sprawdzamy czy klucze zwracają odpowiednie dane:
         */
        for (int i = 0; i < treeKeys.length; i++) {
            assertEquals(rbt.getValue(treeKeys[i]), data[i]);
            rbt.setValue(treeKeys[i], data[i]);
        }
    }

    /**
     * Metoda ta testuje złożoność czasową operacji włożenie do mapy wartości
     */
    @Test
    public void setValueTimeComplexity() throws IOException {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<Integer, String>();

        long start, stop;
        /**
         * Czasy wkładania do mapy poszczególnych elementów
         */
        long r16, r32, r64, r128, r256, r512, r1024, r2048;

        start = System.nanoTime();
        for (int i = 0; i < 16; i++) {
            rbt.setValue(r.nextInt(), "test");
        }
        stop = System.nanoTime();
        r16 = stop - start;

        start = System.nanoTime();
        for (int i = 0; i < 32; i++) {
            rbt.setValue(r.nextInt(), "test");
        }
        stop = System.nanoTime();
        r32 = stop - start;

        start = System.nanoTime();
        for (int i = 0; i < 64; i++) {
            rbt.setValue(r.nextInt(), "test");
        }
        stop = System.nanoTime();
        r64 = stop - start;

        start = System.nanoTime();
        for (int i = 0; i < 128; i++) {
            rbt.setValue(r.nextInt(), "test");
        }
        stop = System.nanoTime();
        r128 = stop - start;

        start = System.nanoTime();
        for (int i = 0; i < 256; i++) {
            rbt.setValue(r.nextInt(), "test");
        }
        stop = System.nanoTime();
        r256 = stop - start;

        start = System.nanoTime();
        for (int i = 0; i < 512; i++) {
            rbt.setValue(r.nextInt(), "test");
        }
        stop = System.nanoTime();
        r512 = stop - start;

        start = System.nanoTime();
        for (int i = 0; i < 1024; i++) {
            rbt.setValue(r.nextInt(), "test");
        }
        stop = System.nanoTime();
        r1024 = stop - start;

        start = System.nanoTime();
        for (int i = 0; i < 2048; i++) {
            rbt.setValue(r.nextInt(), "test");
        }
        stop = System.nanoTime();
        r2048 = stop - start;

        System.out.println("Czas włożenia 16 elementów do drzewa = " + r16 + " ns");
        System.out.println("Czas włożenia 32 elementów do drzewa = " + r32 + " ns");
        System.out.println("Czas włożenia 64 elementów do drzewa = " + r64 + " ns");
        System.out.println("Czas włożenia 128 elementów do drzewa = " + r128 + " ns");
        System.out.println("Czas włożenia 256 elementów do drzewa = " + r256 + " ns");
        System.out.println("Czas włożenia 512 elementów do drzewa = " + r512 + " ns");
        System.out.println("Czas włożenia 1024 elementów do drzewa = " + r1024 + " ns");
        System.out.println("Czas włożenia 2048 elementów do drzewa = " + r2048 + " ns");
    }
}
