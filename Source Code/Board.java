/* Responsible for the Board on which the game is played */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.util.Random;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    private final int DELAY = 10; //used for timer
    private final int BOTTOM = 300; //used to position the elements 
    private final static int OBSTACLE_NUM = 10;//sets how many obstacles there will be as a part of the initial generation

    static Timer timer;
    static Alien alien;
    Obstacle distance;
    static ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(); // Create an ArrayList object
    Obstacle foreground, foreground2, background;
    static Music backgroundMusic;
    int spacing2 = 170;
    double currentSpeed = -3.0;

    private Random r = new Random();

    public Board() {

        addKeyListener(new TAdapter()); //allows keystrokes to be registered
        setBackground(Color.black); //fallback background in case the image is not found
	    setFocusable(true); //allows keystrokes to act on this window

        setupGame();
    }

    //creates the objects needed to run the game
    public void setupGame()
    {
        alien = new Alien();
        distance = new Obstacle(0,0, 2.0, 0, "empty.png"); //this is an empty obstacle placed at the top of the board that measures the distance that has been run

        obstacleGeneration(OBSTACLE_NUM);   //stump generation

        background = new Obstacle(0, -400, currentSpeed + 2, 0, "background.png");
        foreground = new Obstacle(0, -405, currentSpeed, 0, "foreground.png");
        foreground2 = new Obstacle(foreground.getWidth(), -405, currentSpeed, 0, "foreground.png");

        timer = new Timer(DELAY, this);

        backgroundMusic = new Music();
        backgroundMusic.playMusic("bg-music.wav", true);
    }

    public void obstacleGeneration(int amount) //used for initial obstacle generation
    {
        int lastLocation = 150;
        int spacing = 200;

        for(int i = 0; i < amount + 1; i++)
        {

            lastLocation += r.nextInt(100) + spacing;
            obstacles.add(new Obstacle(lastLocation, BOTTOM, currentSpeed, 0, "stump.png"));
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g; //layer stacking goes from back to front

        //draws on the background and foreground
        g2d.drawImage(background.getImage(), background.x, background.y, this);
        g2d.drawImage(foreground.getImage(), foreground.x, foreground.y, this);
        g2d.drawImage(foreground2.getImage(), foreground2.x, foreground2.y, this);

        //creates and  draws on the scoretext
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON); //antialiases text
        CustomFont customFont = new CustomFont(27f);
        g2d.setColor(Color.white);
        g2d.setFont(customFont.getFont());
        g2d.drawString(distance.x /25 + "", Gui.BOARD_WIDTH - 100, 50);

        //draws on the obstacles
        for(int i = 0; i < obstacles.size(); i++)
        {
          g2d.drawImage(obstacles.get(i).getImage(), obstacles.get(i).x, obstacles.get(i).y, this);           
        }

        //how to make sure there is an infinite number of obstacles
        int nextLocation = obstacles.get(obstacles.size() - 1).x + r.nextInt(110) + spacing2;
        if(obstacles.get(0).x <= -2) //check to see if the obstacle is off the screen, then removes it and adds a new one
        {
            obstacles.remove(0);
            obstacles.add(new Obstacle(nextLocation, BOTTOM, currentSpeed, 0, "stump.png"));
            if(spacing2 > 114)
            {
                spacing2 -= 2;
            }
        }
        
        //speeds up the game once 185 points have been reached
        if(distance.x /25 > 185 && foreground.dx != -4)
        {
            foreground.dx = -4;
            foreground2.dx = -4;
            for(int i = 0; i < obstacles.size(); i++)
            {
              obstacles.get(i).dx = -3.8;   
              currentSpeed = -3.8;   
            }
        }

        //makes the alien look like it is running
        alien.changeImage("alien-1.png", "alien-2.png", foreground.x);

        //draws on the alien
        g2d.drawImage(alien.getImage(), alien.x, alien.y, this);
        
        onJump();

    }

    public void onJump()
    {
        if(alien.y < 250) //once the alien reaches the y coordinate of 250, their velocity is posivity so they once again start to travel back down
        {
            alien.dy = 4.20;
        }

        if(alien.y > 300) //stop them once they are on the ground. This uses a greater than sign because sometimes they move past 300.
        {
            alien.dy = 0.00;
            alien.y = 300;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        //move the layers
        foreground.move();
        foreground2.move();
        background.move();
        alien.move();
        distance.move();

        //loop required to move all the obstacles
        for(int i = 0; i < obstacles.size(); i++)
        {
            obstacles.get(i).move();  
        }

        //repaint the board
        repaint();

        //dealing with collisions
         collissionCheck(distance);


    }  

    public static void collissionCheck(Obstacle distance)
    {
        int i = 0; //only need to check the first element of the arraylist because after they dissapear they are removed
        boolean xCollission = alien.x + 8 >= obstacles.get(i).x && alien.x + 8 <= (obstacles.get(i).x + obstacles.get(i).getWidth());
        boolean yCollission = alien.y >= (obstacles.get(i).y - obstacles.get(i).getHeight() + 8) && alien.y <= obstacles.get(i).y;

        if(xCollission == true && yCollission == true)
        {
            new Music().playMusic("gameover.wav", false);
            backgroundMusic.stopMusic();
            alien.changeImage("alien-death-1.png"); //if they are at the same location, it switches the icon and stops the game
            timer.stop(); //stops the game if it hits
            
            gameOverMessage(distance);
        }
    }

    public static void gameOverMessage(Obstacle distance)
    {
        GameOver gameOver = new GameOver(distance.x /25);
        gameOver.setLocationRelativeTo(Gui.board);
        gameOver.setVisible(true);
    }

    private class TAdapter extends KeyAdapter { //this is what detects the keystrokes

        @Override
        public void keyPressed(KeyEvent e) {
            alien.keyPressed(e);
            //System.out.println("key pressed");
        }
    }
}



class GameOver extends JDialog implements ActionListener { //Game Over
    JLabel image;
    JTextField input;
    JButton close;
    JLabel scoreInfo;

    public GameOver(int finalScore) {
        setTitle("Game Over!");
        setSize(400,250);
        //prevent the user from interacting with the main application
        setModal(true);

        Container content = getContentPane();
        content.setLayout(null); //learned you could not set a layout and then position the elements using setBounds() and pixel co-ordinates
        content.setBackground(Color.decode("#151826"));
        
        CustomFont customFont = new CustomFont(14f);

        scoreInfo = new JLabel(); //the text.
        scoreInfo.setText("<html><div style='color: white;'><h1>Game Over!</h1><h2>Your Final Score was " + finalScore + " points. </h2></div></html>");
        scoreInfo.setBounds(25, 10, 250, 100);
        scoreInfo.setFont(customFont.getFont());

        image = new JLabel(); //adds the image on the right hand side - purely aesthetic
        image.setIcon(new ImageIcon(getClass().getResource("alien-large.png")));
        image.setBounds(200,0, 208, 208);

        close = new JButton(); //button to allow the user to close the window 
        close.setText("Close");
        close.addActionListener(this);
        close.setBounds( 25, 150, 75, 25);
        close.setBackground(Color.white);
        close.setBorder(null);

        content.add(image); 
        content.add(scoreInfo); 
        content.add(close); 
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //tells it what to do when it closes
        setResizable(false); 
    }

    public void actionPerformed(ActionEvent e) { 
        Object obj = e.getSource();

        if(obj == close) //if the close button is pressed, get rid of the window
        {
            dispose();
        }
    }
}