import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestValidare {

    @Test
    public void testEsteEmailValid() {
        assertTrue(Validare.esteEmailValid("test@example.com"));
        assertFalse(Validare.esteEmailValid("testexample.com"));
        assertFalse(Validare.esteEmailValid("test@.com"));
        assertFalse(Validare.esteEmailValid(null));
    }

    @Test
    public void testEsteNumeValid() {
        assertTrue(Validare.esteNumeValid("John Doe"));
        assertFalse(Validare.esteNumeValid("John123"));
        assertFalse(Validare.esteNumeValid(""));
        assertFalse(Validare.esteNumeValid(null));
    }

    @Test
    public void testEsteDataValida() {
        assertTrue(Validare.esteDataValida("2024-12-31"));
        assertFalse(Validare.esteDataValida("2024-02-30"));
        assertFalse(Validare.esteDataValida("invalid"));
        assertFalse(Validare.esteDataValida(null));
    }

    @Test
    public void testEsteOraValida() {
        assertTrue(Validare.esteOraValida("14:30"));
        assertFalse(Validare.esteOraValida("25:00"));
        assertFalse(Validare.esteOraValida("14:61"));
        assertFalse(Validare.esteOraValida(null));
    }
}
