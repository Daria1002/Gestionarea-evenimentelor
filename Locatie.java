/**
 * Clasa Locatia reprezinta o locatie care poate fi utilizata in contextul unui eveniment.
 * Aceasta contine informatii despre numele si adresa locatiei.
 */
public class Locatia {
    String nume; // Numele locatiei
    String adresa; // Adresa locatiei

    /**
     * Constructor implicit pentru clasa Locatia.
     * Creeaza o locatie fara a seta numele sau adresa acesteia.
     */
    public void Locatie() {}

    /**
     * Constructor parametrizat pentru clasa Locatia.
     * Permite initializarea locatiei cu un nume si o adresa specifica.
     *
     * @param n Numele locatiei.
     * @param a Adresa locatiei.
     */
    public Locatia(String n, String a) {
        nume = n;
        adresa = a;
    }

    /**
     * Returneaza numele locatiei.
     *
     * @return String ce reprezinta numele locatiei.
     */
    public String getNume() {
        return nume;
    }

    /**
     * Seteaza numele locatiei.
     *
     * @param nume Numele locatiei.
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Returneaza adresa locatiei.
     *
     * @return String ce reprezinta adresa locatiei.
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     * Seteaza adresa locatiei.
     *
     * @param adresa Adresa locatiei.
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
