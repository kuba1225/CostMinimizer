package dynamicstructures;

/**
 *
 * @author Kuba
 */
public interface QueueInterface {

    public boolean empty();

    public int front();

    public void push(int v);

    public int pop();

    public int size();
}
