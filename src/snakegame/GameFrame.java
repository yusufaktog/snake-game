package snakegame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.JFrame;


/**
 *
 * @author joseph
 */
public class GameFrame extends JFrame{
    static GameFrame mainFrame;

    final void loadPreferences(){
        this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }


    public GameFrame() {
        loadPreferences();
    }
    public static void startNewGame(){        
        Food.foodsCreated = 0;
        GamePanel.GAME_SPEED = GamePanel.INITIAL_GAME_SPEED;
        mainFrame = new GameFrame();
    }
    public static void main(String[] args) {
        startNewGame();
    }
}
