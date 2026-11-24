Une partie :

- Tour (entier)
- Durée de tour (entier)
- Map (hauteur, largeur, emplacement des bases, positions de départ, répartition des cristaux)
- Type (enum 1v1, 2v2..)
- Bots (liste de bots)

-> Les robots décident d'une action en même temps (une fois qu'ils ont tous sélectionnés ou que le temps imparti est dépassé)
-> Fin de match possibles : nombre de tour maximum atteint, objectif de point, plus aucun robot dans l'équipe ou combinaison
-> Système de seed pour l'initialisation sur chaque bot

Un robot :

- Position (x, y)
- Direction (angle)
- Point de vie (entier)
- Point d'énergie (entier)
- Portée de tir (coefficient)
- Champ de vision (angle, taille)
- Cargo (entier) -> Nombre maximal de cristaux

-> Il regagne de l'énergie à chaque tour
-> Les caractéristiques des robots sont définis par le type de partie
-> Doit pouvoir faire les actions : MOVE, MINE, DEPOSIT, ENHANCE, SHOOT, REPAIR, WAIT
-> Une action impossible est convertie en WAIT (énergie insuffisante, cargo plein, hors de portée..)

Cristaux et points : 

Base aliée/enemie :
- Nombre de cristaux contenus
- Objectif en nombre de cristaux (entier)
- Position (x, y)

-> Récupère les cristaux

Règles de fin de match : 

- Égalité : dans ce cas c'est l'équipe avec l'élo le plus bas qui gagne le match
- Tour maximum atteint
- La base est remplie
- Une équipe a tuée tous les membres de l'équipe adverse

-----------

Contrats :

Runtime :

- Départ: 
    type de match (match)
    Taille du monde (map)
    /*Vitesse de déplacement (robot)*/
    Nombre de points de vie initiaux (robot)
    Vision (robot)
    Portée de tir (arme)
    Position base (robot)
    Taille Max du cargo (robot)
    Durée de la partie (match)

- Partie:
    Nouvelle position du bot (robot)
    Perception (vue) : Allié/Cristaux/ennemie (robot) 
    Nombre de cristaux dans cargo (robot)
    Point de vie (robot)
    Nombre de cristaux dans la base (base)

--

Ligue :

- Start (ligue):
    Choix mode de jeux
    Durée de la partie
    Objectif de point pour gagner
    Choix de la Map
    Nombre de joueurs par équipe
    Type de partie (règle)

- Fin de partie (engine):
    Résultat (score par équipes, gagnant)

- Démarrage de la partie :
- Fin de partie :
- Envoyer l'affichage (elements { positions + etats des robots + etat des bases }, Score scores, )

--

Affichage :

- Départ:
    Map
    Bases : Team/Coordonées/Dimensions
    Robots : Orientation/Position
    Règles du match
    Cristaux : Couleur, taille

- Partie:
    Robots: Position, Action, vie, orientation, état (vivant/mort),
    Cristaux: état (afficher/ à afficher/ miné)
    Score par équipe
    Numéro du tour