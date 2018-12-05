package Assets;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import Game.Game;

public class Sound {
    private AudioClip damageSound;   //audio of damage sound
    private AudioClip dangerSound;  //audio of danger sound
    private AudioClip gameOverSound;//audio of game over screen
    private AudioClip congratsSound; //audio of when you finish a level


    public Sound() {  //method to load all game sounds

        initSounds();
    }
    public void initSounds(){
        if (Game.getSoundOption()) {

            URL url = this.getClass().getResource("/damage.wav");   //url to damage sound
            damageSound = Applet.newAudioClip(url);     //damage sound object

            url = this.getClass().getResource("/danger.wav");
            dangerSound = Applet.newAudioClip(url);

            url = this.getClass().getResource("/gameover.wav");
            gameOverSound = Applet.newAudioClip(url);

            url = this.getClass().getResource("/congratsSound.wav");
            congratsSound = Applet.newAudioClip(url);
        } else {
            URL url = this.getClass().getResource("/soundoff.wav");
            damageSound = Applet.newAudioClip(url);     //damage sound object

            dangerSound = Applet.newAudioClip(url);

            gameOverSound = Applet.newAudioClip(url);

            congratsSound = Applet.newAudioClip(url);
        }
    }
    public void damage(boolean on){
        if(on){
            damageSound.play();
        }
        else{
            damageSound.stop();
        }
    }
    public void danger(boolean on){
        if(on){
            dangerSound.play();
        }
        else{
            dangerSound.stop();
        }
    }
    public void gameover(boolean on){
        if(on){
            gameOverSound.play();
        }
        else{
            gameOverSound.stop();
        }
    }
    public void congrats(boolean on){
        if(on){
            congratsSound.play();
        }
        else{
            congratsSound.stop();
        }
    }
}
