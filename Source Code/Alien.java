/* Responsible for the alien character */

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

//alien sprite from this website https://opengameart.org/content/alien-2d-sprites

public class Alien {

    double dx = 0;
    double dy = 0;
    int x = 50;
    int y = 300;
    private int width; //width and height are private because these are fields that should not be changed
    private int height;
    private ImageIcon alien;
    private Image image;
    

    long pastClick = 0;

    public Alien() 
    {
        changeImage("alien-1.png");
    }

    public void changeImage(String filename)
    {
        alien = new ImageIcon(getClass().getResource(filename));
        image = alien.getImage(); 
        
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public void changeImage(String file1, String file2, int xcoord) //this method is used to swap between images in order to give the impression of running
    {
        //Depending on the speed of the alien, they will not touch every single square. Ex: on one run of the gameLoop, they may move from square 15 straight to 20. This is why %15 and %30 are used for the first speed, while the others are used for the second speed (occurs after distance of 185)
        if(xcoord % 15 == 0 && xcoord % 30 == 0)
        {
            changeImage(file1);
        }
        else if(xcoord % 15 == 0)
        {
            changeImage(file2);
        }
        else if((xcoord + 11) % 12 == 0 && (xcoord + 11) % 24 == 0)
        {
            changeImage(file1);
        }
        else if((xcoord + 11) % 12 == 0)
        {
            changeImage(file2);
        }
    }

    public void move() {
        
        x += dx;
        y += dy;
    }

    //standard getter and setter methods
    
    public int getWidth() 
    {    
        return width;
    }
    
    public int getHeight() 
    {    
        return height;
    }    

    public Image getImage() 
    {
        return image;
    }

    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) { //if the spacebar is placed it starts to move the alien up, then the game loop handles moving them back down once they reach a certain y value

            if(y == 300)
            {
                dy = -4;
                dx = 0;
                new Music().playMusic("jump.wav", false);
            }

        }
    }

    public void keyReleased(KeyEvent e) 
    {
    }
}