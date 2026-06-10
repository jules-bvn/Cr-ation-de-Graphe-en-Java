import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GrapheMapAdjacenceTest {
    private GrapheEntiteJava graphe;

    @BeforeEach
    void setUp() {
        graphe = new GrapheMapAdjacence();
        graphe.ajouterSommet("Controller");
        graphe.ajouterSommet("Service");
        graphe.ajouterSommet("Repository");
    }

    @Test
    void testAjouterArete_SuccesAvecEtSansEtiquette() {
        graphe.ajouterArete("Controller", "Service", "inject");
        graphe.ajouterArete("Service", "Repository", null);
        
        assertTrue(graphe.existeArete("Controller", "Service"));
        assertEquals("inject", graphe.getEtiquetteArete("Controller", "Service"));
        
        assertTrue(graphe.existeArete("Service", "Repository"));
        assertNull(graphe.getEtiquetteArete("Service", "Repository"));
    }

    @Test
    void testAjouterArete_ExceptionSiSommetManquant() {
        assertThrows(IllegalArgumentException.class, () -> {
            graphe.ajouterArete("Controller", "MissingDependency", null);
        });
    }

    @Test
    void testGetSuccesseurs_BonOrdreEtQuantite() {
        graphe.ajouterArete("Controller", "Service", "calls");
        graphe.ajouterArete("Controller", "Repository", "direct_access");
        
        List<String> successeurs = graphe.getSuccesseurs("Controller");
        assertEquals(2, successeurs.size());
        assertTrue(successeurs.containsAll(List.of("Service", "Repository")));
    }

    @Test
    void testGetSuccesseurs_SommetInconnu() {
        List<String> successeurs = graphe.getSuccesseurs("NonExistent");
        assertTrue(successeurs.isEmpty());
    }
}