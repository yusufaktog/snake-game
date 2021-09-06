package snakegame;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    Snake snake;
    Timer timer;
    Random random;
    Food food;

    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = 1000;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE*UNIT_SIZE);
    static final int INITIAL_GAME_SPEED = 50;
    static int GAME_SPEED = INITIAL_GAME_SPEED;

    final void loadPreferences() {

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

    }

    public GamePanel() {
        snake = new Snake();
        food = new Food();
        random = new Random();
        loadPreferences();
        startTheGame();

    }

    public void createFood() {
        if (food.isEaten()) {
            int posX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            int posY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
            food.setPosX(posX);
            food.setPosY(posY);
            food.setColor(new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
            Food.foodsCreated++;
 
                
        }
        food.setEaten(false);

    }
    private void changeDirection(){
        int length = snake.getBodyLength();
        
        int temp = snake.getSnakeX()[0];
        snake.getSnakeX()[0] = snake.getSnakeX()[length-1];
        snake.getSnakeX()[length-1] = temp;
        
        int temp2 = snake.getSnakeY()[0];
        snake.getSnakeY()[0] = snake.getSnakeY()[length-1];
        snake.getSnakeY()[length-1] = temp2;
        

        switch (snake.getDirection()) {
            case 37:
                snake.setDirection(39);
                break;
            case 39:
                snake.setDirection(37);
                break;
            case 38:
                snake.setDirection(40);
                break;
            case 40:
                snake.setDirection(38);
                break;
            default:
                break;
        }           
        
 
    }
    
    final void startTheGame() {
        createFood();        
        timer = new Timer(GAME_SPEED, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void drawFood(Graphics g, Color color) {
        g.setColor(color);
        g.fillOval(food.getPosX(), food.getPosY(), UNIT_SIZE, UNIT_SIZE);
    }

    public void draw(Graphics g) {
        if (snake.isAlive()) {
            Font font = new Font("Ink Free",Font.ITALIC,50);
            Font smallFont = new Font("Helvetica",Font.ROMAN_BASELINE,SCREEN_WIDTH/50);
            drawStatusInfo(font,Color.MAGENTA,g,"Score",Food.foodsCreated ,SCREEN_WIDTH/2, font.getSize() );            
            drawStatusInfo(smallFont,Color.LIGHT_GRAY,g,"Pause:Esc",SCREEN_WIDTH - g.getFontMetrics().stringWidth("to Pause: Esc")/2 ,SCREEN_HEIGHT - smallFont.getSize() );
            drawStatusInfo(smallFont,Color.LIGHT_GRAY,g,"Reverse Direction: Space",SCREEN_WIDTH - (g.getFontMetrics().stringWidth("Reverse Direction: Space")),SCREEN_HEIGHT - smallFont.getSize()*2);
            drawFood(g, food.getColor());
            g.setColor(Color.red);
            for (int i = 0; i < snake.getBodyLength(); i++) {
                if (i == 0) {
                    g.setColor(food.getColor());
                    g.fillRect(snake.getSnakeX()[i], snake.getSnakeY()[i], UNIT_SIZE, UNIT_SIZE);
                } else{
                    g.setColor(Color.RED);
                    g.fillRect(snake.getSnakeX()[i], snake.getSnakeY()[i], UNIT_SIZE, UNIT_SIZE);
                    
                    g.setColor(Color.BLACK);
                    if(snake.getDirection() == 37 || snake.getDirection() == 39)
                        g.drawLine(snake.getSnakeX()[i], snake.getSnakeY()[i], snake.getSnakeX()[i] , snake.getSnakeY()[i]+ UNIT_SIZE);
                    else if(snake.getDirection() == 38 || snake.getDirection() == 40)
                        g.drawLine(snake.getSnakeX()[i], snake.getSnakeY()[i], snake.getSnakeX()[i] + UNIT_SIZE , snake.getSnakeY()[i]);
                        
                    //System.out.println(snake.getSnakeX()[i] + " , " + snake.getSnakeY()[i]);
                }
            }
        } else {
            gameOver(g);
        }

    }

    public void move() {
        for (int i = snake.getBodyLength(); i > 0; i--) {
            snake.getSnakeX()[i] = snake.getSnakeX()[i - 1];
            snake.getSnakeY()[i] = snake.getSnakeY()[i - 1];

        }
        switch (snake.getDirection()) {
            case 37://LEFT_ARROW                
                snake.getSnakeX()[0] = snake.getSnakeX()[0] - UNIT_SIZE;
                break;
            case 38://UP_ARROW                
                snake.getSnakeY()[0] = snake.getSnakeY()[0] - UNIT_SIZE;
                break;
            case 39://RIGHT_ARROW                
                snake.getSnakeX()[0] = snake.getSnakeX()[0] + UNIT_SIZE;
                break;
            case 40://DOWN_ARROW                
                snake.getSnakeY()[0] = snake.getSnakeY()[0] + UNIT_SIZE;
                break;
            default:
                break;
        }

    }

    public void eatFood() {
        if (food.getPosX() == snake.getSnakeX()[0] && food.getPosY() == snake.getSnakeY()[0]) {
            snake.setBodyLength(snake.getBodyLength() + 1);
            food.setEaten(true);
            createFood();

        }

    }

    public void checkCollisions() {
        //checks if head collides with body
        for (int i = snake.getBodyLength(); i > 0; i--) {
            if ((snake.getSnakeX()[0] == snake.getSnakeX()[i]) && (snake.getSnakeY()[0] == snake.getSnakeY()[i])){
                snake.setIsAlive(false);
            }
        }
        //check if head touches left border
        if (snake.getSnakeX()[0] < 0 || snake.getSnakeY()[0] < 0) {
            snake.setIsAlive(false);
        }
        //check if head touches right border
        if (snake.getSnakeX()[0] > SCREEN_WIDTH || snake.getSnakeX()[0] > SCREEN_WIDTH){
            snake.setIsAlive(false);
        }
        //check if head touches top border
        if (snake.getSnakeY()[0] < 0 || snake.getSnakeY()[0] < 0){
            snake.setIsAlive(false);
        }
        //check if head touches bottom border
        if (snake.getSnakeY()[0] > SCREEN_HEIGHT || snake.getSnakeX()[0] > SCREEN_WIDTH){
            snake.setIsAlive(false);
        }
        if (!snake.isAlive()) {
            timer.stop();
        }

    }

    public void drawStatusInfo(Font font,Color color,Graphics g,String statusName,int info,int posX,int posY){
        g.setColor(color);
        g.setFont(font);
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(statusName+":" + info, posX - metrics.stringWidth( statusName + String.valueOf(info) )/2, posY );
    }
    
    public void drawStatusInfo(Font font,Color color,Graphics g,String statusName,int posX,int posY){
        g.setColor(color);
        g.setFont(font);
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(statusName,posX , posY);
    }


    public void gameOver(Graphics g) { //new Font("Ink Free", Font.BOLD, 40)
        drawStatusInfo(new Font("Ink Free", Font.BOLD, SCREEN_WIDTH/6),Color.ORANGE,g,"Game Over",SCREEN_WIDTH/12,SCREEN_HEIGHT/4);
        drawStatusInfo(new Font("Ink Free", Font.BOLD, SCREEN_WIDTH/12),Color.LIGHT_GRAY,g,"Foods Eaten",Food.foodsCreated,SCREEN_WIDTH/2,SCREEN_HEIGHT/2);
        drawStatusInfo(new Font("INK FREE", Font.ITALIC, SCREEN_WIDTH/15),Color.RED,g,"to Restart-->R",(int)(SCREEN_WIDTH/3.4),(int)(SCREEN_HEIGHT/1.5));
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        checkCollisions();
        createFood();
        eatFood();
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case 37://LEFT_ARROW
                    if (snake.getDirection() != 39) {
                        snake.setDirection(37);
                    }
                    break;

                case 38://UP_ARROW
                    if (snake.getDirection() != 40) {
                        snake.setDirection(38);
                    }
                    break;

                case 39://RIGHT_ARROW
                    if (snake.getDirection() != 37) {
                        snake.setDirection(39);
                    }
                    break;

                case 40://DOWN_ARROW
                    if (snake.getDirection() != 38) {
                        snake.setDirection(40);
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    timer.stop();
                    if(JOptionPane.showConfirmDialog(GamePanel.this, "Continue?") == 0)
                        timer.start();
                    else
                        System.exit(0);                        
                    break;
                case KeyEvent.VK_ENTER:
                    timer.start();
                    break;
                case KeyEvent.VK_R:
                    GameFrame.mainFrame.dispose();
                    GameFrame.startNewGame();
                    break;
                case KeyEvent.VK_SPACE:
                    changeDirection();
                    break;
                default:
                    break;

            }

        }

    }

}
