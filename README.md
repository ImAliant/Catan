# Colons_Catanes | PROJET POO | CAMARA Ibrahime / DIAMANT Alexandre

Commande de compilation :
=========================

* Dans un terminal ouvert dans le dossier du projet (\colons_catanes), écrire la ligne suivante : 
    - "javac -d . catane_camara_diamant/src/main/java/Game/*.java catane_camara_diamant/src/main/java/Ui/*.java" (ajoutez un * si ils ne sont pas visible avant les .java). 

Exécution du programme :
========================

* Dans le même terminal, écrire " java Game.Lanceur " .

Utilisation du programme après exécution :
==========================================

* L'accueil du jeu possède plusieurs boutons:

    - "Jouer" lancera la partie.

    - "Options" vous permettra de choisir combien de joueur vont jouer la partie, le type de joueurs (IA/Humain)
       et le choix de jouer avec/sans l'interface graphique.

    - "Quitter" vous permettra de quitter l'accueil et terminer l'exécution du programme.


* Une phase de fondation vous attendra. Cette phase se déroulera en 2 tours. Pendant le premier tour, chaque joueur posera une colonie sur les arêtes, soit les intersections, disponibles sur le plateau et une route sur les segments adjacents à la colonie. 
Pendant le second tour, l'ordre des joueurs changera (le premier deviendra le dernier, le deuxième deviendra le troisième et vice-versa) et chaque joueur posera une nouvelle fois une colonie et une route adjacente à la colonie.


* Après cette phase de fondation, la partie pourra commencer. L'ordre des joueurs reviendra à la normale.
  Pendant le tour des joueurs, deux dés seront lancés, le résultat de ces dés donneront quelle(s) case(s) donne(ront) des ressources en fonction de la présence d'une colonie ou d'une ville sur une intersection adjacente a cette case. 
    Après la collecte des ressources plusieurs choix s'offre au joueur : 
    - La construction ou amélioration d'une colonie ou la construction d'une route.
    - L'échange avec/sans les ports .
    - L'achat de carte de développement.
    - Jouer une carte de développement.
    - Consulter les ressources.
    - Ne rien faire.


* Au sein d'un tour, il se peut que, sous certaines conditions, un joueur pourra déplacer le voleur, présent au départ dans la tuile du desért. Lors de ce tour, aucune ressource sera permis d'être recolté et provoquera des malus 
au(x) joueur(s) possedant une colonie adjacente sur la tuile où le voleur a été deplacé.


* La partie se terminera lorsque qu'un des joueurs aura atteint 10 points de victoire.

