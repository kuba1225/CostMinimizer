
import dynamicstructures.Queue;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kuba
 */
public class QueueJUnit {

    public QueueJUnit() {
    }

    @Test
    public void pushMethodTest() {
        Queue q = new Queue();
        q.push(0);
        q.push(10);
        q.push(232);
        q.push(2);

        assertEquals(q.pop(), 0);
        assertEquals(q.pop(), 10);
        assertEquals(q.pop(), 232);
        assertEquals(q.pop(), 2);
    }

    @Test
    public void popMethodTest() {
        Queue q = new Queue();
        q.push(0);
        q.push(10);
        q.push(232);
        q.push(2);

        assertEquals(q.pop(), 0);
        assertEquals(q.pop(), 10);
        assertEquals(q.pop(), 232);
        assertEquals(q.pop(), 2);
        try {
            q.pop();
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "Empty queue");
        }

    }

    @Test
    public void sizeMethodTest() {
        Queue q = new Queue();
        q.push(0);
        q.push(10);
        q.push(232);
        q.push(2);

        assertEquals(q.size(), 4);
    }

    @Test
    public void frontMethodTest() {
        Queue q = new Queue();
        q.push(0);
        q.push(10);
        q.push(232);
        q.push(2);

        assertEquals(q.front(), 0);

    }

    @Test
    public void emptyMethodTest() {
        Queue q = new Queue();

        assertEquals(q.empty(), true);

        q.push(23);
        q.push(345);
        q.push(523);
        q.push(12);

        assertEquals(q.empty(), false);

        assertEquals(q.pop(), 23);
        assertEquals(q.pop(), 345);
        assertEquals(q.pop(), 523);
        assertEquals(q.pop(), 12);

        assertEquals(q.empty(), true);

    }
}
