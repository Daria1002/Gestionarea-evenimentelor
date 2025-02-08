import com.example.db.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clasa GestionareUtilizatori se ocupă cu gestionarea utilizatorilor din baza de date.
 */
public class GestionareUtilizatori {

    /**
     * Înregistrează un utilizator nou în baza de date.
     *
     * @param nume   Numele utilizatorului.
     * @param email  Email-ul utilizatorului.
     * @param parola Parola utilizatorului.
     * @return true dacă utilizatorul a fost înregistrat cu succes, false dacă email-ul există deja.
     */
    public boolean inregistreazaUtilizator(String nume, String email, String parola) {
        String verificareSQL = "SELECT COUNT(*) FROM utilizatori WHERE email = ?";
        String insertSQL = "INSERT INTO utilizatori (nume, email, parola) VALUES (?, ?, ?)";

        try (Connection conn = DBconnection.getConnection()) {
            // Verifică dacă email-ul există deja
            try (PreparedStatement verificareStmt = conn.prepareStatement(verificareSQL)) {
                verificareStmt.setString(1, email);
                ResultSet rs = verificareStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Email-ul este deja înregistrat.");
                    return false; // Email-ul există deja
                }
            }

            // Adaugă utilizatorul în baza de date
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
                insertStmt.setString(1, nume);
                insertStmt.setString(2, email);
                insertStmt.setString(3, parola);
                insertStmt.executeUpdate();
                System.out.println("Utilizator adăugat cu succes.");
            }

            return true; // Utilizatorul a fost înregistrat
        } catch (SQLException e) {
            System.err.println("Eroare la înregistrarea utilizatorului: " + e.getMessage());
            return false;
        }
    }

    /**
     * Autentifică un utilizator folosind email-ul și parola.
     *
     * @param email  Email-ul utilizatorului.
     * @param parola Parola utilizatorului.
     * @return Obiectul Utilizator dacă autentificarea a reușit, null altfel.
     */
    public Utilizator autentificaUtilizator(String email, String parola) {
        String query = "SELECT id, nume, email FROM utilizatori WHERE email = ? AND parola = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, parola);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Autentificare reușită pentru utilizatorul: " + rs.getString("nume"));
                return new Utilizator(rs.getInt("id"), rs.getString("nume"), rs.getString("email"));
            } else {
                System.out.println("Autentificare eșuată. Email sau parolă incorectă.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Eroare la autentificare: " + e.getMessage());
            return null;
        }
    }

    /**
     * Șterge un utilizator din baza de date pe baza email-ului.
     *
     * @param email Email-ul utilizatorului care trebuie șters.
     * @return true dacă ștergerea a reușit, false altfel.
     */
    public boolean stergeUtilizator(String email) {
        String deleteSQL = "DELETE FROM utilizatori WHERE email = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {
            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilizator șters cu succes.");
                return true;
            } else {
                System.out.println("Nu există un utilizator cu acest email.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Eroare la ștergerea utilizatorului: " + e.getMessage());
            return false;
        }
    }

    /**
     * Afișează toți utilizatorii din baza de date.
     */
    public void afiseazaUtilizatori() {
        String query = "SELECT id, nume, email FROM utilizatori";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Lista utilizatorilor înregistrați:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Nume: " + rs.getString("nume") +
                        ", Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Eroare la afișarea utilizatorilor: " + e.getMessage());
        }
    }
}
