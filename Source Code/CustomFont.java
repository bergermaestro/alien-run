/* Class created to use a custom font */

import java.awt.*;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class CustomFont {

    private Font customFont;

    CustomFont(float size)
    {
            try {
                //creates the font to use. Size variable allows it to be differently sized per instance
                customFont = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("righteous.ttf")).deriveFont(size);
                
                //register the font within the GraphicsEnvironment
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
    
            } catch (IOException e) {
                e.printStackTrace();

            } catch(FontFormatException e) {
                e.printStackTrace();
            }
        
    }

    public Font getFont()
    {
        return customFont;
    }
}
