package graphe.outils;

import graphe.modele.IGraphe;
import graphe.modele.IEntite;
import graphe.modele.NatureRelation;
import graphe.modele.RelationEntrante;

import java.util.HashSet;
import java.util.Set;

/**
 * Bibliothèque d'algorithmes génériques pour l'analyse des dépendances.
 */
public final class AlgorithmesGraphe {
    
    /**
     * Identifie les entités qui ont une dépendance directe (statique) vers une cible.
     * La relation de contenance (CONTIENT) est ignorée ici.
     */
    public static Set<IEntite> dependantsDirects(IGraphe graphe, IEntite cible) {
        Set<IEntite> dependants = new HashSet<>();
        
        // On explore les relations arrivant sur la cible pour identifier les sources
        for (RelationEntrante rel : graphe.relationsEntrantes(cible)) {
            if (rel.nature().estDependanceStatique()) {
                dependants.add(rel.source());
            }
        }
        return dependants;
    }

    /**
     * Calcule la clôture transitive des dépendants en remontant la hiérarchie de contenance.
     * Le parcours s'arrête dès qu'un paquetage est rencontré.
     */
    public static Set<IEntite> dependantsElargis(IGraphe graphe, IEntite cible) {
        Set<IEntite> resultats = new HashSet<>();
        Set<IEntite> directs = dependantsDirects(graphe, cible);

        // Pour chaque dépendant direct, on "remonte" vers ses parents
        for (IEntite direct : directs) {
            remonterContenance(graphe, direct, resultats);
        }
        return resultats;
    }

    /**
     * Méthode récursive pour remonter les relations de contenance.
     */
    private static void remonterContenance(IGraphe graphe, IEntite courant, Set<IEntite> resultats) {
        resultats.add(courant);

        // CONDITION D'ARRÊT : On ne remonte pas au-delà du premier paquetage rencontré
        if (!courant.estType()) {
            return;
        }

        // On cherche récursivement l'entité qui contient l'entité actuelle
        for (RelationEntrante rel : graphe.relationsEntrantes(courant)) {
            if (rel.nature() == NatureRelation.CONTIENT) {
                remonterContenance(graphe, rel.source(), resultats);
            }
        }
    }
}