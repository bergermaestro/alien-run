/* Respnsible for both collidable (tree stumps) and non-collidable obstacles (foreground/background) */
import java.awt.Image;
import javax.swing.ImageIcon;

public class Obstacle {

    double dx;
    double dy;
    int x;
    int y;
    private int width;
    private int height;
    private Image image;
    String iconPath;

    Obstacle()
    {
        loadImage();
    }

    Obstacle(String path) 
    {
        iconPath = path;
        loadImage();
    }

    Obstacle(int xcoord, int ycoord, String path)
    {
        iconPath = path;
        x = xcoord;
        y = ycoord;
        loadImage();
    }

    
    Obstacle(int xcoord, int ycoord, double dx, double dy, String path)
    {
        iconPath = path;
        x = xcoord;
        y = ycoord;
        this.dx = dx;
        this.dy = dy;
        loadImage();
    }

    public void reset()
    {
        dx = 0;
        dy = 0;
        x = 50;
        y = 300;
    }

    //no obstacles need to have their images changed, hence this simpler loadImage method
    private void loadImage() {
        try
        {
            ImageIcon ii = new ImageIcon(getClass().getResource(iconPath));
            image = ii.getImage(); 
        }
        catch(Exception error)
        {
            System.out.println(error);
        }

        
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public void move() {
        
        x += dx;
        y += dy;
    }

    //standard getter and setter methods

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public int getWidth() 
    {    
        return width;
    }
    
    public int getHeight() 
    {    
        return height;
    }    

    public Image getImage() {
        
        return image;
    }
}