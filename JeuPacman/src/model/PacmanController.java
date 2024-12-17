package model;

import java.awt.event.KeyEvent;

import engine.Cmd;
import engine.GameController;


/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * controleur de type KeyListener
 * 
 */
public class PacmanController implements GameController {

	/**
	 * commande en cours
	 */
	public Cmd commandeEnCours;
	public Cmd lastCommand;
	
	/**
	 * construction du controleur par defaut le controleur n'a pas de commande
	 */
	public PacmanController() {
		this.commandeEnCours = Cmd.IDLE;
	}

	/**
	 * quand on demande les commandes, le controleur retourne la commande en
	 * cours
	 * 
	 * @return commande faite par le joueur
	 */
	public Cmd getCommand() {
		return this.commandeEnCours;
	}

	@Override
	/**
	 * met a jour les commandes en fonctions des touches appuyees
	 */
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			this.commandeEnCours = Cmd.LEFT;
			this.lastCommand = Cmd.LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			this.commandeEnCours = Cmd.RIGHT;
			this.lastCommand = Cmd.RIGHT;

			break;
		case KeyEvent.VK_UP:
			this.commandeEnCours = Cmd.UP;
			this.lastCommand = Cmd.UP;

			break;
		case KeyEvent.VK_DOWN:
			this.commandeEnCours = Cmd.DOWN;
			this.lastCommand = Cmd.DOWN;

			break;
		}

	}

	@Override
	/**
	 * met a jour les commandes quand le joueur relache une touche
	 */
	public void keyReleased(KeyEvent e) {
		this.commandeEnCours = this.lastCommand;
	}

	@Override
	/**
	 * ne fait rien
	 */
	public void keyTyped(KeyEvent e) {

	}

}
