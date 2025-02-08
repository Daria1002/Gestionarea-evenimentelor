import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCreareEveniment {

    @Test
    public void testAddValidEveniment() {
        CreareEveniment ce = new CreareEveniment();
        Organizator organizator = new Organizator("Daria", "daria@example.com");
        Eveniment eveniment = new Eveniment("Concert", "2024-12-31", "20:00", "Sala Polivalenta", organizator, null, null);

        ce.addEveniment(eveniment);
        assertEquals(1, ce.getAllEvenimente().size());
        assertEquals("Concert", ce.getAllEvenimente().get(0).getNume());
    }

    @Test
    public void testInvalidEvenimentNotAdded() {
        CreareEveniment ce = new CreareEveniment();
        Organizator organizator = new Organizator("Daria", "daria@example.com");
        Eveniment eveniment = new Eveniment("", "2024-12-31", "20:00", "Sala Polivalenta", organizator, null, null);

        ce.addEveniment(eveniment);
        assertEquals(0, ce.getAllEvenimente().size());
    }

    @Test
    public void testInvalidEvenimentNotAdded_InvalidData() {
        CreareEveniment ce = new CreareEveniment();
        Organizator organizator = new Organizator("Daria", "daria@example.com");
        Eveniment eveniment = new Eveniment("Festival", "2023-12-01", "20:00", "Sala Polivalenta", organizator, null, null);

        ce.addEveniment(eveniment);
        assertEquals(0, ce.getAllEvenimente().size());
    }

    @Test
    public void testInvalidEvenimentNotAdded_InvalidOrganizator() {
        CreareEveniment ce = new CreareEveniment();
        Organizator organizator = new Organizator("", "");
        Eveniment eveniment = new Eveniment("Expozitie", "2024-12-31", "20:00", "Sala Polivalenta", organizator, null, null);

        ce.addEveniment(eveniment);
        assertEquals(0, ce.getAllEvenimente().size());
    }

    @Test
    public void testRetrieveEvenimentByName() {
        CreareEveniment ce = new CreareEveniment();
        Organizator organizator = new Organizator("Daria", "daria@example.com");
        Eveniment eveniment = new Eveniment("Concert", "2024-12-31", "20:00", "Sala Polivalenta", organizator, null, null);

        ce.addEveniment(eveniment);

        Eveniment retrieved = ce.getEveniment("Concert");
        assertNotNull(retrieved);
        assertEquals("Concert", retrieved.getNume());

        assertNull(ce.getEveniment("Festival"));
    }

    @Test
    public void testGetAllEvenimente() {
        CreareEveniment ce = new CreareEveniment();
        Organizator organizator = new Organizator("Daria", "daria@example.com");

        ce.addEveniment(new Eveniment("Concert", "2024-12-31", "20:00", "Sala Polivalenta", organizator, null, null));
        ce.addEveniment(new Eveniment("Festival", "2024-12-25", "18:00", "Parcul Central", organizator, null, null));

        assertEquals(2, ce.getAllEvenimente().size());
    }
}
