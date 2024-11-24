package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.awt.Color;

/**
 * Cette classe représente les monstres dans le jeu Pacman. 
 * Les monstres ont différents comportements selon leur type : 
 * - type 0 : chasse Pacman,
 * - type 1 : fuit Pacman,
 * - type 2 : se déplace de manière autonome,
 * - type 3 : patrouille dans une zone aléatoire.
 * 
 * Chaque monstre possède une position (x, y), une couleur et un comportement spécifique.
 */
public class Monstres {

    private int x;								// Position actuelle du monstre sur l'axe x.
    private int y;								// Position actuelle du monstre sur l'axe y.
    private int previousX, previousY; 			// Position antérieur
    private final int distanceThreshold = 10;	// Distance à partir de laquelle le monstre détecte Pacman pour certains comportements.
    private final int randomMoveCooldown = 3;	// Nombre de mouvements aléatoires à effectuer lorsqu'aucune autre action n'est possible.
    private int randomMoveStepsRemaining = 0;	// Compteur pour suivre le nombre de mouvements aléatoires restants.
    private int moveDelayCounter;				// Compteur pour introduire un délai entre les mouvements.
    private int lastDirection;					// Direction du dernier mouvement (0 : haut, 1 : droite, 2 : bas, 3 : gauche). 
    private int type;							// Type de comportement du monstre.
    private Color color;						// Couleur associée au monstre selon son type.

    /**
     * Constructeur de la classe Monstres.
     * 
     * @param startX La position de départ sur l'axe x.
     * @param startY La position de départ sur l'axe y.
     * @param type   Le type de comportement du monstre (de 0 à 3).
     */
    public Monstres(int startX, int startY, int type) {
        this.x = startX;
        this.y = startY;
        this.type = type;
        this.moveDelayCounter = 0;
        this.lastDirection = -1;

        // Définit la couleur en fonction du type de monstre.
        switch (type) {
            case 0: color = Color.RED; break;
            case 1: color = Color.PINK; break;
            case 2: color = Color.CYAN; break;
            case 3: color = Color.ORANGE; break;
            default: color = Color.GRAY; break;
        }
    }

    /**
     * Retourne la position actuelle du monstre sur l'axe x. 
     * @return La position x.
     */
    public int getX() { return this.x; }

    /**
     * Retourne la position actuelle du monstre sur l'axe y.
     * @return La position y.
     */
    public int getY() { return this.y; }

    /**
     * Retourne la couleur associée au monstre.
     * @return La couleur du monstre.
     */
    public Color getColor() { return this.color; }

    /**
     * Déplace le monstre selon son type de comportement.
     * 
     * @param pacmanX La position x actuelle de Pacman.
     * @param pacmanY La position y actuelle de Pacman.
     * @param board   La représentation du plateau de jeu.
     */
    public void moveMonstre(int pacmanX, int pacmanY, Board board) {
        // Si un délai est actif, le monstre attend avant de bouger.
        if (moveDelayCounter > 0) {
            moveDelayCounter--;
            return;
        }

        // Exécute le comportement spécifique selon le type du monstre.
        switch (type) {
            case 0:
                chasePacman(pacmanX, pacmanY, board); 	// Chasse Pacman.
                break;
            case 1:
                evadePacman(pacmanX, pacmanY, board); 	// Fuit Pacman.
                break;
            case 2:
                autonomousMove(board); 					// Se déplace de manière autonome.
                break;
            case 3:
                patrol(board); 							// Patrouille dans une zone définie.
                break;
        }

        // Réinitialise le compteur pour introduire un délai entre les mouvements.
        moveDelayCounter = 2;
    }

    /**
     * Méthode permettant au monstre de suivre Pacman ou de se déplacer de manière aléatoire.
     *
     * @param pacmanX Coordonnée X actuelle de Pacman
     * @param pacmanY Coordonnée Y actuelle de Pacman
     * @param board Plateau de jeu contenant les murs et les chemins
     */
    private void chasePacman(int pacmanX, int pacmanY, Board board) {
        // Probabilité de mouvement aléatoire
        if (Math.abs(this.x - pacmanX) > distanceThreshold || Math.abs(this.y - pacmanY) > distanceThreshold) {
        	autonomousMove(board);
            return;
        }

        // Si le mouvement n'est pas aléatoire, utiliser l'algorithme A*
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        HashSet<String> closedSet = new HashSet<>();
        
        openSet.add(new Node(this.x, this.y, 0, calculateHeuristic(this.x, this.y, pacmanX, pacmanY), null));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            String currentKey = current.x + "," + current.y;

            if (current.x == pacmanX && current.y == pacmanY) {
                moveAlongPath(current);
                return;
            }

            closedSet.add(currentKey);

            for (int[] dir : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                String neighborKey = newX + "," + newY;

                if (closedSet.contains(neighborKey) || !board.canMove(newX, newY)) {
                    continue;
                }

                int newGCost = current.gCost + 1;
                int newHCost = calculateHeuristic(newX, newY, pacmanX, pacmanY);
                Node neighbor = new Node(newX, newY, newGCost, newHCost, current);

                openSet.add(neighbor);
            }
        }
    }

    /**
     * Calcule l'estimation du coût pour atteindre Pacman (distance de Manhattan).
     *
     * @param x Coordonnée X actuelle
     * @param y Coordonnée Y actuelle
     * @param pacmanX Coordonnée X de Pacman
     * @param pacmanY Coordonnée Y de Pacman
     * @return La distance de Manhattan entre le monstre et Pacman
     */
    private int calculateHeuristic(int x, int y, int pacmanX, int pacmanY) {
        return Math.abs(x - pacmanX) + Math.abs(y - pacmanY);
    }

    /**
     * Reconstruit le chemin trouvé par l'algorithme A* et déplace le monstre
     * au prochain noeud sur ce chemin.
     *
     * @param target Noeud cible représentant la position de Pacman
     */
    private void moveAlongPath(Node target) {
        // Remonter le chemin jusqu'au noeud avant la position actuelle
        while (target.parent != null && target.parent.parent != null) {
            target = target.parent;
        }
        
        // Mettre à jour la position du monstre
        this.x = target.x;
        this.y = target.y;
    }
    
    /**
     * Cette méthode permet au monstre de fuir Pacman en fonction de sa position actuelle.
     * Le monstre se déplace soit aléatoirement s'il est suffisamment éloigné de Pacman, 
     * soit en choisissant la direction qui l'éloigne le plus de Pacman.
     * Les mouvements sont limités par les bords du tableau et les portails.
     * 
     * @param pacmanX La position X de Pacman sur le tableau.
     * @param pacmanY La position Y de Pacman sur le tableau.
     * @param board Le plateau de jeu qui contient des informations sur les mouvements possibles.
     */
    private void evadePacman(int pacmanX, int pacmanY, Board board) {
        // Si le monstre est suffisamment éloigné de Pacman, il se déplace de manière aléatoire.
        // Cela signifie que la distance absolue entre les positions X ou Y du monstre et de Pacman est supérieure à un seuil.
        if (Math.abs(this.x - pacmanX) > distanceThreshold || Math.abs(this.y - pacmanY) > distanceThreshold) {
            autonomousMove(board);  // Déplacement aléatoire du monstre
            return;  // On arrête l'exécution de la méthode si le monstre se déplace aléatoirement
        }

        // Sinon, le monstre doit essayer de s'éloigner de Pacman en choisissant la direction qui maximise la distance
        int bestX = this.x;
        int bestY = this.y;
        int maxDistance = -1;  // Initialise la distance maximale comme étant très petite

        // Définition des directions possibles : haut, bas, gauche, droite
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        // Pour chaque direction possible, on calcule la distance de Manhattan jusqu'à Pacman
        for (int[] dir : directions) {
            int newX = this.x + dir[0];  // Calcul de la nouvelle position X
            int newY = this.y + dir[1];  // Calcul de la nouvelle position Y

            // On vérifie si le mouvement est valide :
            // - Le monstre ne doit pas sortir du tableau (vérifié par board.canMove)
            // - Le monstre ne doit pas revenir sur sa position précédente (éviter les va-et-vient)
            if (board.canMove(newX, newY) && !(newX == this.previousX && newY == this.previousY)) {
                // Calcul de la distance de Manhattan entre la nouvelle position et Pacman
                int distanceToPacman = Math.abs(newX - pacmanX) + Math.abs(newY - pacmanY);

                // Si cette nouvelle position est plus éloignée de Pacman, on la sélectionne comme meilleure position
                if (distanceToPacman > maxDistance) {
                    maxDistance = distanceToPacman;  // Mise à jour de la distance maximale
                    bestX = newX;  // Mise à jour de la meilleure position X
                    bestY = newY;  // Mise à jour de la meilleure position Y
                }
            }
        }

        // On garde la position actuelle pour éviter un déplacement en arrière (en revenant à la position précédente)
        this.previousX = this.x;
        this.previousY = this.y;

        // Vérification des portails (empêche le monstre de traverser certains points spécifiques)
        // Bloque le mouvement vers la gauche au portail de gauche (x = 3, y = 9)
        if (this.x == 3 && this.y == 9 && bestX < this.x) {
            bestX = this.x;  // Empêche le déplacement vers la gauche
        }
        // Bloque le mouvement vers la droite au portail de droite (x = 15, y = 9)
        else if (this.x == 15 && this.y == 9 && bestX > this.x) {
            bestX = this.x;  // Empêche le déplacement vers la droite
        }

        // Si le meilleur mouvement n'est pas valide (le monstre reste à sa position actuelle),
        // il essaie de se déplacer de manière aléatoire.
        if (bestX == this.x && bestY == this.y) {
            autonomousMove(board);  // Déplacement aléatoire si aucune direction viable
        } else {
            // Sinon, le monstre se déplace vers la direction qui maximise la distance avec Pacman
            if (board.canMove(bestX, bestY)) {
                this.x = bestX;  // Mise à jour de la position X du monstre
                this.y = bestY;  // Mise à jour de la position Y du monstre
            }
        }
    }

    /**
     * Se déplace de manière autonome en choisissant une direction aléatoire parmi les directions valides.
     * Les directions valides sont celles qui ne mènent pas à un mur et qui ne représentent pas un retour immédiat
     * à la position précédente. Si aucune direction valide n'est trouvée, le monstre reste immobile.
     * 
     * @param board La représentation du plateau de jeu. Utilisé pour vérifier la validité des mouvements.
     */
    private void autonomousMove(Board board) {   
        int[] directions = {0, 1, 2, 3};	// Tableau des directions possibles : 0 (haut), 1 (droite), 2 (bas), 3 (gauche).
        Random rand = new Random();			// Générateur de nombres aléatoires pour sélectionner une direction valide.
        ArrayList<Integer> validDirections = new ArrayList<>();	// Liste des directions valides pour le déplacement.

        // Parcourt chaque direction pour vérifier si elle est valide.
        for (int direction : directions) {
            int newX = this.x; 	// Position potentielle x après le mouvement.
            int newY = this.y; 	// Position potentielle y après le mouvement.

            // Calcule les nouvelles coordonnées en fonction de la direction.
            switch (direction) {
                case 0: 
                    newY--; // Mouvement vers le haut.
                    break;
                case 1: 
                    newX++; // Mouvement vers la droite.
                    break;
                case 2: 
                    newY++; // Mouvement vers le bas.
                    break;
                case 3: 
                    newX--; // Mouvement vers la gauche.
                    break;
            }

            /**
             * Vérifie si le déplacement est possible :
             * - La nouvelle position ne doit pas être un mur (utilise board.canMove).
             * - La direction ne doit pas être l'opposé de la direction précédente
             *   (évite les allers-retours inutiles).
             */
            if (board.canMove(newX, newY) && direction != (lastDirection + 2) % 4) {
                validDirections.add(direction); // Ajoute cette direction à la liste des mouvements valides.
            }
        }

        // Si des directions valides sont disponibles, en choisit une au hasard et effectue le mouvement.
        if (!validDirections.isEmpty()) {
            int direction = validDirections.get(rand.nextInt(validDirections.size()));
            move(direction, board); // Exécute le mouvement dans la direction choisie.
            lastDirection = direction; // Met à jour la dernière direction.
        }
    }

    // TODO: Patrouiller dans une zone définie.
    private void patrol(Board board) {
        Random rand = new Random();
        int[] directions = {-1, 0, 1};
        
        int dx = directions[rand.nextInt(3)];
        int dy = directions[rand.nextInt(3)];

        int newX = this.x + dx;
        int newY = this.y + dy;

        if (board.canMove(newX, newY)) {
            this.x = newX;
            this.y = newY;
        }
    }

    /**
     * Met à jour les coordonnées du monstre en fonction de la direction choisie. 
     * Prend en compte les conditions spécifiques pour les positions spéciales 
     * (téléportation sur les bords du plateau).
     * 
     * @param direction La direction du mouvement : 
     *                  0 (haut), 1 (droite), 2 (bas), 3 (gauche).
     * @param board     La représentation du plateau de jeu. Actuellement inutilisé 
     *                  mais peut servir pour des vérifications futures.
     */
    private void move(int direction, Board board) {
        // Initialise les nouvelles coordonnées avec la position actuelle.
        int newX = this.x;
        int newY = this.y;

        /**
         * Calcule les nouvelles coordonnées en fonction de la direction donnée :
         * - 0 : Déplacement vers le haut.
         * - 1 : Déplacement vers la droite (avec gestion de la téléportation à droite).
         * - 2 : Déplacement vers le bas.
         * - 3 : Déplacement vers la gauche (avec gestion de la téléportation à gauche).
         */
        switch (direction) {
            case 0: 
                newY--; 	// Déplacement vers le haut.
                break;
            case 2: 
                newY++; 	// Déplacement vers le bas.
                break;
            case 1: 
                // Si à la bordure droite (18, 9), téléporte à gauche (0, 9).
                if (newX == 18 && newY == 9) {
                    newX = 0; 
                } else {
                    newX++; // Déplacement vers la droite.
                }
                break;
            case 3: 
                // Si à la bordure gauche (0, 9), téléporte à droite (18, 9).
                if (newX == 0 && newY == 9) {
                    newX = 18;
                } else {
                    newX--; // Déplacement vers la gauche.
                }
                break;
            default: 
                break; 		// Aucun changement pour une direction invalide.
        }

        // Met à jour les coordonnées du monstre avec les nouvelles valeurs calculées.
        this.x = newX;
        this.y = newY;

        /**
         * Vérifie si le monstre se trouve dans l'une des positions de téléportation
         * (0, 9) ou (18, 9). Applique la logique de téléportation si nécessaire :
         * - Si le monstre se déplace vers la droite depuis (18, 9), il est téléporté à (0, 9).
         * - Si le monstre se déplace vers la gauche depuis (0, 9), il est téléporté à (18, 9).
         */
        if ((this.x == 0 && this.y == 9) || (this.x == 18 && this.y == 9)) {
            if (direction == 1 && this.x == 18) {
                this.x = 0; 	// Téléportation de droite à gauche.
            } else if (direction == 3 && this.x == 0) {
                this.x = 18; 	// Téléportation de gauche à droite.
            }
        }
    }
}
