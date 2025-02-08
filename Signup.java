import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup {
    private JPanel panel1;
    private JTextField tfNume;
    private JTextField tfEmail;
    private JPasswordField pfParola;
    private JButton signupButton;

    public Signup() {
        JFrame frame = new JFrame("Signup");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new java.awt.Dimension(500, 400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Instanțiază GestionareUtilizatori pentru a accesa baza de date
        GestionareUtilizatori gestionareUtilizatori = new GestionareUtilizatori();

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nume = tfNume.getText();
                String email = tfEmail.getText();
                String parola = new String(pfParola.getPassword());

                // Verificăm dacă toate câmpurile sunt completate
                if (nume.isEmpty() || email.isEmpty() || parola.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Toate câmpurile trebuie completate!");
                    return;
                }

                // Validare nume
                if (!Validare.esteNumeValid(nume)) {
                    JOptionPane.showMessageDialog(null, "Numele poate conține doar litere și spații!");
                    return;
                }

                // Validare email
                if (!Validare.esteEmailValid(email)) {
                    JOptionPane.showMessageDialog(null, "Email-ul nu este valid!");
                    return;
                }

                // Validare parolă (de exemplu, minim 6 caractere)
                if (parola.length() < 6) {
                    JOptionPane.showMessageDialog(null, "Parola trebuie să conțină cel puțin 6 caractere!");
                    return;
                }

                // Înregistrare utilizator în baza de date
                boolean succes = gestionareUtilizatori.inregistreazaUtilizator(nume, email, parola);
                if (succes) {
                    JOptionPane.showMessageDialog(null, "Te-ai înregistrat cu succes!");
                    frame.dispose();
                    new Login(); // Deschide fereastra Login
                } else {
                    JOptionPane.showMessageDialog(null, "Email-ul este deja utilizat!");
                }
            }
        });
    }

    public static void main(String[] args) {
        new Signup();
    }
}
