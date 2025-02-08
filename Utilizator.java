public class Utilizator {
    private int id; // ID-ul utilizatorului din baza de date
    private String nume;
    private String email;
    private String parola;
    private String rol;

    // Constructor pentru utilizatorul autentificat (fără parolă)
    public Utilizator(int id, String nume, String email) {
        this.id = id;
        this.nume = nume;
        this.email = email;
    }

    // Constructor complet (folosit doar intern, dacă e nevoie)
    public Utilizator(int id, String nume, String email, String parola) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.parola = parola;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getEmail() {
        return email;
    }

    public String getParola() {
        return parola;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getRol() {
        return rol;
    }
}
