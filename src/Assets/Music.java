package Assets;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import Game.Game;

public class Music {
    private AudioClip backgroundMusic; // music playing in the background

    public Music(){
        initMusic();
    }

    public void initMusic() { // method to load background music
        URL url;
        if (Game.getMusicOption()) {
            url = this.getClass().getResource("/backgroundMusic.wav"); // url to background Music file
        } else {
            url = this.getClass().getResource("/soundoff.wav"); // url to background Music file
        }
        backgroundMusic = Applet.newAudioClip(url); // background music object

    }
    public void music(boolean on){
        if(on){
            backgroundMusic.play();
        }
        else{
            backgroundMusic.stop();
        }
    }
}
