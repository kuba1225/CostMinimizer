package dynamicstructures;

/**
 *
 * @author Kuba
 */
public class Farm {

    private int id;
    private String name;
    private int dailyProductions;

    public Farm(int id, String nazwa, int dzienna_produkcja) {
        this.id = id;
        this.name = nazwa;
        this.dailyProductions = dzienna_produkcja;
    }

    public int getId() {
        return id;
    }

    public String getNazwa() {
        return name;
    }

    public int getDzienna_produkcja() {
        return dailyProductions;
    }
}
