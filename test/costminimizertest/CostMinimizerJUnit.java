package costminimizertest;

import costminimizer.CostMinimizer;
import costminimizer.Tools;
import iocommunication.Writer;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kuba
 */
public class CostMinimizerJUnit {

    public CostMinimizerJUnit() {
    }

    @Test
    public void CostMinimizerTest() throws IOException {
        CostMinimizer c = new CostMinimizer();
        Writer w = new Writer();
        c.minimizeCost("test.txt");
        w.writeResultsToStdOut();

    }

    @Test
    public void returnSumOfCostMethodTest() throws IOException {
        CostMinimizer c = new CostMinimizer();
        Writer w = new Writer();
        c.minimizeCost("test.txt");
        Tools t = new Tools();

        assertEquals(t.returnSumOfCosts(), 3140);
    }
}
