import com.example.db.DBconnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreareEveniment {

    // Adaugă un eveniment în baza de date folosind parametri individuali
    public boolean adaugaEveniment(String nume, String data, String ora, String locatie, int organizatorId) {
        if (!isEvenimentValid(nume, data, ora, locatie)) {
            JOptionPane.showMessageDialog(null, "Evenimentul nu este valid. Verificați câmpurile!");
            return false;
        }

        String sql = "INSERT INTO evenimente (nume, data, ora, locatie, organizator_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nume);
            stmt.setString(2, data);
            stmt.setString(3, ora);
            stmt.setString(4, locatie);
            stmt.setInt(5, organizatorId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Evenimentul a fost adăugat cu succes.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la adăugarea evenimentului: " + e.getMessage());
            return false;
        }
    }

    // Adaugă un eveniment în baza de date folosind un obiect Eveniment
    public boolean adaugaEveniment(Eveniment eveniment) {
        return adaugaEveniment(
                eveniment.getNume(),
                eveniment.getData(),
                eveniment.getOra(),
                eveniment.getLocatie(),
                eveniment.getOrganizator().getId()
        );
    }

    // Metodă pentru actualizarea unui eveniment existent
    public boolean actualizeazaEveniment(int id, String nume, String data, String ora, String locatie) {
        if (!isEvenimentValid(nume, data, ora, locatie)) {
            JOptionPane.showMessageDialog(null, "Evenimentul nu este valid. Verificați câmpurile!");
            return false;
        }

        String sql = "UPDATE evenimente SET nume = ?, data = ?, ora = ?, locatie = ? WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nume);
            stmt.setString(2, data);
            stmt.setString(3, ora);
            stmt.setString(4, locatie);
            stmt.setInt(5, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Evenimentul a fost actualizat cu succes.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Evenimentul nu a fost găsit.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la actualizarea evenimentului: " + e.getMessage());
            return false;
        }
    }

    // Verifică dacă un eveniment este valid
    private boolean isEvenimentValid(String nume, String data, String ora, String locatie) {
        if (!Validare.esteNumeValid(nume)) {
            JOptionPane.showMessageDialog(null, "Numele poate conține doar litere și spații!");
            return false;
        }
        if (!Validare.esteDataValida(data)) {
            JOptionPane.showMessageDialog(null, "Data trebuie să fie cel puțin ziua curentă!");
            return false;
        }
        if (!Validare.esteOraValida(ora)) {
            JOptionPane.showMessageDialog(null, "Ora evenimentului nu este validă!");
            return false;
        }
        if (locatie == null || locatie.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Locația nu poate fi goală!");
            return false;
        }
        return true;
    }

    // Returnează lista de evenimente din baza de date
    public List<Eveniment> getAllEvenimenteFromDatabase() {
        List<Eveniment> evenimente = new ArrayList<>();
        String sql = """
            SELECT e.id, e.nume, e.data, e.ora, e.locatie, e.status, u.nume AS organizator_nume, u.email AS organizator_email
            FROM evenimente e
            JOIN utilizatori u ON e.organizator_id = u.id
            WHERE e.status = 'Activ'
        """;

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Organizator organizator = new Organizator(
                        rs.getString("organizator_nume"),
                        rs.getString("organizator_email")
                );
                Eveniment eveniment = new Eveniment(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("data"),
                        rs.getString("ora"),
                        rs.getString("locatie"),
                        organizator,
                        null,
                        null
                );
                eveniment.setStatus(rs.getString("status"));
                evenimente.add(eveniment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenimente;
    }

    // Caută un eveniment după nume în baza de date
    public Eveniment getEveniment(String numeEveniment) {
        String sql = """
            SELECT e.id, e.nume, e.data, e.ora, e.locatie, e.status, u.nume AS organizator_nume, u.email AS organizator_email
            FROM evenimente e
            JOIN utilizatori u ON e.organizator_id = u.id
            WHERE e.nume = ? AND e.status = 'Activ'
        """;

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeEveniment);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Organizator organizator = new Organizator(
                            rs.getString("organizator_nume"),
                            rs.getString("organizator_email")
                    );
                    return new Eveniment(
                            rs.getInt("id"),
                            rs.getString("nume"),
                            rs.getString("data"),
                            rs.getString("ora"),
                            rs.getString("locatie"),
                            organizator,
                            null,
                            null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Afișează toate evenimentele disponibile
    public void vizualizeazaEvenimente() {
        List<Eveniment> evenimente = getAllEvenimenteFromDatabase();
        if (evenimente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nu există evenimente disponibile.");
        } else {
            StringBuilder sb = new StringBuilder("Evenimente disponibile:\n");
            for (Eveniment ev : evenimente) {
                sb.append("Nume: ").append(ev.getNume())
                        .append(", Data: ").append(ev.getData())
                        .append(", Ora: ").append(ev.getOra())
                        .append(", Locație: ").append(ev.getLocatie())
                        .append(", Status: ").append(ev.getStatus())
                        .append(", Organizator: ").append(ev.getOrganizator().getNume())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    // Caută și afișează un eveniment
    public void cautaEveniment(String numeEveniment) {
        Eveniment ev = getEveniment(numeEveniment);
        if (ev != null) {
            JOptionPane.showMessageDialog(null, "Eveniment găsit:\n" +
                    "Nume: " + ev.getNume() + "\n" +
                    "Data: " + ev.getData() + "\n" +
                    "Ora: " + ev.getOra() + "\n" +
                    "Locație: " + ev.getLocatie() + "\n" +
                    "Organizator: " + ev.getOrganizator().getNume());
        } else {
            JOptionPane.showMessageDialog(null, "Evenimentul nu a fost găsit sau este anulat.");
        }
    }

    // Anulează un eveniment după nume
    public boolean anuleazaEveniment(String numeEveniment) {
        String sql = "UPDATE evenimente SET status = 'Anulat' WHERE nume = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeEveniment);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Evenimentul a fost anulat cu succes.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Evenimentul nu a fost găsit.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la anularea evenimentului: " + e.getMessage());
            return false;
        }
    }

    // Anulează un eveniment după ID
    public boolean anulareEveniment(int evenimentId) {
        String sql = "UPDATE evenimente SET status = 'Anulat' WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, evenimentId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Evenimentul a fost anulat cu succes.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Evenimentul nu a fost găsit.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la anularea evenimentului: " + e.getMessage());
            return false;
        }
    }
}
