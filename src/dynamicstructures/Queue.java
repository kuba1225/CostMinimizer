package dynamicstructures;

import java.util.NoSuchElementException;

/**
 *
 * @author Kuba
 */
public class Queue implements QueueInterface {

    private class QElement {

        int data;
        QElement next;
    };

    private QElement head;
    private QElement tail;
    private int elementsNumber;

    public Queue() {
        head = null;
        tail = null;
        elementsNumber = 0;
    }

    /**
     * Sprawdza czy kolejka jest pusta
     *
     * @return zwraca true jeżeli kolejka jest pusta
     */
    @Override
    public boolean empty() {
        return (head == null);
    }

    /**
     * Zwraca wartość pierwszego elementu w kolsjce
     *
     * @return wartość pierwszego elementu w kolejce
     */
    @Override
    public int front() {
        if (head != null) {
            return head.data;
        } else {
            throw new NoSuchElementException("Empty queue");
        }
    }

    /**
     * Dodaje element do kolejki
     *
     * @param v element dodawany do kolejki
     */
    @Override
    public void push(int v) {
        QElement p = new QElement();
        p.next = null;
        p.data = v;
        if (tail != null) {
            tail.next = p;
        } else {
            head = p;
        }
        tail = p;
        elementsNumber++;
    }

    /**
     * Usuwa z kolejki pierwszy element
     *
     * @return wartość przechowywaną w usuwanym elemencie
     */
    @Override
    public int pop() {
        if (head != null) {
            QElement p = head;
            head = head.next;
            if (head == null) {
                tail = null;
            }
            elementsNumber--;
            return p.data;
        } else {
            throw new NoSuchElementException("Empty queue");
        }
    }

    /**
     * Zwraca liczbę elementów w kolejce
     *
     * @return liczba elementó w kolejce
     */
    @Override
    public int size() {
        return elementsNumber;
    }
}
