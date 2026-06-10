import java.util.List;

public interface GrapheEntiteJava {
    void ajouterSommet(String nomEntite);
    void ajouterArete(String source, String destination, String etiquette);
    boolean existeArete(String source, String destination);
    String getEtiquetteArete(String source, String destination);
    List<String> getSuccesseurs(String nomEntite);
}