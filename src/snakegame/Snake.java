package snakegame;


import java.awt.event.KeyEvent;


public class Snake {
    private int direction;
    private boolean isAlive;
    final int initialBodyLength;
    private int bodyLength;
    private final int[] snakeX,snakeY;


    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }
    public Snake() {
        snakeX = new int[GamePanel.GAME_UNITS];
        snakeY = new int[GamePanel.GAME_UNITS];
        initialBodyLength = 6;
        bodyLength = initialBodyLength;
        direction = KeyEvent.VK_RIGHT;     
        isAlive = true;

    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int[] getSnakeX() {
        return snakeX;
    }

    public int[] getSnakeY() {
        return snakeY;
    }
    
    
}
