import com.example.db.DBconnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionareParticipanti {

    // Adaugă un participant la baza de date și la un eveniment specific
    public boolean addParticipantToEvent(String numeParticipant, String emailParticipant, int evenimentId) {
        if (numeParticipant == null || numeParticipant.isEmpty() || emailParticipant == null || emailParticipant.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Numele și email-ul participantului nu pot fi goale.");
            return false;
        }

        String sqlParticipant = "INSERT INTO participanti (nume, email) VALUES (?, ?) ON DUPLICATE KEY UPDATE email = email";
        String sqlBilet = "INSERT INTO bilete (eveniment_id, participant_id) " +
                "SELECT ?, id FROM participanti WHERE email = ?";

        try (Connection conn = DBconnection.getConnection()) {
            // Adaugă participantul în tabelul `participanti`
            try (PreparedStatement stmtParticipant = conn.prepareStatement(sqlParticipant)) {
                stmtParticipant.setString(1, numeParticipant);
                stmtParticipant.setString(2, emailParticipant);
                stmtParticipant.executeUpdate();
            }

            // Verifică dacă participantul este deja asociat evenimentului
            if (isParticipantInEvent(emailParticipant, evenimentId)) {
                JOptionPane.showMessageDialog(null, "Participantul este deja asociat acestui eveniment.");
                return false;
            }

            // Creează o legătură între participant și eveniment în tabelul `bilete`
            try (PreparedStatement stmtBilet = conn.prepareStatement(sqlBilet)) {
                stmtBilet.setInt(1, evenimentId);
                stmtBilet.setString(2, emailParticipant);
                stmtBilet.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Participantul a fost adăugat cu succes la eveniment.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la adăugarea participantului: " + e.getMessage());
            return false;
        }
    }

    // Verifică dacă un participant este deja asociat unui eveniment
    private boolean isParticipantInEvent(String emailParticipant, int evenimentId) {
        String sql = "SELECT COUNT(*) AS count FROM bilete b " +
                "JOIN participanti p ON b.participant_id = p.id " +
                "WHERE p.email = ? AND b.eveniment_id = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emailParticipant);
            stmt.setInt(2, evenimentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt("count") > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Șterge un participant din baza de date
    public boolean deleteParticipant(String email) {
        if (email == null || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email-ul participantului nu poate fi gol.");
            return false;
        }

        String sql = "DELETE FROM participanti WHERE email = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Participantul a fost șters cu succes.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Participantul nu a fost găsit.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la ștergerea participantului: " + e.getMessage());
            return false;
        }
    }

    // Obține lista tuturor participanților din baza de date
    public List<Participant> getAllParticipants() {
        List<Participant> participanti = new ArrayList<>();
        String sql = "SELECT * FROM participanti";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Participant participant = new Participant(
                        rs.getString("nume"),
                        rs.getString("email")
                );
                participanti.add(participant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return participanti;
    }

    // Vizualizează toți participanții din baza de date
    public void vizualizeazaParticipanti() {
        List<Participant> participanti = getAllParticipants();
        if (participanti.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nu există participanți înregistrați.");
        } else {
            StringBuilder sb = new StringBuilder("Lista participanților:\n");
            for (Participant p : participanti) {
                sb.append("Nume: ").append(p.getNume()).append(", Email: ").append(p.getEmail()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    // **Metodă nouă: Afișează participanții împreună cu evenimentele lor**
    public void afiseazaParticipantiCuEvenimente() {
        String sql = """
        SELECT p.nume AS participant_nume, p.email AS participant_email, e.nume AS eveniment_nume, e.status AS eveniment_status
        FROM participanti p
        JOIN bilete b ON p.id = b.participant_id
        JOIN evenimente e ON b.eveniment_id = e.id
        WHERE e.status = 'Activ'
    """;

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            StringBuilder sb = new StringBuilder("Lista participanților și evenimentele lor:\n");
            while (rs.next()) {
                String participantNume = rs.getString("participant_nume");
                String participantEmail = rs.getString("participant_email");
                String evenimentNume = rs.getString("eveniment_nume");

                sb.append("Participant: ").append(participantNume)
                        .append(" (").append(participantEmail).append(") ")
                        .append(" - Eveniment: ").append(evenimentNume).append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eroare la afișarea participanților: " + e.getMessage());
        }
    }

}
