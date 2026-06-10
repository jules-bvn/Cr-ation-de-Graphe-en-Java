import java.util.*;

public class GrapheListeAdjacence implements GrapheEntiteJava {
    private final Map<String, List<Arete>> listeAdjacence;

    public GrapheListeAdjacence() {
        this.listeAdjacence = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String nomEntite) {
        listeAdjacence.putIfAbsent(nomEntite, new ArrayList<>());
    }

    @Override
    public void ajouterArete(String source, String destination, String etiquette) {
        if (!listeAdjacence.containsKey(source) || !listeAdjacence.containsKey(destination)) {
            throw new IllegalArgumentException("Les sommets doivent exister avant d'ajouter une arête.");
        }
        if (existeArete(source, destination)) {
            throw new IllegalStateException("L'arête existe déjà.");
        }
        listeAdjacence.get(source).add(new Arete(destination, etiquette));
    }

    @Override
    public boolean existeArete(String source, String destination) {
        if (!listeAdjacence.containsKey(source)) return false;
        for (Arete arete : listeAdjacence.get(source)) {
            if (arete.getDestination().equals(destination)) return true;
        }
        return false;
    }

    @Override
    public String getEtiquetteArete(String source, String destination) {
        if (!listeAdjacence.containsKey(source)) return null;
        for (Arete arete : listeAdjacence.get(source)) {
            if (arete.getDestination().equals(destination)) return arete.getEtiquette();
        }
        return null;
    }

    @Override
    public List<String> getSuccesseurs(String nomEntite) {
        if (!listeAdjacence.containsKey(nomEntite)) return Collections.emptyList();
        List<String> successeurs = new ArrayList<>();
        for (Arete arete : listeAdjacence.get(nomEntite)) {
            successeurs.add(arete.getDestination());
        }
        return successeurs;
    }
}