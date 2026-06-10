package graphe.impl;

import graphe.modele.IEntite;
import graphe.modele.IGraphe;
import graphe.modele.NatureRelation;
import graphe.modele.RelationEntrante;
import graphe.modele.RelationSortante;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implémentation concrète d'un graphe orienté d'entités Java.
 * Utilise une double indexation par Map pour optimiser les recherches de relations.
 */
public class Graphe implements IGraphe {
    // Stockage unique de toutes les entités du graphe
    private final Set<IEntite> entites = new HashSet<>();
    
    // On indexe les relations dans les deux sens pour garantir des performances en O(1)
    // peu importe si l'on cherche ce qui "sort" d'une classe ou ce qui "arrive" sur elle.
    private final Map<IEntite, Set<RelationSortante>> sortantes = new HashMap<>();
    private final Map<IEntite, Set<RelationEntrante>> entrantes = new HashMap<>();

    @Override
    public boolean ajouterEntite(IEntite entite) {
        if (entites.contains(entite)) {
            return false;
        }
        entites.add(entite);
        // Initialisation des ensembles de relations pour éviter les NullPointerException
        sortantes.put(entite, new HashSet<>());
        entrantes.put(entite, new HashSet<>());
        return true;
    }

    @Override
    public boolean ajouterRelation(IEntite source, IEntite cible, NatureRelation nature) {
        // Selon les exigences des tests, on s'assure que les entités existent dans le graphe
        ajouterEntite(source);
        ajouterEntite(cible);

        RelationSortante relSortante = new RelationSortante(cible, nature);
        RelationEntrante relEntrante = new RelationEntrante(source, nature);

        // On n'ajoute la relation que si elle n'existe pas déjà (gestion des doublons)
        boolean ajoute = sortantes.get(source).add(relSortante);
        if (ajoute) {
            entrantes.get(cible).add(relEntrante);
        }
        return ajoute;
    }

    @Override
    public Set<IEntite> entites() {
        // Sécurisation des données : on renvoie une vue non modifiable
        return Collections.unmodifiableSet(entites);
    }

    @Override
    public Set<RelationSortante> relationsSortantes(IEntite source) {
        if (!entites.contains(source)) return Collections.emptySet();
        return Collections.unmodifiableSet(sortantes.get(source));
    }

    @Override
    public Set<RelationEntrante> relationsEntrantes(IEntite cible) {
        if (!entites.contains(cible)) return Collections.emptySet();
        return Collections.unmodifiableSet(entrantes.get(cible));
    }
}