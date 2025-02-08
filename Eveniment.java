import java.util.ArrayList;
import java.util.List;

/**
 * Clasa Eveniment reprezintă un eveniment cu detalii asociate precum numele,
 * data, ora, locația, organizatorul, participanții și biletele.
 */
public class Eveniment {
    private int id;
    private String nume;
    private String data;
    private String ora;
    private String locatie;
    private Organizator organizator;
    private List<Participant> participanti;
    private List<Bilet> bilete;
    private String status;

    /**
     * Constructor implicit pentru clasa Eveniment.
     * Initializează listele de participanți și bilete ca liste goale.
     */
    public Eveniment() {
        this.participanti = new ArrayList<>();
        this.bilete = new ArrayList<>();
        this.status = "Activ"; // Default status
    }

    /**
     * Constructor parametrizat pentru clasa Eveniment.
     * Creează un obiect Eveniment cu detaliile specificate.
     *
     * @param n   Numele evenimentului.
     * @param d   Data evenimentului.
     * @param o   Ora evenimentului.
     * @param l   Locația evenimentului.
     * @param org Organizatorul evenimentului.
     * @param p   Lista participanților (poate fi null, caz în care se inițializează ca listă goală).
     * @param b   Lista biletelor (poate fi null, caz în care se inițializează ca listă goală).
     */
    public Eveniment(String n, String d, String o, String l, Organizator org, List<Participant> p, List<Bilet> b) {
        this.nume = n;
        this.data = d;
        this.ora = o;
        this.locatie = l;
        this.organizator = org;
        this.participanti = p != null ? p : new ArrayList<>();
        this.bilete = b != null ? b : new ArrayList<>();
        this.status = "Activ"; // Default status
    }

    /**
     * Constructor complet pentru clasa Eveniment.
     *
     * @param id            ID-ul evenimentului.
     * @param n             Numele evenimentului.
     * @param d             Data evenimentului.
     * @param o             Ora evenimentului.
     * @param l             Locația evenimentului.
     * @param org           Organizatorul evenimentului.
     * @param p             Lista participanților la eveniment (poate fi null).
     * @param b             Lista biletelor emise pentru eveniment (poate fi null).
     */
    public Eveniment(int id, String n, String d, String o, String l, Organizator org, List<Participant> p, List<Bilet> b) {
        this.id = id;
        this.nume = n;
        this.data = d;
        this.ora = o;
        this.locatie = l;
        this.organizator = org;
        this.participanti = p != null ? p : new ArrayList<>();
        this.bilete = b != null ? b : new ArrayList<>();
        this.status = "Activ"; // Default status
    }

    // Getteri și setteri pentru toate proprietățile

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public Organizator getOrganizator() {
        return organizator;
    }

    public void setOrganizator(Organizator organizator) {
        this.organizator = organizator;
    }

    public List<Participant> getParticipanti() {
        return participanti;
    }

    public void setParticipanti(List<Participant> participanti) {
        this.participanti = participanti;
    }

    public List<Bilet> getBilete() {
        return bilete;
    }

    public void setBilete(List<Bilet> bilete) {
        this.bilete = bilete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Metode utile

    /**
     * Adaugă un participant la eveniment.
     *
     * @param participant Participantul care trebuie adăugat.
     */
    public void adaugaParticipant(Participant participant) {
        if (participant != null) {
            participanti.add(participant);
        }
    }

    /**
     * Schimbă statusul evenimentului.
     *
     * @param status Noua valoare pentru status (e.g., "Activ", "Anulat").
     */
    public void schimbaStatus(String status) {
        this.status = status;
    }

    /**
     * Returnează o descriere a evenimentului.
     *
     * @return Descrierea completă a evenimentului.
     */
    @Override
    public String toString() {
        return "Eveniment{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", data='" + data + '\'' +
                ", ora='" + ora + '\'' +
                ", locatie='" + locatie + '\'' +
                ", organizator=" + (organizator != null ? organizator.getNume() : "Necunoscut") +
                ", status='" + status + '\'' +
                '}';
    }
}
