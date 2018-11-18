import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements ActionListener {

private static int windowWidth = 1024;  //window's width
private static int windowHeight = 720;  //window's height
private final int DELAY = 16;   //delay between each frame
private int levelTime = 60000;  //default time for level, will be multiplied by levelNum
private int levelNum = 1;   //number of chosen level
private AudioClip damageSound;   //audio of damage sound
private AudioClip dangerSound;  //audio of danger sound
private AudioClip gameOverSound;//audio of game over screen
private AudioClip congratsSound; //audio of when you finish a level
private AudioClip backgroundMusic; // music playing in the background
private Player player;  //player object
private Shooter shooter;    //shooter object
private int gameState = 0;  //0-game menu, 1-game, 2-game over screen, 3-congrats screen
private ArrayList<Sprite> sprites;
private Timer mainTimer;    //main timer for the game
private int livesNum;
private int levelTimer =0;
private int obstacleTimer =0;
private int shooterTimer =0;
private boolean timersOn;
private int shooterRandomTimer;
private boolean musicOption;
private static boolean soundOption;
private Menu menu; // menu object
private Congrats congrats;
private Shop shop; // shop object
private Options options; // options object
private Pause pause; // pause object
public int MenuState = 0;



public Game(){
    initGame();
}

private void initGame(){

    addKeyListener(new TAdapter());
    setFocusable(true);
    setBackground(Color.cyan);  //when we have our background ready it will be deleted
    setDoubleBuffered(true);
    musicOption=true;
    soundOption=true;
    initSounds();
    initBackgroundMusic();


    //creates timer with delay of our DELAY variable,
    // It's the main Timer which should be created separately at the top of the program
    // (Other timers regarding sprites are created at initTime() method
    mainTimer = new Timer(DELAY, this);
    mainTimer.start();  //starts mainTimer



    if(gameState < 2){   //Game state 0 (menu) or 1 (actual game)

        menu = new Menu(); // menu object from Menu class is created

        shop = new Shop();

        options = new Options();

        pause = new Pause();

        congrats = new Congrats(); //congrats object created

        levelTime *= levelNum;  //this will make levelTime scale up incredibly fast //time needed to complete level, levelNum will be decided in the menu
        player = new Player();  //creates new Player object

        shooter = new Shooter(0, player.getY());    //creates new Shooter object on the edge of the screen and the same floor level as player

        sprites = new ArrayList<>();
        sprites.add(new Background(0, 0, new Random().nextInt(5)+1));

        shooter = new Shooter(0, player.getY());    //creates new Shooter object on the edge of the
        // screen and the same floor level as player

        initLives();
    }

}


@Override
public void paintComponent(Graphics g){     //draws everything on screen
    super.paintComponent(g);

    if (gameState == 0){

        if (MenuState == 2) {
            drawShop(g);
        }
        if (MenuState == 1) {
            drawOptions(g);
        }
        else {
            drawMenu(g);
        }

    }

    if(gameState == 1){

        drawGame(g);
    }
    if(gameState == 2){

        drawGameOver(g);
    }
    if(gameState == 3){

        drawCongrats(g);
    }
    if (gameState == 4){
        drawPause(g);
    }


    Toolkit.getDefaultToolkit().sync();
}


private void drawMenu(Graphics g) { // draws menu scene

    menu.render(g);


}

private void drawShop(Graphics g) {
    shop.render(g);
}

private void drawOptions(Graphics g) {
    options.render(g);
}

private void drawPause(Graphics g) {
    pause.render(g);
}

private void drawGame(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        //draw Background
        for(Sprite background : sprites){
            if(background instanceof Background) {
                g2d.drawImage(background.getImage(), background.getX(), background.getY(), this);
            }
        }

        //draw BgSprites
   /* for(BgSprite bgSprite : bgSprites){
        g2d.drawImage(bgSprite.getImage(), bgSprite.getX(), bgSprite.getY(), this);
    }*/

        //draw player
        g2d.drawImage(player.getImage(),player.getX(), player.getY(), this);

        //draw shooter
        g2d.drawImage(shooter.getImage(), shooter.getX(), shooter.getY(), this);

        //draw obstacles
        for(Sprite obstacle : sprites){
            if(obstacle instanceof Obstacle) {
                g2d.drawImage(obstacle.getImage(), obstacle.getX(), obstacle.getY(), this);
            }
        }

        //draw lives
        for(Sprite life : sprites){
            if(life instanceof Heart) {
                g2d.drawImage(life.getImage(), life.getX(), life.getY(), this);
            }
        }

        //draw bullets
        for(Sprite bullet : sprites){
            if(bullet instanceof Bullet) {
                g2d.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
            }
        }

        //draw danger
        for(Sprite danger : sprites){
            if(danger instanceof Danger) {
                g2d.drawImage(danger.getImage(), danger.getX(), danger.getY(), this);
            }
        }

        //draw timer
        int tempTime = levelTime;
        g.setColor(Color.black);
        Font small = menu.loadFont();  //creating new font object
        small = small.deriveFont(Font.BOLD, 30);
        g.setFont(small);
        String msg = "Time: " +String.valueOf((tempTime/60))+":"+String.valueOf((tempTime%60));   //time in format M:SS
        FontMetrics fm = getFontMetrics(small);


        g.drawString(msg,  0, fm.getHeight());
}



private void drawGameOver(Graphics g){  //draws game over screen

    String msg = "Game Over";
    Font small = menu.loadFont();  //creating new font object
    small = small.deriveFont(Font.BOLD, 30);
    FontMetrics fm = getFontMetrics(small);     //creating new font metrics object with dimensions of our font
    setBackground(Color.black);

    g.setColor(Color.white);       //set's color of drawing tool to white
    g.setFont(small);       //sets font of drawing tool to our font
    g.drawString(msg, (windowWidth - fm.stringWidth(msg)) / 2, windowHeight / 2);   //draws string msg
    int tempTime = levelTime;
    msg = "Time: " +String.valueOf((tempTime/60))+":"+String.valueOf((tempTime%60));
    g.drawString(msg, (windowWidth - fm.stringWidth(msg)) / 2, windowHeight / 2 + fm.getHeight());
    g.drawString("-Press SPACE to retry-", (windowWidth/2) - (fm.stringWidth(msg)), windowHeight / 2 + (fm.getHeight()*3));
}

private void drawCongrats(Graphics g) { // draws congrats page

       congrats.render(g);


}


@Override
public void actionPerformed(ActionEvent e){     //actions performed by mainTimer


    if(gameState == 0){
       menu.tick(); // Update the menu
        if (MenuState == 1)
            options.tick();
    }

    if (gameState == 1) {

        Timers();
        updateSprites();
        checkCollisions();
        checkTimer();
    }

    if(gameState == 2) {
        stopTimers();
        backgroundMusic.stop();
        gameOverSound.play();

    }
    if(gameState == 3){
     //   stopTimers();
        congrats.tick();
        backgroundMusic.stop();
        congratsSound.play();
    }

    if (gameState == 4) {
        pause.tick();
    }
    repaint();
}

private void updateSprites(){

    //updates player position and animation frame
    player.animationFrame();
    player.move();

    //updates position of BgSprites and deletes ones that are out of screen
/*    for(int i = 0; i < bgSprites.size(); i++){


        if(bgSprites.get(i).isVisible()){
            bgSprites.get(i).move();
        }
        else{
            bgSprites.remove(i);
        }

    }
*/
    //updates position of background
    for(int i = 0; i<sprites.size();i++) {

        if (sprites.get(i) instanceof Background) {
            if (sprites.get(i).getX() == 0) {
                sprites.add(new Background(windowWidth - ((Background) sprites.get(i)).getSpeed(), 0, new Random().nextInt(5) + 1));
            }
            if (sprites.get(i).isVisible()) {
                ((Background) sprites.get(i)).move();
            } else {
                sprites.remove(i);
            }

        }


        //updates position of obstacles and deletes ones that are out of screen

        if (sprites.get(i) instanceof Obstacle) {

            if (sprites.get(i).isVisible()) {
                ((Obstacle) sprites.get(i)).move();
            } else {
                sprites.remove(i);
            }

        }


        //updates position of bullets and deletes ones that are out of screen
        if(sprites.get(i) instanceof Bullet) {

            if (sprites.get(i).isVisible()) {

                ((Bullet) sprites.get(i)).move();
            } else {
                sprites.remove(i);
            }
        }
    }

}

private void checkCollisions() {

        Rectangle rP = player.getBounds(); //creates hit box for player

        //checks collision with shooter
        Rectangle rS = shooter.getBounds(); //creates hit box for shooter

        if (rP.intersects(rS)) {  //if player touches the shooter

            damageSound.stop();
            gameState = 2;    //game over screen
        }

        //checks collision with obstacles
        boolean bulletCheck = true;
        for (int i = 0; i < sprites.size(); i++) {

            if (sprites.get(i) instanceof Obstacle) {
                Rectangle rO = sprites.get(i).getBounds();    //creates hit box for obstacle

                if (rP.intersects(rO)) {   //if player touches obstacle
                    player.obstacleHit();
                    sprites.remove(i);
                    damageSound.play();
                }

                if (rS.intersects(rO)) {  //if shooter touches obstacle
                    //later there will be some visual effect added here
                    sprites.remove(i);
                }
            }
        }
        for (int i = 0; i < sprites.size(); i++) {


            //checks collision with bullet
            if (sprites.get(i) instanceof Bullet) {

                Rectangle rB = sprites.get(i).getBounds();  //creates hit box for bullet

                if (rP.intersects(rB)) {  //if player touches bullet

                    for (int j = 0; j < sprites.size(); j++) {
                        sprites.remove(i);
                        if (bulletCheck && sprites.get(j) instanceof Heart) {
                            sprites.remove(j);  //removes one life
                            damageSound.play();
                            livesNum--;
                            bulletCheck = false;


                            if (livesNum == 0) {  //checks if player is still alive

                                gameState = 2;    //game over screen
                                damageSound.stop();
                            }
                            break;
                        }
                    }
                }
            }
        }



}

private void checkTimer() {
        if(levelTime <= 0) {
            gameState = 3;
            congratsSound.play();
            stopTimers();
        }
}

private void initLives(){   //creates 3 heart objects and adds them to lives list

    for(int i = 0; i<3;i++){
        sprites.add(new Heart(windowWidth-(55+(i*55)), 0));
    }
    livesNum = 3;
}
    public static boolean getSoundOption(){
        return soundOption;
    }

private void initBackgroundMusic() { // method to load background music
    URL url;
    if(musicOption) {
        url = this.getClass().getResource("/backgroundMusic.wav"); // url to background Music file
    }
    else{
        url = this.getClass().getResource("/soundoff.wav"); // url to background Music file
    }
        backgroundMusic = Applet.newAudioClip(url); // background music object
}

private void initSounds(){  //method to load all game sounds

    if(soundOption) {

        URL url = this.getClass().getResource("/damage.wav");   //url to damage sound
        damageSound = Applet.newAudioClip(url);     //damage sound object

        url = this.getClass().getResource("/danger.wav");
        dangerSound = Applet.newAudioClip(url);

        url = this.getClass().getResource("/gameover.wav");
        gameOverSound = Applet.newAudioClip(url);

        url = this.getClass().getResource("/congratsSound.wav");
        congratsSound = Applet.newAudioClip(url);
    }
    else{
        URL url = this.getClass().getResource("/soundoff.wav");
        damageSound = Applet.newAudioClip(url);     //damage sound object

        dangerSound = Applet.newAudioClip(url);

        gameOverSound = Applet.newAudioClip(url);

        congratsSound = Applet.newAudioClip(url);
    }
}



private void initTimers(){   //starts all timers except main timer which is started at the first steps

    levelTime = 60;  //default time for level, will be multiplied by levelNum
    levelTime *= levelNum;

    timersOn =true;

    shooterRandomTimer = new Random().nextInt(420)+180;

   /* bgSprites = new ArrayList<>();
    bgSpriteTimer = new Timer(3000, e -> {      //creates timer that will add new background sprite every 3 seconds
        bgSprites.add(new BgSprite(windowWidth, new Random().nextInt(windowHeight/3)+1));   //adds new BgSprite object to bgSprites list at random y position
    });
    bgSpriteTimer.start(); */

    /*obstacleTimer = new Timer(1500, e -> {      //creates timer that adds new obstacles every 1.5 second, will be
        // changed in the future based on difficulty level
        sprites.add(new Obstacle(windowWidth, windowHeight, new Random().nextInt(3)+1));
    });
    obstacleTimer.start();

    shooterTimer = new Timer(new Random().nextInt(7000)+3000, e ->{     //timer that makes shooter shoot at random intervals between 3-10 seconds
       sprites.add(new Danger(0, shooter.getY()-15));    //ads danger icon above the shooter
       dangerSound.play();      //plays danger sounds
        Timer tempTimer = new Timer(1000, e1 -> {   //temporary timer that will only run once that creates new bullet and removes danger icon
           sprites.add(new Bullet(shooter.getWidth(), windowHeight-140));
           for(int i=0; i<sprites.size();i++){
               if(sprites.get(i) instanceof Danger){
                   sprites.remove(i);
               }
           }
        });
        tempTimer.setRepeats(false);    //runs only once
        tempTimer.start();

        int newDelay = new Random().nextInt(7000)+3000;     //new random delay for shooterTimer
        shooterTimer.setDelay(newDelay);
        shooterTimer.setInitialDelay(newDelay);
        shooterTimer.restart();     //those 3 line implement new delay for this timer
    });
    shooterTimer.start();

    levelTimer = new Timer(200, e -> {
       levelTime-=1000;
    });
    levelTimer.start();*/

}

private void Timers(){

        if(timersOn){
            levelTimer+=1;
            obstacleTimer+=1;
            shooterTimer+=1;
            if(obstacleTimer==90){
                sprites.add(new Obstacle(windowWidth, windowHeight, new Random().nextInt(3)+1));
                obstacleTimer=0;
            }
            if(shooterTimer==shooterRandomTimer){
                sprites.add(new Danger(0, shooter.getY()-15));    //ads danger icon above the shooter
                dangerSound.play();      //plays danger sounds
            }
            if(shooterTimer==shooterRandomTimer+60){
                sprites.add(new Bullet(shooter.getWidth(), windowHeight-140));
                for(int i=0; i<sprites.size();i++){
                    if(sprites.get(i) instanceof Danger){
                        sprites.remove(i);
                    }
                }
                shooterRandomTimer= new Random().nextInt(420)+180;
                shooterTimer=0;
            }
            if(levelTimer==60){
                levelTime-=1;
                levelTimer=0;
            }
        }

}

private void stopTimers(){  //method that stops all timers

    if (gameState != 3)
        mainTimer.stop();

    timersOn=false;
}

public static int getWindowWidth(){
    return windowWidth;
}

public static int getWindowHeight(){
    return windowHeight;
}




    private class TAdapter extends KeyAdapter{  //checks for key inputs

        @Override
        public void keyReleased(KeyEvent e){

            if (gameState == 0) {
               if (MenuState == 0) {
                   menu.keyReleased(e);
               }
               if (MenuState == 1) {
                   options.keyReleased(e);
               }

            }

            if (gameState == 1) {
                player.keyReleased(e);
            }

            if (gameState == 3) {
                congrats.keyReleased(e);
            }


        }

        @Override
        public void keyPressed(KeyEvent e){

            if (gameState == 1) {
                player.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    gameState = 4;
                    timersOn=false;
                    backgroundMusic.stop();
                }
            }

            if(gameState == 0) {
                if (MenuState == 0) {
                    menu.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                        if (menu.currentSelection == 0) { // CLICK ON PLAY
                            gameState = 1;
                            backgroundMusic.play();
                            initTimers(); //////////////// ATTENTION /////////////////
                            ////////////////////////////// After clicking on play all the other timers need to be start
                        }

                        if (menu.currentSelection == 1) { // OPTIONS
                            MenuState = 1;
                        }

                        if (menu.currentSelection == 2) {
                            MenuState = 2;
                        }

                        if (menu.currentSelection == 3) { // CLICK ON EXIT
                            System.exit(1);
                        }
                    }
                }
                if (MenuState == 1) {
                    options.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {  //to fix, when you click on options you automatically toggle options button

                        if (options.currentSelection == 0) { // turn off background music

                            musicOption = !musicOption;
                            initBackgroundMusic();
                        }

                        if (options.currentSelection == 1) { // turn off sound effects
                            soundOption = !soundOption;
                            initSounds();
                        }

                        if (options.currentSelection == 2) { // goes back
                            MenuState = 0;
                        }
                    }
                }
            }

            if(gameState == 2){
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    gameOverSound.stop();
                    gameState = 1;
                    levelTime = 60;
                    backgroundMusic.play();
                    initGame(); // Besides starting the game again we need to start timers for sprites so we will call initTimers();
                    initTimers();
                }
            }
            if(gameState == 3) {
                congrats.keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (congrats.currentSelection == 0) { // CLICK ON NextLevel
                        gameState = 1;
                        levelNum++;
                        initTimers();
                        player = new Player();
                        initLives();
                        backgroundMusic.play();


                        //////////////// ATTENTION /////////////////
                        ////////////////////////////// After clicking on play all the other timers need to be start
                    }

                    if (congrats.currentSelection == 1) { // CLICK ON Menu
                        gameState = 0;
                    }
                }

            }
            if (gameState == 4) {
                pause.keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (pause.currentSelection == 0) {
                        gameState = 1;
                        backgroundMusic.play();
                        timersOn=true;
                    }
                    if (pause.currentSelection == 1) {
                        gameState = 0;
                        MenuState = 1;
                    }
                    if (pause.currentSelection == 2) {
                        gameState = 0;
                        MenuState = 2;
                    }
                    if (pause.currentSelection == 3) {
                        System.exit(1);
                    }
                }
            }
        }
    }
}


