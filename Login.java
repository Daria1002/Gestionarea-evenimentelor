import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPanel panel1;
    private JTextField tfEmail;
    private JPasswordField pfParola;
    private JButton loginButton;

    public Login() {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new java.awt.Dimension(400, 300));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Instanțiază GestionareUtilizatori pentru a accesa baza de date
        GestionareUtilizatori gestionareUtilizatori = new GestionareUtilizatori();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String parola = new String(pfParola.getPassword());

                if (email.isEmpty() || parola.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vă rugăm să completați toate câmpurile!");
                    return;
                }

                // Autentificare utilizator din baza de date
                Utilizator utilizator = gestionareUtilizatori.autentificaUtilizator(email, parola);
                if (utilizator != null) {
                    JOptionPane.showMessageDialog(null, "Login reușit! Bun venit, " + utilizator.getNume());
                    frame.dispose(); // Închide fereastra curentă
                    new Meniu(); // Deschide meniul principal
                } else {
                    JOptionPane.showMessageDialog(null, "Email sau parolă greșită!");
                }
            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}
