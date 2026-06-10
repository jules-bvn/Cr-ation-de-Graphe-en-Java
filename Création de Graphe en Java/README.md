## Équipe
* Rayan MOHAMED 104
* Jules BIENVENU 104
* Moeez MOHAMMAD AKRAM 104
* Sujen RASAKUMARAN 104

### Bilan des Sprints 1 et 2
Ce qui marche : Tout est opérationnel ! Les Sprints 1 et 2 sont terminés et tous nos tests JUnit 5 passent au vert. Notre classe Graphe (qui utilise notre structure Map) implémente bien l'interface IGraphe et gère correctement la création automatique des entités. Nos deux algorithmes de recherche (dependantsDirects et dependantsElargis) fonctionnent parfaitement, y compris la condition d'arrêt au premier paquetage.

Ce qui ne marche pas : Tout fonctionne sans problème.

### Infos utiles pour la recette

* Performances (O(1)) : Nous avons choisi de garder notre implémentation avec les dictionnaires (GrapheMapAdjacence renommé en Graphe) pour la version finale. L'utilisation des HashMap nous permet d'accéder aux dépendances super rapidement, en temps constant.

* Sécurité des données : Pour bien protéger notre graphe, toutes les méthodes qui renvoient des listes (entites(), relationsSortantes(), etc.) retournent des vues non modifiables (unmodifiableSet). Ça empêche de modifier le graphe par accident depuis l'extérieur.

* Souplesse : Si on essaie de lier deux entités qui ne sont pas encore dans le graphe avec ajouterRelation, le programme ne plante pas : il les crée et les ajoute automatiquement avant de faire la liaison.

* Optimisation de l'algorithme : Pour dependantsElargis (Exercice B), on a fait attention à bien filtrer les dépendances statiques au départ, et surtout à couper net la remontée dès qu'on tombe sur le premier paquetage. Ça évite de faire tourner l'algorithme dans le vide jusqu'à la racine du projet.
