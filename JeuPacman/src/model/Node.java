package model;

/**
 * Classe représentant un noeud dans le tableau pour l'algorithme A*.
 * Chaque noeud contient des coordonnées, des coûts associés, et une référence au noeud parent.
 */
class Node implements Comparable<Node> {
    int x, y; 		// Coordonnées du noeud
    int gCost; 		// Coût de déplacement depuis le point de départ
    int hCost; 		// Estimation du coût pour atteindre la destination
    Node parent; 	// Référence au nœud précédent dans le chemin

    public Node(int x, int y, int gCost, int hCost, Node parent) {
        this.x = x;
        this.y = y;
        this.gCost = gCost;
        this.hCost = hCost;
        this.parent = parent;
    }

    // Retourne le coût total F = G + H
    public int getFCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getFCost(), other.getFCost());
    }
}
