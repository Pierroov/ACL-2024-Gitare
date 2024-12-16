package model;

/**
 * Classe représentant un noeud dans le tableau pour l'algorithme A*.
 * Chaque noeud contient des coordonnées, des coûts associés, et une référence au noeud parent.
 */
class Node implements Comparable<Node> {
    private int x, y; 		// Coordonnées du noeud
    private int gCost; 		// Coût de déplacement depuis le point de départ
    private int hCost; 		// Estimation du coût pour atteindre la destination
    private Node parent; 	// Référence au nœud précédent dans le chemin

    public Node(int x, int y, int gCost, int hCost, Node parent) {
        this.x = x;
        this.y = y;
        this.gCost = gCost;
        this.hCost = hCost;
        this.parent = parent;
    }
    
    // Retourne le parent
    public Node getParent() { return this.parent; }
    
    // Retourne le coût G
    public int getFCost() {
        return this.gCost + this.hCost;
    }
    
    // Retourne le coût total F = G + H
    public int getGCost() { return this.gCost; }
    
    /**
     * Retourne la position actuelle sur l'axe x. 
     * @return La position x.
     */
    public int getX() { return this.x; }

    /**
     * Retourne la position actuelle sur l'axe y.
     * @return La position y.
     */
    public int getY() { return this.y; }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getFCost(), other.getFCost());
    }
}
