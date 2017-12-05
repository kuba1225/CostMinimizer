
import dynamicstructures.Matrix;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kuba
 */
public class MatrixJUnit {

    public MatrixJUnit() {
    }

    @Test
    public void putMethodTest() {
        Matrix m = new Matrix(2, 3);
        m.put(23, 0, 1);
        m.put(123, 1, 1);

        assertEquals(23, (int) m.get(0, 1));
        assertEquals(123, (int) m.get(1, 1));
    }

    @Test
    public void addMethodTest() {
        Matrix m = new Matrix(2, 3);
        m.put(23, 0, 1);
        m.put(123, 1, 1);

        m.add(23, 0, 1);
        m.add(123, 1, 1);

        assertEquals(46, (int) m.get(0, 1));
        assertEquals(246, (int) m.get(1, 1));
    }

    @Test
    public void getMethodTest() {
        Matrix m = new Matrix(2, 3);
        m.put(23, 0, 1);
        m.put(123, 1, 1);
        m.put(4233, 0, 2);
        m.put(223, 1, 2);

        assertEquals(23, (int) m.get(0, 1));
        assertEquals(123, (int) m.get(1, 1));
        assertEquals(4233, (int) m.get(0, 2));
        assertEquals(223, (int) m.get(1, 2));
    }

    @Test
    public void getColumnNumberMethodTest() {
        Matrix m = new Matrix(2, 3);

        assertEquals(3, m.getColumnNumber());
    }

    @Test
    public void getRowNumberMethodTest() {
        Matrix m = new Matrix(2, 3);

        assertEquals(2, m.getRowNumber());
    }

    @Test
    public void fillAllMatrixMethodTest() {
        Matrix m = new Matrix(2, 3);

        m.fillAllMatrix(12);

        for (int i = 0; i < m.getColumnNumber(); i++) {
            for (int j = 0; j < m.getRowNumber(); j++) {
                assertEquals(12, (int)m.get(i,j));
            }
        }
    }
}
