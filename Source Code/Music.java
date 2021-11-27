// A custom class to deal with playing music

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {

    File musicPath;
    AudioInputStream audioInput;
    Clip clip;

    void playMusic(String musicLocation, boolean loop)
    {
        try
        {
            URL musicPathURL = getClass().getResource(musicLocation); //a URL had to be used in order to put things into a Jar file

            audioInput = AudioSystem.getAudioInputStream(musicPathURL);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            if(loop == true) //loop is used to play the backgroundmusic over and over
            {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }   
    void stopMusic()
    {
        clip.stop();
    }
}
