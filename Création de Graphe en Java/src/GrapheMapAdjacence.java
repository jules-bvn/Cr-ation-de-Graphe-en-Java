import java.util.*;

public class GrapheMapAdjacence implements GrapheEntiteJava {
    private final Map<String, Map<String, String>> mapAdjacence;

    public GrapheMapAdjacence() {
        this.mapAdjacence = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String nomEntite) {
        mapAdjacence.putIfAbsent(nomEntite, new HashMap<>());
    }

    @Override
    public void ajouterArete(String source, String destination, String etiquette) {
        if (!mapAdjacence.containsKey(source) || !mapAdjacence.containsKey(destination)) {
            throw new IllegalArgumentException("Les sommets doivent exister avant d'ajouter une arête.");
        }
        if (mapAdjacence.get(source).containsKey(destination)) {
            throw new IllegalStateException("L'arête existe déjà.");
        }
        mapAdjacence.get(source).put(destination, etiquette == null ? "" : etiquette);
    }

    @Override
    public boolean existeArete(String source, String destination) {
        return mapAdjacence.containsKey(source) && mapAdjacence.get(source).containsKey(destination);
    }

    @Override
    public String getEtiquetteArete(String source, String destination) {
        if (existeArete(source, destination)) {
            String etiquette = mapAdjacence.get(source).get(destination);
            return etiquette.isEmpty() ? null : etiquette;
        }
        return null;
    }

    @Override
    public List<String> getSuccesseurs(String nomEntite) {
        if (!mapAdjacence.containsKey(nomEntite)) return Collections.emptyList();
        return new ArrayList<>(mapAdjacence.get(nomEntite).keySet());
    }
}