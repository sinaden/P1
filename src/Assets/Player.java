package Assets;
import javax.imageio.ImageIO;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import Game.Game;

public class Player extends Sprite {

    private final int jumpSpeed = -15;
    private final int fallingSpeed = 10;
    private final int floorLevel = 60;
    private int standingOrDucking = 0;  //variable for Array list to determine which sprite to use
    private int animationFrame = 0;     //^
    private final ArrayList<ArrayList<Image>> imageList = new ArrayList<>();  //Array list with all frames of animation for player
    private final ArrayList<Image> standingSprites = new ArrayList<>();   //Array list with all standing sprites
    private final ArrayList<Image> duckingSprites = new ArrayList<>();    //Array list with all ducking sprites
    private final ArrayList<Image> jumpingSprites = new ArrayList<>();
    private boolean jumpKey;    //check if jump key is pressed
    private boolean jumpAvailable;  //checks if player landed after the last jump
    private URL jumpUrl;
    AudioClip jumpSound;
    private int standingHeight;
    private int duckingHeight;
    private final int gameHeight = Game.getWindowHeight();
    private final int gameWidth = Game.getWindowWidth();
    boolean standingUP;


    public Player(){

        super(0, 0); //starting x and y position of the player

        setPlayerSound();

        initPlayer();

        x = gameWidth/3;
        y = gameHeight-floorLevel-standingHeight;

    }

    private void initPlayer(){

        BufferedImage ii;
        URL url;
        String urlS;


        for(int i = 0; i<43; i++){   //loop for standing loading images to list, right now there is only one picture but the loop is ready for the future

            try {
                if (i < 10)
                    urlS ="/player/run/run_00" + i + ".png";
                else
                    urlS ="/player/run/run_0" + i + ".png";
                url = this.getClass().getResource(urlS);
                ii = ImageIO.read(url);
                standingSprites.add(ii);

            }
            catch (Exception ignored){

            }
        }
        standingHeight = standingSprites.get(0).getHeight(null);


        for(int i = 8; i<20; i++){   //loop for loading ducking images to list

            try {
                if (i < 10)
                    urlS = "/player/slide/slide_0" + i + ".png";
                else
                    urlS = "/player/slide/slide_" + i + ".png";
                url = this.getClass().getResource(urlS);
                ii = ImageIO.read(url);
                duckingSprites.add(ii);
            }
            catch (Exception ignored){

            }
        }

            for (int i = 0; i < 37; i++) {
                try {
                    if (i < 10)
                        urlS = "/player/jump/jump_00" + i + ".png";
                    else
                        urlS = "/player/jump/jump_0" + i + ".png";
                    url = this.getClass().getResource(urlS);
                    ii = ImageIO.read(url);
                    jumpingSprites.add(ii);
                }
                catch (Exception ignored){

            }
        }

        duckingHeight = duckingSprites.get(4).getHeight(null);

        imageList.add(standingSprites);     //adding 2 lists to imageList list
        imageList.add(duckingSprites);
        imageList.add(jumpingSprites);
        image = imageList.get(standingOrDucking).get(animationFrame); //setting the first frame of standing player to image variable

    }

    public void setPlayerSound(){

        if(Game.getSoundOption()){
            jumpUrl  = this.getClass().getResource("/jump.wav"); //URL address to jump sound
        }
        else{
            jumpUrl = this.getClass().getResource("/soundoff.wav"); //URL address to jump sound
        }
        jumpSound = Applet.newAudioClip(jumpUrl);     //jump audio clip
    }

    private void setImage(){     //sets new image from Array list for each frame

        image = imageList.get(standingOrDucking).get(animationFrame);
        getImageDimensions();
    }

    public void animationFrame(){   //changes animationFrame variable each frame

        switch(standingOrDucking){
            case 0:
                if(animationFrame<imageList.get(0).size()-1){
                    animationFrame++;

                }
                else{
                    animationFrame=0;
                }
                break;
            case 1:
                if(animationFrame<7){
                    if(animationFrame>=0&&animationFrame<3){
                        y += (standingHeight-duckingHeight)/3;
                    }
                    animationFrame++;
                }
                else if(animationFrame==7){
                    animationFrame=3;
                }
                if(standingUP){
                    if(animationFrame<7){
                        animationFrame=7;
                    }
                    if(animationFrame<11)
                    y -= (standingHeight-duckingHeight)/3;
                    animationFrame++;
                    if(animationFrame==11){
                        standingUP=false;
                        standingOrDucking=0;
                        animationFrame=0;
                    }
                }
                break;
            case 2:
                if(animationFrame<imageList.get(2).size()-1){
                    animationFrame++;

                }
                else{
                    standingOrDucking=0;
                    animationFrame=0;
                }
                if(y >= gameHeight-floorLevel-standingHeight){
                    standingOrDucking=0;
                    animationFrame=0;
                }

                break;
        }
        setImage();
    }

    public void move(){     //changes y position of player

        if(jumpKey&& jumpAvailable){    //jump key is pressed and highest jump position is not reached
            y += jumpSpeed;
        }
        else if(y < gameHeight-floorLevel-standingHeight){   //else player will start falling down
            y += fallingSpeed;
        }
        if(standingOrDucking==0 && y>gameHeight-floorLevel-standingHeight){
            y = gameHeight-floorLevel-standingHeight;
        }
        if(y == gameHeight-floorLevel-standingHeight){       //if player touches the ground he can jump again
            jumpAvailable =true;
        }
        if(y <= gameHeight-floorLevel-(standingHeight*2)) {  //if player reaches highest point of jump
            jumpAvailable = false;

        }
    }


    public void obstacleHit(){  //player is moved 40 pixels back if he hits the obstacle

        x-=40;
    }

    public void powerup(){
        if(x<gameWidth/2){
            x+=40;
        }
    }

    public void keyPressed(KeyEvent e){

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP && jumpAvailable && standingOrDucking == 0 && isVisible()){ //up key pressed
            jumpKey = true;
            jumpSound.play();
            standingOrDucking = 2;
            animationFrame=0;
        }

        if (key == KeyEvent.VK_DOWN){ //down key pressed
            if(jumpAvailable && y == gameHeight-floorLevel-standingHeight && standingOrDucking==0){
                standingOrDucking = 1;
                animationFrame=0;
                setImage();

            }
        }

    }


    public void keyReleased(KeyEvent e){

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP ){ //up key released

            jumpKey = false;
            jumpAvailable = false;
        }

        if (key == KeyEvent.VK_DOWN){ //down key released
            if(jumpAvailable && standingOrDucking==1){
                standingUP=true;
            }

        }
    }

}
