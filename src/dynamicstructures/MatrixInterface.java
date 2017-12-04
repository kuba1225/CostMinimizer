package dynamicstructures;

/**
 *
 * @author Kuba
 */
public interface MatrixInterface {

    public void put(double obj, int row, int column);

    public void add(double obj, int row, int column);

    public double get(int row, int column);

    public int getColumnNumber();

    public int getRowNumber();
}
