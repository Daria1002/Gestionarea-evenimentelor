import com.example.db.DBconnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


/**
 * Clasa Main reprezinta punctul de intrare in aplicatie.
 * Aceasta gestioneaza meniurile principale si interactiunile utilizatorului,
 * inclusiv login, inregistrare, gestionarea evenimentelor si participantilor.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionareUtilizatori gestionareUtilizatori = new GestionareUtilizatori();
        CreareEveniment creareEveniment = new CreareEveniment();
        GestionareParticipanti gestionareParticipanti = new GestionareParticipanti();
        Utilizator utilizatorCurent = null;

        int optiune;
        do {
            System.out.println("\nMeniu Principal:");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("0. Ieșire");

            System.out.print("Alege o optiune: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Optiune invalida");
                scanner.next();
            }
            optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1: // Login
                    System.out.print("Introduceți email-ul: ");
                    String emailLogin = scanner.nextLine();
                    System.out.print("Introduceți parola: ");
                    String parolaLogin = scanner.nextLine();

                    utilizatorCurent = gestionareUtilizatori.autentificaUtilizator(emailLogin, parolaLogin); // Verifică în baza de date
                    if (utilizatorCurent != null) {
                        System.out.println("Bun venit, " + utilizatorCurent.getNume() + "!");
                        meniuEvenimente(scanner, utilizatorCurent, creareEveniment, gestionareParticipanti);
                    } else {
                        System.out.println("Autentificare eșuată! Verificați email-ul sau parola.");
                    }
                    break;

                case 2:
                    System.out.print("Introduceți numele: ");
                    String nume = scanner.nextLine();
                    System.out.print("Introduceți email-ul: ");
                    String email = scanner.nextLine();
                    System.out.print("Introduceți parola: ");
                    String parola = scanner.nextLine();

                    if (Validare.esteNumeValid(nume) && Validare.esteEmailValid(email) && !parola.isEmpty()) {
                        if (gestionareUtilizatori.inregistreazaUtilizator(nume, email, parola)) {
                            System.out.println("Utilizator adăugat cu succes.");
                        } else {
                            System.out.println("Email-ul există deja în baza de date.");
                        }
                    } else {
                        System.out.println("Datele introduse sunt invalide.");
                    }
                    break;


                case 0:
                    System.out.println("Iesire din aplicatie.");
                    break;

                default:
                    System.out.println("Optiune invalida");
            }


        } while (optiune != 0);

        scanner.close();
    }


    /**
     * Gestioneaza meniul pentru evenimente dupa autentificarea unui utilizator.
     *
     * @param scanner Scanner pentru input-ul utilizatorului.
     * @param utilizatorCurent Utilizatorul autentificat.
     * @param creareEveniment Obiect pentru gestionarea evenimentelor.
     * @param gestionareParticipanti Obiect pentru gestionarea participantilor.
     */
    public static void meniuEvenimente(Scanner scanner, Utilizator utilizatorCurent, CreareEveniment creareEveniment, GestionareParticipanti gestionareParticipanti) {
        int optiune;
        do {
            System.out.println("\nMeniu Evenimente:");
            System.out.println("1. Adauga eveniment");
            System.out.println("2. Vizualizeaza evenimente");
            System.out.println("3. Cauta eveniment");
            System.out.println("4. Inregistreaza participant");
            System.out.println("5. Vizualizeaza participanti");
            System.out.println("6. Anulează eveniment");

            System.out.println("0. Logout");

            System.out.print("Alege o optiune: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Optiune invalida");
                scanner.next();
            }
            optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    adaugaEveniment(scanner, utilizatorCurent, creareEveniment);
                    break;

                case 2:
                    vizualizeazaEvenimente(creareEveniment);
                    break;

                case 3:
                    cautaEveniment(scanner, creareEveniment);
                    break;

                case 4:
                    inregistreazaParticipant(scanner, creareEveniment, gestionareParticipanti);
                    break;

                case 5:
                    vizualizeazaParticipanti(scanner, creareEveniment);
                    break;

                case 6:
                    System.out.print("Introduceți ID-ul evenimentului pe care doriți să-l anulați: ");
                    int evenimentId = scanner.nextInt();
                    scanner.nextLine();
                    if (creareEveniment.anulareEveniment(evenimentId)) {
                        System.out.println("Evenimentul a fost anulat cu succes.");
                    } else {
                        System.out.println("Anularea evenimentului a eșuat.");
                    }
                    break;

                case 0:
                    System.out.println("Delogare");
                    break;

                default:
                    System.out.println("Optiune invalida");
            }
        } while (optiune != 0);
    }

    /**
     * Permite utilizatorului sa adauge un eveniment nou.
     *
     */
    public static void adaugaEveniment(Scanner scanner, Utilizator utilizatorCurent, CreareEveniment creareEveniment) {
        // Solicită utilizatorului detalii despre eveniment
        System.out.print("Introduceți numele evenimentului: ");
        String nume = scanner.nextLine();

        System.out.print("Introduceți data evenimentului (yyyy-MM-dd): ");
        String data = scanner.nextLine();

        System.out.print("Introduceți ora evenimentului (HH:mm): ");
        String ora = scanner.nextLine();

        System.out.print("Introduceți locația evenimentului: ");
        String locatie = scanner.nextLine();

        // Crează un obiect Organizator folosind utilizatorul curent
        Organizator organizator = new Organizator(utilizatorCurent.getNume(), utilizatorCurent.getEmail());

        // Crează un obiect Eveniment cu detaliile introduse
        Eveniment eveniment = new Eveniment(nume, data, ora, locatie, organizator, null, null);

        // Apelează metoda adaugaEveniment din clasa CreareEveniment
        boolean rezultat = creareEveniment.adaugaEveniment(eveniment);

        // Verifică rezultatul operațiunii și afișează un mesaj corespunzător
        if (rezultat) {
            System.out.println("Evenimentul a fost adăugat cu succes!");
        } else {
            System.out.println("Eroare la adăugarea evenimentului.");
        }
    }




    /**
     * Afișează toate evenimentele disponibile.
     *
     * @param creareEveniment Obiect pentru gestionarea evenimentelor.
     */
    public static void vizualizeazaEvenimente(CreareEveniment creareEveniment) {
        System.out.println("\nEvenimente disponibile:");
        List<Eveniment> evenimente = creareEveniment.getAllEvenimenteFromDatabase();
        if (evenimente.isEmpty()) {
            System.out.println("Nu există evenimente disponibile.");
        } else {
            for (Eveniment ev : evenimente) {
                System.out.println(ev.getNume() + " - " + ev.getData() + " " + ev.getOra() + " la " + ev.getLocatie());
            }
        }
    }


    /**
     * Permite cautarea unui eveniment dupa numele acestuia.
     *
     * @param scanner Scanner pentru input-ul utilizatorului.
     * @param creareEveniment Obiect pentru gestionarea evenimentelor.
     */
    public static void cautaEveniment(Scanner scanner, CreareEveniment creareEveniment) {
        System.out.print("Introduceti numele evenimentului: ");
        String numeEveniment = scanner.nextLine();
        Eveniment ev = creareEveniment.getEveniment(numeEveniment);
        if (ev != null) {
            System.out.println("Eveniment gasit: " + ev.getNume() + " - " + ev.getData() + " la " + ev.getLocatie());
        } else {
            System.out.println("Evenimentul nu a fost gasit");
        }
    }

    /**
     * Permite inregistrarea unui participant la un eveniment.
     *
     * @param scanner Scanner pentru input-ul utilizatorului.
     * @param creareEveniment Obiect pentru gestionarea evenimentelor.
     * @param gestionareParticipanti Obiect pentru gestionarea participantilor.
     */
    public static void inregistreazaParticipant(Scanner scanner, CreareEveniment creareEveniment, GestionareParticipanti gestionareParticipanti) {
        System.out.print("Introduceti numele evenimentului: ");
        String numeEveniment = scanner.nextLine();
        Eveniment ev = creareEveniment.getEveniment(numeEveniment);

        if (ev != null) {
            System.out.print("Nume participant: ");
            String numeParticipant = scanner.nextLine();
            System.out.print("Email participant: ");
            String emailParticipant = scanner.nextLine();

            if (!Validare.esteEmailValid(emailParticipant)) {
                System.out.println("Email invalid. Înregistrarea a eșuat.");
            } else {
                if (gestionareParticipanti.addParticipantToEvent(numeParticipant, emailParticipant, ev.getId())) {
                    System.out.println("Participant înregistrat cu succes.");
                } else {
                    System.out.println("Eroare la înregistrarea participantului.");
                }
            }
        } else {
            System.out.println("Evenimentul nu a fost găsit.");
        }
    }


    /**
     * Afișează lista participantilor unui eveniment.
     *
     * @param scanner Scanner pentru input-ul utilizatorului.
     * @param creareEveniment Obiect pentru gestionarea evenimentelor.
     */
    public static void vizualizeazaParticipanti(Scanner scanner, CreareEveniment creareEveniment) {
        System.out.print("Introduceti numele evenimentului: ");
        String numeEveniment = scanner.nextLine();
        Eveniment ev = creareEveniment.getEveniment(numeEveniment);
        if (ev != null && !ev.getParticipanti().isEmpty()) {
            System.out.println("Participanti la evenimentul " + ev.getNume() + ":");
            for (Participant p : ev.getParticipanti()) {
                System.out.println(p.getNume() + " - " + p.getEmail());
            }
        } else {
            System.out.println("Evenimentul nu are participanți sau nu a fost găsit.");
        }
    }
}
