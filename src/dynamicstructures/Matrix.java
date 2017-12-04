package dynamicstructures;

/**
 *
 * @author Kuba
 */
public class Matrix implements MatrixInterface {

    public Matrix(int rn, int cn) {
        this.rn = rn;
        this.cn = cn;
        matrix = new double[rn * cn];
    }

    private int cn = 0;
    private int rn = 0;

    private double[] matrix;

    @Override
    public void put(double obj, int row, int column) {
        if (column >= 0 && column < cn && row >= 0 && row < rn) {
            matrix[row * cn + column] = obj;
        }
    }

    @Override
    public void add(double obj, int row, int column) {
        if (column >= 0 && column < cn && row >= 0 && row < rn) {
            matrix[row * cn + column] += obj;
        }
    }

    @Override
    public double get(int row, int column) {
        if (column >= 0 && column < cn && row >= 0 && row < rn) {
            return matrix[row * cn + column];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public int getColumnNumber() {
        return cn;
    }

    @Override
    public int getRowNumber() {
        return rn;
    }

    /**
     * Wypełnia całą macierz podaną wartością
     *
     * @param obj wartość jaką chcemy wypełnić całą macierz
     */
    public void fillAllMatrix(double obj) {
        for (int i = 0; i < cn; i++) {
            for (int j = 0; j < rn; j++) {
                matrix[i * cn + j] = obj;
            }
        }
    }

    public void writeMatrix() {
        for (int i = 0; i < rn; i++) {
            System.out.print("[");
            for (int j = 0; j < cn; j++) {
                System.out.print(matrix[i * cn + j]);
                if (j < cn - 1) {
                    System.out.print("  ");
                }
            }
            System.out.print("]\n");
        }
    }

}
