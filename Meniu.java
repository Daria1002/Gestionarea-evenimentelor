import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class Meniu {
    private JPanel panel1;
    private JButton adaugaEvenimentButton;
    private JButton vizualizeazaEvenimenteButton;
    private JButton cautaEvenimentButton;
    private JButton inregistreazaParticipantButton;
    private JButton vizualizeazaParticipantiButton;
    private JButton logoutButton;
    private JButton anuleazaEvenimentButton;
    private JButton vizualizeazaParticipantiSiEvenimenteButton;

    private CreareEveniment creareEveniment;
    private GestionareParticipanti gestionareParticipanti;

    public Meniu() {
        creareEveniment = new CreareEveniment();
        gestionareParticipanti = new GestionareParticipanti();

        // Initialize buttons
        adaugaEvenimentButton = new JButton("Adaugă eveniment");
        vizualizeazaEvenimenteButton = new JButton("Vizualizează evenimente");
        cautaEvenimentButton = new JButton("Caută eveniment");
        inregistreazaParticipantButton = new JButton("Înregistrează participant");
        vizualizeazaParticipantiButton = new JButton("Vizualizează participanți");
        logoutButton = new JButton("Logout");
        anuleazaEvenimentButton = new JButton("Anulează eveniment");
        vizualizeazaParticipantiSiEvenimenteButton = new JButton("Afișează participanți și evenimente");

        // Configure panel
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(8, 1)); // 8 buttons in one column
        panel1.add(adaugaEvenimentButton);
        panel1.add(vizualizeazaEvenimenteButton);
        panel1.add(cautaEvenimentButton);
        panel1.add(inregistreazaParticipantButton);
        panel1.add(vizualizeazaParticipantiButton);
        panel1.add(anuleazaEvenimentButton);
        panel1.add(vizualizeazaParticipantiSiEvenimenteButton);
        panel1.add(logoutButton);

        // Create and display the window
        JFrame frame = new JFrame("Meniu Evenimente");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new Dimension(500, 400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Add functionality to buttons
        adaugaEvenimentButton.addActionListener(e -> {
            String nume = JOptionPane.showInputDialog("Introdu numele evenimentului:");
            String data = JOptionPane.showInputDialog("Introdu data evenimentului (yyyy-MM-dd):");
            String ora = JOptionPane.showInputDialog("Introdu ora evenimentului (HH:mm):");
            String locatie = JOptionPane.showInputDialog("Introdu locația evenimentului:");

            if (nume == null || data == null || ora == null || locatie == null ||
                    nume.isEmpty() || data.isEmpty() || ora.isEmpty() || locatie.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Toate câmpurile trebuie completate!");
                return;
            }

            int organizatorId = 1; // Example organizer ID
            if (creareEveniment.adaugaEveniment(nume, data, ora, locatie, organizatorId)) {
                JOptionPane.showMessageDialog(null, "Eveniment adăugat cu succes!");
            } else {
                JOptionPane.showMessageDialog(null, "Eroare la adăugarea evenimentului!");
            }
        });

        vizualizeazaParticipantiSiEvenimenteButton.addActionListener(this::handleVizualizeazaParticipantiSiEvenimente);

        anuleazaEvenimentButton.addActionListener(e -> {
            String numeEveniment = JOptionPane.showInputDialog("Introdu numele evenimentului de anulat:");
            if (numeEveniment != null && !numeEveniment.trim().isEmpty()) {
                boolean succes = creareEveniment.anuleazaEveniment(numeEveniment);
                if (succes) {
                    JOptionPane.showMessageDialog(null, "Evenimentul a fost anulat cu succes!");
                } else {
                    JOptionPane.showMessageDialog(null, "Evenimentul nu a fost găsit sau nu poate fi anulat.");
                }
            }
        });

        vizualizeazaEvenimenteButton.addActionListener(e -> creareEveniment.vizualizeazaEvenimente());

        cautaEvenimentButton.addActionListener(e -> {
            String numeEveniment = JOptionPane.showInputDialog("Introdu numele evenimentului:");
            if (numeEveniment == null || numeEveniment.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Numele evenimentului nu poate fi gol!");
                return;
            }
            creareEveniment.cautaEveniment(numeEveniment);
        });

        inregistreazaParticipantButton.addActionListener(e -> {
            List<Eveniment> evenimente = creareEveniment.getAllEvenimenteFromDatabase();
            if (evenimente.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nu există evenimente disponibile pentru înregistrare.");
                return;
            }

            String[] numeEvenimente = evenimente.stream().map(Eveniment::getNume).toArray(String[]::new);
            String evenimentSelectat = (String) JOptionPane.showInputDialog(
                    null,
                    "Selectează evenimentul:",
                    "Înregistrare Participant",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    numeEvenimente,
                    numeEvenimente[0]
            );

            if (evenimentSelectat != null) {
                String numeParticipant = JOptionPane.showInputDialog("Introdu numele participantului:");
                String emailParticipant = JOptionPane.showInputDialog("Introdu email-ul participantului:");

                if (numeParticipant == null || emailParticipant == null ||
                        numeParticipant.isEmpty() || emailParticipant.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Toate câmpurile trebuie completate!");
                    return;
                }

                Eveniment eveniment = evenimente.stream()
                        .filter(ev -> ev.getNume().equals(evenimentSelectat))
                        .findFirst()
                        .orElse(null);

                if (eveniment != null) {
                    if (gestionareParticipanti.addParticipantToEvent(numeParticipant, emailParticipant, eveniment.getId())) {
                        JOptionPane.showMessageDialog(null, "Participantul a fost înregistrat cu succes!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Eroare la înregistrarea participantului!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Eroare: Evenimentul selectat nu a fost găsit!");
                }
            }
        });

        vizualizeazaParticipantiButton.addActionListener(e -> gestionareParticipanti.vizualizeazaParticipanti());

        logoutButton.addActionListener(e -> {
            frame.dispose();
            new Login();
        });
    }

    private void handleVizualizeazaParticipantiSiEvenimente(ActionEvent e) {
        gestionareParticipanti.afiseazaParticipantiCuEvenimente();
    }

    public static void main(String[] args) {
        new Meniu();
    }
}
