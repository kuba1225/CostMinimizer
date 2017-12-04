package dynamicstructures;

/**
 *
 * @author Kuba
 */
public class Farm {

    private int id;
    private String nazwa;
    private int dzienna_produkcja;

    public Farm(int id, String nazwa, int dzienna_produkcja) {
        this.id = id;
        this.nazwa = nazwa;
        this.dzienna_produkcja = dzienna_produkcja;
    }

    public int getId() {
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getDzienna_produkcja() {
        return dzienna_produkcja;
    }
    
    
}
