# Colons_Catanes | PROJET POO | CAMARA Ibrahime / DIAMANT Alexandre

Commande de compilation :
=========================

* Dans un terminal ouvert dans le dossier du projet (\catane_camara_diamant), écrire 
javac -d . src/main/java/Game/*.java .

Exécution du programme :
========================

* Dans le même terminal, écrire java Game.Lanceur.

Utilisation du programme après exécution :
==========================================

* L'accueil du jeu possède plusieurs boutons:
    - "Jouer" lance la partie.
    - "Options" vous permet de choisir combien de joueur vont jouer la partie et quel type de joueur il s'agit.
    - "Quitter" vous permet de quitter l'accueil et terminer l'exécution du programme.
* Une phase de fondation vous attends. Cette phase se déroule en 2 tours. Pendant le premier tour chaque joueur pose une colonie et une route sur arête adjacente a la colonie. Pendant le second tour, l'ordre des joueurs change (le premier devient le dernier, le deuxième devient le troisième et vice-versa) et chaque joueur pose une nouvelle fois une colonie et une route adjacente a la colonie.
* Après cette phase de fondation la partie peut commencer. L'ordre des joueurs revient a la normale. Pendant le tour des joueurs deux dés sont lancés, le résultat de ces dés donne quelle(s) case(s) donne(nt) des ressources en fonction de si est présente une colonie ou une ville sur une intersection adjacente a cette case. 
Après la collecte des ressources plusieurs choix s'offre au joueur : 
    - La construction.
    - L'échange avec les ports.
    - L'achat de carte de développement.
    - Jouer une carte de développement.
    - Consulter les ressources.
    - ou ne rien faire.
* La partie se termine lorsque qu'un des joueurs atteint 10 points de victoire.

