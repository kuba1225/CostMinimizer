package dynamicstructures;

/**
 *
 * @author Kuba
 */
public class RedBlackTree<K extends Comparable<K>, V> implements MapInterface<K, V> {

    private static final boolean BLACK = false;
    private static final boolean RED = true;

    private RBTNode root;//korzeń drzewa
    private RBTNode guard;//strażnik drzewa - pełni rolę liści

    public RedBlackTree() {

        guard = new RBTNode();
        guard.color = BLACK;
        guard.up = guard;
        guard.left = guard;
        guard.right = guard;
        root = guard;
    }

    /**
     * Klasa reprezentująca pojedyńczy węzeł drzewa
     */
    private class RBTNode {

        RBTNode left;
        RBTNode right;
        RBTNode up;
        boolean color;
        K key;
        V data;

        /**
         * Metoda ta ustawia kolor danego węzła na czarny
         */
        private void setBlackColor() {
            this.color = BLACK;
        }

        /**
         * Metoda ta ustawia kolor danego węzła na czerwony
         */
        private void setRedColor() {
            this.color = RED;
        }

        /**
         * Metoda ta sprawdza czy dany węzeł ma kolor czarny
         *
         * @return zwraca "true" jeżeli węzeł jest czarny
         */
        private boolean hasBlackColor() {
            return (this.color == BLACK);
        }

        /**
         * Metoda ta sprawdza czy dany węzeł ma kolor czerwony
         *
         * @return zwraca "true" jeżeli węzeł jest czerwony
         */
        private boolean hasRedColor() {
            return (this.color == RED);
        }
    }

    /**
     * Metoda ta służy do wstawiania nowych elementów do drzewa
     * czerwono-czarnego.
     *
     * @param key klucz nowego węzła
     * @param obj nowe dane do umieszczenia we wstawianym węźle
     */
    @Override
    public void setValue(K key, V obj) {
        RBTNode tmp;
        if ((tmp = findNodeWith(key)) != null) {//sprawdzamy tu czy w drzewie nie ma już węzła o podanym kluczu
            tmp.data = obj;
            return;
        }

        RBTNode t = new RBTNode();
        t.left = guard;
        t.right = guard;
        t.up = root;//na początku nowy węzeł ustawiamy pod korzeniem
        t.key = key;
        t.data = obj;

        if (t.up == guard) {
            root = t;//jeżeli drzewo jest puste to wstawiany węzeł staje się korzeniem
        } else {
            compareKeys(t, key, obj);
        }

        t.setRedColor();//ustawiamy kolor nowego węzła na czerwony

        repaintRBTreeAfterPush(t);
    }

    /**
     * Metoda ta służy do ustawiania węzłów w taki sposób aby jego klucze jego
     * węzłów spełniały warunek binarnego drzewa przeszukiwań (BST)
     *
     * @param t wstawiany węzeł
     * @param key klucz wstawianego węzła
     */
    private void compareKeys(RBTNode t, K key, V obj) {
        if (key.compareTo(t.up.key) < 0) {//gdy klucz wstawianego węzła jest mniejszy od klucza jego ojca
            if (t.up.left == guard) {
                t.up.left = t;
            } else {
                t.up = t.up.left;
                compareKeys(t, key, obj);
            }
        } else {
            if (t.up.right == guard) {
                t.up.right = t;
            } else {
                t.up = t.up.right;
                compareKeys(t, key, obj);
            }
        }
    }

    /**
     * Metoda ta służy do ustawiania nowego węzła w drzewie tak, aby po jego
     * wstawieniu drzewo spełniało warunek drzewa czerwono-czarnego.
     * Rozpatrujemy tu trzy podstawowe przypadki umiejscowienia węzłów jakie
     * mogą wystąpić podczas wstawiania do drzewa nowego węzła oraz ich
     * lustrzane wersje.
     *
     * @param t węzeł wstawiany do drzewa
     */
    private void repaintRBTreeAfterPush(RBTNode t) {
        RBTNode uncle;
        if (t != root && t.up.hasRedColor()) {
            if (t.up == t.up.up.left) {
                uncle = t.up.up.right;//"wujek" wstawianego węzła
                if (uncle.hasRedColor()) {//PIERWSZY PRZYPADEK
                    t.up.setBlackColor();
                    uncle.setBlackColor();
                    t.up.up.setRedColor();
                    t = t.up.up;//tymcasowy węzeł staje się "dziadkiem"
                    repaintRBTreeAfterPush(t);
                } else {
                    if (t == t.up.right) {//DRUGI PRZYPADEK
                        t = t.up;
                        rotacjaLewo(t);
                    }
                    t.up.setBlackColor();//TRZECI PRZYPADEK
                    t.up.up.setRedColor();
                    rotacjaPrawo(t.up.up);
                    repaintRBTreeAfterPush(t);
                }
            } else {//Przypadki lustrzane
                uncle = t.up.up.left;
                if (uncle.hasRedColor()) {//PIERWSZY PRZYPADEK
                    t.up.setBlackColor();
                    uncle.setBlackColor();
                    t.up.up.setRedColor();
                    t = t.up.up;//tymcasowy węzeł staje się "dziadkiem"
                    repaintRBTreeAfterPush(t);
                } else {
                    if (t == t.up.left) {//DRUGI PRZYPADEK
                        t = t.up;
                        rotacjaPrawo(t);
                    }
                    t.up.setBlackColor();//TRZECI PRZYPADEK
                    t.up.up.setRedColor();
                    rotacjaLewo(t.up.up);
                    repaintRBTreeAfterPush(t);
                }
            }
        }
        root.setBlackColor();//ustawiamy kolor korzenia na czarny (w razie gdyby w wyniku powyższych operacji stał się czerwony)

    }

    /**
     * Metoda ta służy do wykonywania rotacji węzłów drzewa w prawo względem
     * podanego węzła. Metoda lustrzana do metody rotacjaLewo(RBTNode node).
     *
     * @param node węzeł względem którego ma zostać wykonana rotacja w prawo
     */
    private void rotacjaPrawo(RBTNode node) {
        RBTNode tmp1, tmp2;

        tmp1 = node.left;
        if (tmp1 != guard) {
            tmp2 = node.up;
            node.left = tmp1.right;

            if (node.left != guard) {
                node.left.up = node;
            }
            tmp1.right = node;
            tmp1.up = tmp2;
            node.up = tmp1;

            if (tmp2 != guard) {
                if (tmp2.left == node) {
                    tmp2.left = tmp1;
                } else {
                    tmp2.right = tmp1;
                }
            } else {
                root = tmp1;
            }
        }
    }

    /**
     * Metoda ta służy do wykonywania rotacji węzłów drzewa w lewo względem
     * podanego węzła. Metoda lustrzana do metody rotacjaPrawo(RBTNode node).
     *
     * @param node węzeł względem którego ma zostać wykonana rotacja w lewo
     */
    private void rotacjaLewo(RBTNode node) {
        RBTNode tmp1, tmp2;

        tmp1 = node.right;
        if (tmp1 != guard) {
            tmp2 = node.up;
            node.right = tmp1.left;
            if (node.right != guard) {
                node.right.up = node;
            }
            tmp1.left = node;
            tmp1.up = tmp2;
            node.up = tmp1;

            if (tmp2 != guard) {
                if (tmp2.left == node) {
                    tmp2.left = tmp1;
                } else {
                    tmp2.right = tmp1;
                }
            } else {
                root = tmp1;
            }
        }
    }

    /**
     * Metoda ta służy do pobierania danych z węzłów drzewa czerwono-czarnego na
     * podstawie podanego klucza
     *
     * @param key klucz do danego węzła
     * @return dane znajdujące się wewnątrz wstawianego węzła
     */
    @Override
    public V getValue(K key) {
        RBTNode t = findNodeWith(key);
        if (t == null) {
            return null;
        } else {
            return t.data;
        }

    }

    /**
     * Metoda ta przeszukuje drzewo i zwraca węzeł o podanym kluczu
     *
     * @param key klucz poszukiwanego węzła
     * @return węzeł o podanym kluczu
     */
    private RBTNode findNodeWith(K key) {
        RBTNode tmp = root;
        while (tmp != guard) {
            if (key.compareTo(tmp.key) == 0) {
                return tmp;
            } else if (key.compareTo(tmp.key) < 0) {
                tmp = tmp.left;
            } else {
                tmp = tmp.right;
            }
        }
        return null;
    }

    /**
     * Metoda zwracająca liczbę węzłów w całym drzewie (od korzenia do jego
     * liści)
     *
     * @return liczba węzłów w drzewie czerwono-czarnym
     */
    public int getNumberOfAllNodes() {
        return getNumberOfNodes(root);
    }

    /**
     * Metoda zwracająca liczbę węzłów w drzewie od podanego węzła do jego liści
     *
     * @param r węzeł od którego należy rozpocząc liczenie węzłów
     * @return liczba węzłów od podanego węzła do jego liści
     */
    private int getNumberOfNodes(RBTNode r) {

        int number = 1;
        if (r == guard) {
            return 0;
        } else {
            number += getNumberOfNodes(r.left);
            number += getNumberOfNodes(r.right);
            return number;
        }
    }

    /**
     * Metoda ta zwraca kolor węzła o podanym kluczu
     *
     * @param key klucz do węzła
     * @return kolor węzła o podanym kluczu
     */
    public String getColor(K key) {
        RBTNode t = findNodeWith(key);
        if (t.color == RED) {
            return "czerwony";
        } else {
            return "czarny";
        }
    }
}
