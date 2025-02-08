import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import com.example.db.DBconnection;


public class TestGestionareUtilizatori {

    private GestionareUtilizatori gestionareUtilizatori;

    @BeforeEach
    public void setUp() throws SQLException {
        gestionareUtilizatori = new GestionareUtilizatori();

        // Curățarea tabelei utilizatori înainte de fiecare test
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM utilizatori")) {
            stmt.executeUpdate();
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Curățarea tabelei utilizatori după fiecare test
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM utilizatori")) {
            stmt.executeUpdate();
        }
    }

    @Test
    public void testRegisterAndAuthenticateUser() {
        // Înregistrare utilizator
        boolean inregistrare = gestionareUtilizatori.inregistreazaUtilizator("John Doe", "john.doe@example.com", "password");
        assertTrue(inregistrare);

        // Autentificare utilizator
        Utilizator user = gestionareUtilizatori.autentificaUtilizator("john.doe@example.com", "password");
        assertNotNull(user);
        assertEquals("John Doe", user.getNume());
    }

    @Test
    public void testInvalidLogin() {
        // Înregistrare utilizator
        gestionareUtilizatori.inregistreazaUtilizator("Jane Doe", "jane.doe@example.com", "password");

        // Autentificare utilizator cu parolă greșită
        Utilizator user = gestionareUtilizatori.autentificaUtilizator("jane.doe@example.com", "wrongpassword");
        assertNull(user);
    }

    @Test
    public void testDuplicateEmailRegistration() {
        // Înregistrare utilizator
        boolean primaInregistrare = gestionareUtilizatori.inregistreazaUtilizator("John Doe", "john.doe@example.com", "password");
        assertTrue(primaInregistrare);

        // Încercare de înregistrare cu același email
        boolean duplicat = gestionareUtilizatori.inregistreazaUtilizator("John Doe", "john.doe@example.com", "password");
        assertFalse(duplicat);
    }
}
