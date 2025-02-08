import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestParticipant {

    @Test
    public void testParticipantProperties() {
        Participant participant = new Participant("John Doe", "john.doe@example.com");
        assertEquals("John Doe", participant.getNume());
        assertEquals("john.doe@example.com", participant.getEmail());
    }

    @Test
    public void testSetProperties() {
        Participant participant = new Participant();
        participant.setNume("Jane Doe");
        participant.setEmail("jane.doe@example.com");
        assertEquals("Jane Doe", participant.getNume());
        assertEquals("jane.doe@example.com", participant.getEmail());
    }

    @Test
    public void testInvalidEmailAndName() {
        Participant participant = new Participant();
        participant.setNume("1234");
        participant.setEmail("invalid_email");
        assertNotEquals("1234", participant.getNume());
        assertNotEquals("invalid_email", participant.getEmail());
    }
}
