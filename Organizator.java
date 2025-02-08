/**
 * Clasa Organizator reprezinta persoana responsabila pentru organizarea unui eveniment.
 * Aceasta contine informatii despre ID-ul, numele si email-ul organizatorului.
 */
public class Organizator {
    private int id; // ID-ul organizatorului
    private String nume; // Numele organizatorului
    private String email; // Email-ul organizatorului

    /**
     * Constructor implicit pentru clasa Organizator.
     * Creeaza un obiect Organizator fara a seta id-ul, numele sau email-ul acestuia.
     */
    public Organizator(String organizatorNume, String organizatorEmail) {}

    /**
     * Constructor parametrizat pentru clasa Organizator.
     * Permite initializarea organizatorului cu un ID, un nume si un email specific.
     *
     * @param id ID-ul organizatorului.
     * @param nume Numele organizatorului.
     * @param email Email-ul organizatorului.
     */
    public Organizator(int id, String nume, String email) {
        this.id = id;
        this.nume = nume;
        this.email = email;
    }

    /**
     * Returneaza ID-ul organizatorului.
     *
     * @return ID-ul organizatorului.
     */
    public int getId() {
        return id;
    }

    /**
     * Seteaza ID-ul organizatorului.
     *
     * @param id ID-ul organizatorului.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returneaza numele organizatorului.
     *
     * @return String ce reprezinta numele organizatorului.
     */
    public String getNume() {
        return nume;
    }

    /**
     * Seteaza numele organizatorului.
     *
     * @param nume Numele organizatorului.
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Returneaza email-ul organizatorului.
     *
     * @return String ce reprezinta email-ul organizatorului.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Seteaza email-ul organizatorului.
     *
     * @param email Email-ul organizatorului.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
