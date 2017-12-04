package dynamicstructures;

/**
 *
 * @author Kuba
 */
public class Shop {

    private int id;
    private String name;
    private int dailyDemands;

    public Shop(int id, String nazwa, int dzienne_zapotrzebowanie) {
        this.id = id;
        this.name = nazwa;
        this.dailyDemands = dzienne_zapotrzebowanie;
    }

    public int getId() {
        return id;
    }

    public String getNazwa() {
        return name;
    }

    public int getDzienne_zapotrzebowanie() {
        return dailyDemands;
    }
}
