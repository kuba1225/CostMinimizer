package algorithmstest;

import algorithms.EdmondsKarpAlgorithm;
import costminimizer.CostMinimizer;
import costminimizer.Tools;
import iocommunication.Parser;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kuba
 */
public class EdmondsKarpAlgorithmJUnit {

    public EdmondsKarpAlgorithmJUnit() {
    }

    @Test
    public void loadDataMethodTest() throws IOException {
        CostMinimizer c = new CostMinimizer();
        Parser r = new Parser("test.txt");

        r.readFile();

        EdmondsKarpAlgorithm e = new EdmondsKarpAlgorithm();
        e.loadData();
    }

    @Test
    public void edmondsKarpMethodTest() throws IOException {
        CostMinimizer c = new CostMinimizer();
        Parser r = new Parser("test.txt");
        Tools t = new Tools();

        r.readFile();

        EdmondsKarpAlgorithm e = new EdmondsKarpAlgorithm();
        e.edmondsKarp();

        assertEquals(t.returnStringResidualGraph(), "3 -> 0  przepływ:500  koszt:0\n"
                + "2 -> 0  przepływ:330  koszt:0\n"
                + "0 -> 1  przepływ:150  koszt:0\n"
                + "1 -> 0  przepływ:150  koszt:0\n"
                + "1 -> 7  przepływ:100  koszt:4\n"
                + "6 -> 1  przepływ:150  koszt:-2\n"
                + "1 -> 6  przepływ:100  koszt:2\n"
                + "1 -> 5  przepływ:200  koszt:3\n"
                + "1 -> 4  przepływ:200  koszt:3\n"
                + "2 -> 7  przepływ:300  koszt:2\n"
                + "6 -> 2  przepływ:150  koszt:-5\n"
                + "5 -> 2  przepływ:30  koszt:-4\n"
                + "2 -> 5  przepływ:170  koszt:4\n"
                + "4 -> 2  przepływ:150  koszt:-3\n"
                + "2 -> 4  przepływ:150  koszt:3\n"
                + "7 -> 3  przepływ:200  koszt:-5\n"
                + "6 -> 3  przepływ:100  koszt:-6\n"
                + "5 -> 3  przepływ:200  koszt:-4\n"
                + "3 -> 5  przepływ:100  koszt:4\n"
                + "3 -> 4  przepływ:300  koszt:4\n"
                + "8 -> 4  przepływ:150  koszt:0\n"
                + "8 -> 5  przepływ:230  koszt:0\n"
                + "8 -> 6  przepływ:400  koszt:0\n"
                + "8 -> 7  przepływ:200  koszt:0\n");
    }
}
