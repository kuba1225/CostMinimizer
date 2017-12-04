package dynamicstructures;

/**
 *
 * @author Kuba
 */
public class Shop {

    private int id;
    private String nazwa;
    private int dzienne_zapotrzebowanie;

    public Shop(int id, String nazwa, int dzienne_zapotrzebowanie) {
        this.id = id;
        this.nazwa = nazwa;
        this.dzienne_zapotrzebowanie = dzienne_zapotrzebowanie;
    }

    public int getId() {
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getDzienne_zapotrzebowanie() {
        return dzienne_zapotrzebowanie;
    }
    
}
