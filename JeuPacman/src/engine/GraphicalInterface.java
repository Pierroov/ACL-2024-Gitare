package engine;


import javax.swing.*;

import model.PacmanController;
import model.PacmanGame;
import model.PacmanPainter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * interface graphique avec son controller et son afficheur
 *
 */
public class GraphicalInterface  {

	/**
	 * le Panel pour l'afficheur
	 */
	private DrawingPanel panel;
	private JButton retryButton;
    private JFrame frame;
	
	/**
	 * la construction de l'interface graphique: JFrame avec panel pour le game
	 * 
	 * @param gamePainter l'afficheur a utiliser dans le moteur
	 * @param gameController l'afficheur a utiliser dans le moteur
	 * 
	 */
	public GraphicalInterface(GamePainter gamePainter, GameController gameController, Game game){
 
		JFrame f=new JFrame("Jeu de Pac-Man");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// attacher le panel contenant l'afficheur du game
		this.panel=new DrawingPanel(gamePainter);
		f.setContentPane(this.panel);
		
		// attacher controller au panel du game
		this.panel.addKeyListener(gameController);	
		
        retryButton = new JButton("Retry");
        retryButton.setEnabled(false); // Désactivé par défaut, car la partie n'est pas terminée
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action pour redémarrer le jeu
                resetGame();
            }
        });

        // Ajouter le bouton en bas de l'écran
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(retryButton);
        f.add(buttonPanel, BorderLayout.SOUTH);
        
		f.pack();
		f.setVisible(true);
		f.getContentPane().setFocusable(true);
		f.getContentPane().requestFocus();
	}
	
	/**
	 * mise a jour du dessin
	 */
	public void paint() {
		this.panel.drawGame();	
	}
	
    public void resetGame() {
    	if (frame != null) {
            frame.dispose();
        }
    	PacmanGame game = new PacmanGame(19, 21);
		PacmanPainter painter = new PacmanPainter(game);
		PacmanController controller = new PacmanController();

		// Iniciar o motor gráfico do jogo
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		try {
			engine.run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	

	    /**
	     * Afficher ou masquer le bouton Retry selon l'état du jeu
	     * 
	     * @param gameOver vrai si le jeu est terminé, sinon faux
	     */
	    public void setRetryButtonVisible(boolean gameOver) {
	        retryButton.setEnabled(gameOver);
	    }
	    public JFrame getFrame() {
	        return frame;
	    }
	
}
