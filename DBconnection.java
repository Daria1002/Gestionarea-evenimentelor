package com.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBconnection {
    private static final String URL = "jdbc:mysql://localhost:3306/proiect_p3?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "Strumfii1002*";

    // Obține conexiunea la baza de date
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Inițializează tabelele
    public static void initializeTables() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            // Tabel utilizatori
            String createUtilizatoriTable = """
                CREATE TABLE IF NOT EXISTS utilizatori (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nume VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    parola VARCHAR(100) NOT NULL,
                    rol VARCHAR(50) DEFAULT 'utilizator'
                )
            """;
            stmt.execute(createUtilizatoriTable);

            // Tabel evenimente
            String createEvenimenteTable = """
                CREATE TABLE IF NOT EXISTS evenimente (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nume VARCHAR(100) NOT NULL,
                    data DATE NOT NULL,
                    ora TIME NOT NULL,
                    locatie VARCHAR(100),
                    organizator_id INT,
                    status VARCHAR(20) DEFAULT 'Activ',
                    FOREIGN KEY (organizator_id) REFERENCES utilizatori(id) ON DELETE CASCADE ON UPDATE CASCADE
                )
            """;
            stmt.execute(createEvenimenteTable);

            // Tabel participanti
            String createParticipantiTable = """
                CREATE TABLE IF NOT EXISTS participanti (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nume VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL
                )
            """;
            stmt.execute(createParticipantiTable);

            // Tabel bilete
            String createBileteTable = """
                CREATE TABLE IF NOT EXISTS bilete (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    eveniment_id INT NOT NULL,
                    participant_id INT NOT NULL,
                    FOREIGN KEY (eveniment_id) REFERENCES evenimente(id) ON DELETE CASCADE ON UPDATE CASCADE,
                    FOREIGN KEY (participant_id) REFERENCES participanti(id) ON DELETE CASCADE ON UPDATE CASCADE
                )
            """;
            stmt.execute(createBileteTable);

            // Index-uri suplimentare
            String createEmailIndex = "CREATE INDEX IF NOT EXISTS idx_email ON utilizatori(email)";
            stmt.execute(createEmailIndex);

            System.out.println("Tabelele și index-urile au fost create/verificate cu succes.");

            // Populare opțională a bazei de date cu date de test
            populateTestData(stmt);

        } catch (SQLException e) {
            System.err.println("Eroare la inițializarea tabelelor: " + e.getMessage());
        }
    }

    // Metodă pentru a popula baza de date cu date de test
    private static void populateTestData(Statement stmt) {
        try {
            // Utilizatori de test
            String insertUtilizatori = """
                INSERT INTO utilizatori (nume, email, parola, rol) VALUES
                ('Admin User', 'admin@example.com', 'adminpass', 'Admin'),
                ('Organizer User', 'organizer@example.com', 'organizerpass', 'Organizator'),
                ('Participant User', 'participant@example.com', 'participantpass', 'Participant')
                ON DUPLICATE KEY UPDATE email = email;
            """;
            stmt.execute(insertUtilizatori);

            // Evenimente de test
            String insertEvenimente = """
                INSERT INTO evenimente (nume, data, ora, locatie, organizator_id, status) VALUES
                ('Workshop Java', '2025-02-10', '10:00:00', 'Sala A', 2, 'Activ'),
                ('Concurs Design', '2025-02-20', '12:00:00', 'Sala B', 2, 'Activ')
                ON DUPLICATE KEY UPDATE nume = nume;
            """;
            stmt.execute(insertEvenimente);

            // Participanți de test
            String insertParticipanti = """
                INSERT INTO participanti (nume, email) VALUES
                ('Ion Popescu', 'ion.popescu@example.com'),
                ('Maria Ionescu', 'maria.ionescu@example.com')
                ON DUPLICATE KEY UPDATE email = email;
            """;
            stmt.execute(insertParticipanti);

            // Bilete de test
            String insertBilete = """
                INSERT INTO bilete (eveniment_id, participant_id) VALUES
                (1, 1),
                (1, 2),
                (2, 1)
                ON DUPLICATE KEY UPDATE eveniment_id = eveniment_id;
            """;
            stmt.execute(insertBilete);

            System.out.println("Baza de date a fost populată cu date de test.");

        } catch (SQLException e) {
            System.err.println("Eroare la popularea bazei de date: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Rulează metoda de inițializare
        initializeTables();
    }
}
