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

-----------

Format des messages (JSON) :

Tous les messages partagent la même enveloppe :

```json
{
  "type": "NOM_DU_MESSAGE",
  "matchId": "string",
  "tour": 0,
  "data": { }
}
```

Conventions :
- Positions en `{ "x": number, "y": number }` (origine en haut à gauche de la map)
- Angles en degrés (0 = vers la droite, sens horaire)
- `equipe` = id de l'équipe (entier)

--

Runtime :

- GAME_START (moteur -> runtime, une fois par robot)

```json
{
  "type": "GAME_START",
  "matchId": "m-001",
  "tour": 0,
  "data": {
    "match": {
      "type": "1v1",
      "toursMax": 200,
      "dureeTourMs": 500,
      "seed": 123456
    },
    "map": { "largeur": 1000, "hauteur": 1000 },
    "robot": {
      "id": 1,
      "equipe": 1,
      "position": { "x": 50, "y": 50 },
      "direction": 0,
      "pvMax": 100,
      "energieMax": 100,
      "vision": { "angle": 90, "distance": 200 },
      "porteeTir": 150,
      "cargoMax": 5
    },
    "base": { "position": { "x": 0, "y": 0 }, "objectif": 50 }
  }
}
```

- TURN_STATE (moteur -> runtime, chaque tour, un message par robot)

```json
{
  "type": "TURN_STATE",
  "matchId": "m-001",
  "tour": 12,
  "data": {
    "robot": {
      "id": 1,
      "position": { "x": 120, "y": 80 },
      "direction": 45,
      "pv": 80,
      "energie": 40,
      "cargo": 3
    },
    "perception": {
      "allies":   [ { "id": 2, "position": { "x": 140, "y": 90 } } ],
      "ennemis":  [ { "id": 3, "position": { "x": 300, "y": 150 } } ],
      "cristaux": [ { "id": 17, "position": { "x": 200, "y": 100 } } ]
    },
    "base": { "cristaux": 12, "objectif": 50 }
  }
}
```

- ACTION (runtime -> moteur, réponse au TURN_STATE)

```json
{
  "type": "ACTION",
  "matchId": "m-001",
  "tour": 12,
  "data": {
    "robotId": 1,
    "action": "MOVE",
    "params": { }
  }
}
```

  `action` ∈ MOVE | MINE | DEPOSIT | ENHANCE | SHOOT | REPAIR | WAIT
  `params` selon l'action :
    MOVE    -> { "direction": 45, "distance": 10 }
    SHOOT   -> { "direction": 30 }
    MINE    -> { "cristalId": 17 }
    autres  -> { }
  Pas de réponse avant la fin du temps imparti ou action impossible -> WAIT

--

Ligue :

- MATCH_START (ligue -> moteur)

```json
{
  "type": "MATCH_START",
  "matchId": "m-001",
  "tour": 0,
  "data": {
    "mode": "COURSOCRISTO",
    "typePartie": "1v1",
    "joueursParEquipe": 1,
    "toursMax": 200,
    "dureeTourMs": 500,
    "objectifPoints": 50,
    "mapId": "map-01",
    "seed": 123456,
    "equipes": [
      { "id": 1, "elo": 1200, "bots": [ { "id": 1, "nom": "botA" } ] },
      { "id": 2, "elo": 1150, "bots": [ { "id": 3, "nom": "botB" } ] }
    ]
  }
}
```

  `mode` ∈ COURSOCRISTO | DEATHMATCH

- MATCH_END (moteur -> ligue)

```json
{
  "type": "MATCH_END",
  "matchId": "m-001",
  "tour": 143,
  "data": {
    "gagnant": 2,
    "raison": "BASE_PLEINE",
    "egalite": false,
    "scores": [
      { "equipe": 1, "score": 32, "robotsVivants": 1 },
      { "equipe": 2, "score": 50, "robotsVivants": 1 }
    ]
  }
}
```

  `raison` ∈ TOURS_MAX | BASE_PLEINE | EQUIPE_DETRUITE | EGALITE
  En cas d'égalité : `egalite = true`, `gagnant` = équipe avec l'élo le plus bas

- L'affichage est relayé via les messages DISPLAY_INIT / DISPLAY_FRAME ci-dessous

--

Affichage :

- DISPLAY_INIT (moteur -> affichage, une fois au départ)

```json
{
  "type": "DISPLAY_INIT",
  "matchId": "m-001",
  "tour": 0,
  "data": {
    "map": { "largeur": 1000, "hauteur": 1000, "couleur": "GREEN" },
    "regles": {
      "mode": "COURSOCRISTO",
      "typePartie": "1v1",
      "toursMax": 200,
      "objectifPoints": 50
    },
    "bases": [
      { "equipe": 1, "position": { "x": 0, "y": 0 }, "largeur": 50, "hauteur": 50, "couleur": "RED" },
      { "equipe": 2, "position": { "x": 950, "y": 950 }, "largeur": 50, "hauteur": 50, "couleur": "BLUE" }
    ],
    "robots": [
      { "id": 1, "equipe": 1, "position": { "x": 50, "y": 50 }, "direction": 0 },
      { "id": 3, "equipe": 2, "position": { "x": 950, "y": 950 }, "direction": 180 }
    ],
    "cristaux": [
      { "id": 17, "position": { "x": 200, "y": 100 }, "couleur": "YELLOW", "taille": 10 }
    ]
  }
}
```

- DISPLAY_FRAME (moteur -> affichage, chaque tour)

```json
{
  "type": "DISPLAY_FRAME",
  "matchId": "m-001",
  "tour": 12,
  "data": {
    "robots": [
      {
        "id": 1,
        "position": { "x": 120, "y": 80 },
        "direction": 45,
        "action": "MOVE",
        "pv": 80,
        "etat": "VIVANT"
      }
    ],
    "cristaux": [
      { "id": 17, "etat": "AFFICHE" }
    ],
    "bases": [
      { "equipe": 1, "cristaux": 12, "objectif": 50 },
      { "equipe": 2, "cristaux": 9, "objectif": 50 }
    ],
    "scores": [
      { "equipe": 1, "score": 12 },
      { "equipe": 2, "score": 9 }
    ]
  }
}
```

  `robots[].etat`   ∈ VIVANT | MORT
  `cristaux[].etat` ∈ AFFICHE | A_AFFICHER | MINE
