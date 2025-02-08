/**
 * Clasa Bilet reprezinta un tichet care este emis pentru un anumit eveniment si este detinut de un participant.
 * Biletul contine informatii despre evenimentul la care se refera, participantul care il detine si un ID unic
 * care il identifica.
 */
public class Bilet {
    Eveniment eveniment; // Evenimentul pentru care este emis biletul
    Participant participant; // Participantul care a achizitionat biletul
    String id_bilet; // ID-ul unic al biletului

    /**
     * Constructorul gol al clasei Bilet. Acesta nu seteaza nimic, dar este util pentru
     * cazurile in care vrem sa cream un bilet fara sa-i oferim detalii imediat.
     */
    public Bilet() {}

    /**
     * Constructorul complet al clasei Bilet. Acesta permite crearea unui bilet
     * asociat unui eveniment, unui participant si avand un ID unic.
     *
     * @param e Evenimentul la care se refera biletul.
     * @param p Participantul care detine biletul.
     * @param id ID-ul unic al biletului.
     */
    public Bilet(Eveniment e, Participant p, String id) {
        eveniment = e;
        participant = p;
        id_bilet = id;
    }

    /**
     * Returneaza evenimentul asociat acestui bilet.
     *
     * @return Evenimentul pentru care a fost emis biletul.
     */
    public Eveniment getEveniment() {
        return eveniment;
    }

    /**
     * Returneaza participantul care detine acest bilet.
     *
     * @return Participantul asociat biletului.
     */
    public Participant getParticipant() {
        return participant;
    }

    /**
     * Returneaza ID-ul unic al acestui bilet.
     *
     * @return Un sir de caractere (String) care reprezinta ID-ul biletului.
     */
    public String getId() {
        return id_bilet;
    }

    /**
     * Schimba evenimentul asociat acestui bilet.
     *
     * @param e Noul eveniment pentru care este valabil biletul.
     */
    public void setEveniment(Eveniment e) {
        eveniment = e;
    }

    /**
     * Schimba participantul asociat acestui bilet.
     *
     * @param p Noul participant care detine biletul.
     */
    public void setParticipant(Participant p) {
        participant = p;
    }

    /**
     * Schimba ID-ul unic al acestui bilet.
     *
     * @param id Noul ID pentru acest bilet.
     */
    public void setId(String id) {
        id_bilet = id;
    }
}
