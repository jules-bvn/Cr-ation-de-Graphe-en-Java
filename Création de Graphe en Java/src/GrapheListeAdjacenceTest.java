import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GrapheListeAdjacenceTest {
    private GrapheEntiteJava graphe;

    @BeforeEach
    void setUp() {
        graphe = new GrapheListeAdjacence();
        graphe.ajouterSommet("MainClass");
        graphe.ajouterSommet("HelperClass");
        graphe.ajouterSommet("IEntity");
    }

    @Test
    void testAjouterArete_Succes() {
        graphe.ajouterArete("MainClass", "HelperClass", "create");
        assertTrue(graphe.existeArete("MainClass", "HelperClass"));
        assertEquals("create", graphe.getEtiquetteArete("MainClass", "HelperClass"));
    }

    @Test
    void testAjouterArete_ExceptionSiSommetManquant() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            graphe.ajouterArete("MainClass", "UnknownClass", "uses");
        });
        assertTrue(exception.getMessage().contains("doivent exister"));
    }

    @Test
    void testAjouterArete_ExceptionSiDoublon() {
        graphe.ajouterArete("MainClass", "HelperClass", null);
        assertThrows(IllegalStateException.class, () -> {
            graphe.ajouterArete("MainClass", "HelperClass", "create");
        });
    }

    @Test
    void testGetSuccesseurs_PlusieursAretes() {
        graphe.ajouterArete("MainClass", "HelperClass", "create");
        graphe.ajouterArete("MainClass", "IEntity", "implements");
        
        List<String> successeurs = graphe.getSuccesseurs("MainClass");
        assertEquals(2, successeurs.size());
        assertTrue(successeurs.contains("HelperClass"));
        assertTrue(successeurs.contains("IEntity"));
    }

    @Test
    void testGetSuccesseurs_VideSiAucuneAreteSortante() {
        graphe.ajouterArete("MainClass", "HelperClass", "create");
        List<String> successeurs = graphe.getSuccesseurs("HelperClass");
        assertTrue(successeurs.isEmpty());
    }
}