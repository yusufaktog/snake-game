package snakegame;


import java.awt.Color;
import java.util.Random;


public class Food {
    private Color Color;
    private int posX,posY;
    static int foodsCreated;
    private boolean eaten;
    

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean isEaten) {
        this.eaten = isEaten;
    }

    public Food() {
        eaten = false;
        this.posX = new Random().nextInt((int) (GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE)) * GamePanel.UNIT_SIZE;
        this.posY = new Random().nextInt((int) (GamePanel.SCREEN_HEIGHT / GamePanel.UNIT_SIZE)) * GamePanel.UNIT_SIZE;
        this.Color = new Color(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255));
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    public Color getColor() {
        return Color;
    }

    public void setColor(Color Color) {
        this.Color = Color;
    }
     

}
