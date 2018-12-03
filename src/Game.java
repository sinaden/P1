import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game extends JPanel implements ActionListener {

    private static final int windowWidth = 1024;  //window's width
    private static final int windowHeight = 720;  //window's height
    private final int DELAY = 16;   //delay between each frame
    private int levelTime;  //default time for level, will be multiplied by levelNum
    private int levelNum;   //number of chosen level
    private AudioClip damageSound;   //audio of damage sound
    private AudioClip dangerSound;  //audio of danger sound
    private AudioClip gameOverSound;//audio of game over screen
    //private AudioClip congratsSound; //audio of when you finish a level
    private AudioClip backgroundMusic; // music playing in the background
    private Player player;  //player object
    private Shooter shooter;    //shooter object
    private int gameState = 0;  //0-game menu, 1-game, 2-game over screen, 3-congrats screen
    private ArrayList<Sprite> sprites;
    private Timer mainTimer;    //main timer for the game
    private int livesNum;
    private int levelTimer;
    private int obstacleTimer;
    private int shooterTimer;
    private boolean timersOn;
    private int shooterRandomTimer;
    private int spritesRandomTimer;
    private static boolean musicOption;
    private static boolean soundOption;
    private Menu menu; // menu object
    private CongratsScreen congratsScreen; // congrats screen object
    private Shop shop; // shop object
    private Options options; // options object
    private Pause pause; // pause object
    private GameOverScreen gameOverScreen; //game over screen object
    private Levels levels; // levels object
    private int MenuState = 0;
    private static int levelsUnlocked = 2;


    public Game() {
        initGame();
    }

    private void initGame() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.cyan);  //when we have our background ready it will be deleted
        setDoubleBuffered(true);
        musicOption = true;
        soundOption = true;
        initSounds();
        initBackgroundMusic();
        loadSave();


        //creates timer with delay of our DELAY variable,
        // It's the main Timer which should be created separately at the top of the program
        // (Other timers regarding sprites are created at initTime() method
        mainTimer = new Timer(DELAY, this);
        mainTimer.start();  //starts mainTimer

        newMenu();
    }

    private void newGame() {
        pause = new Pause();

        congratsScreen = new CongratsScreen(); //congrats screen object created

        gameOverScreen = new GameOverScreen(); // game over screen object created

        levelTime *= levelNum;  //this will make levelTime scale up incredibly fast //time needed to complete level, levelNum will be decided in the menu

        player = new Player();  //creates new Player object

        shooter = new Shooter(0, player.getY());    //creates new Shooter object on the edge of the screen and the same floor level as player

        sprites = new ArrayList<>(11);
        for (int i = 0; i < 11; i++) {
            sprites.add(null);
        }
        initLives();
        sprites.set(4, new Background(0, new Random().nextInt(5) + 1));
        sprites.set(5, new Background(0, new Random().nextInt(5) + 1));

    }

    private void newMenu() {
        menu = new Menu(); // menu object from Menu class is created

        shop = new Shop();

        options = new Options();

        levels = new Levels(); // levels object
    }

    private void closeGame() {
        pause = null;

        congratsScreen = null;

        gameOverScreen = null;

        levelTime = 60;

        player = null;

        shooter = null;

        sprites = null;
    }

    private void closeMenu() {
        menu = null;

        shop = null;

        options = null;

        levels = null;
    }

    @Override
    public void paintComponent(Graphics g) {     //draws everything on screen
        super.paintComponent(g);

        if (gameState == 0) {

            if (MenuState == 3) {
                drawShop(g);
            }
            if (MenuState == 2) {
                drawOptions(g);
            }
            if (MenuState == 1) {
                drawLevels(g);
            }
            if (MenuState == 0) {
                drawMenu(g);
            }

        }

        if (gameState == 1) {

            drawGame(g);
        }

        if (gameState == 2) {

            drawGameOverScreen(g);
        }
        if (gameState == 3) {

            drawCongratsScreen(g);
        }
        if (gameState == 4) {
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

    private void drawLevels(Graphics g) {
        levels.render(g);
    }

    private void drawGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //draw Background
        g2d.drawImage(sprites.get(livesNum + 1).getImage(), sprites.get(livesNum + 1).getX(), sprites.get(livesNum + 1).getY(), this);
        g2d.drawImage(sprites.get(livesNum + 2).getImage(), sprites.get(livesNum + 2).getX(), sprites.get(livesNum + 2).getY(), this);

        //draw player
        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);

        //draw shooter
        g2d.drawImage(shooter.getImage(), shooter.getX(), shooter.getY(), this);

        //draw obstacles
        if (sprites.get(livesNum + 4) instanceof Obstacle)
        g2d.drawImage(sprites.get(livesNum + 4).getImage(), sprites.get(livesNum + 4).getX(), sprites.get(livesNum + 4).getY(), this);
        if (sprites.get(livesNum + 5) instanceof Obstacle)
            g2d.drawImage(sprites.get(livesNum + 5).getImage(), sprites.get(livesNum + 5).getX(), sprites.get(livesNum + 5).getY(), this);


        //draw lives
        for (int i = 0; i < livesNum; i++) {
            g2d.drawImage(sprites.get(i).getImage(), sprites.get(i).getX(), sprites.get(i).getY(), this);
        }

        //draw bullets/danger
        if (sprites.get(livesNum + 7) instanceof Bullet || sprites.get(livesNum + 7) instanceof Danger)
        g2d.drawImage(sprites.get(livesNum + 7).getImage(), sprites.get(livesNum + 7).getX(), sprites.get(livesNum + 7).getY(), this);


        //draw timer
        g.setColor(Color.black);
        Font small = pause.loadFont();  //creating new font object
        small = small.deriveFont(Font.BOLD, 30);
        g.setFont(small);
        String msg = "Time: " + String.valueOf((levelTime / 60)) + ":" + String.valueOf((levelTime % 60));   //time in format M:SS
        FontMetrics fm = getFontMetrics(small);


        g.drawString(msg, 0, fm.getHeight());
    }


    private void drawGameOverScreen(Graphics g) {  //draws game over screen

        gameOverScreen.render(g);
        int tempTime = levelTime;
        String msg = "Time: " + String.valueOf((tempTime / 60)) + ":" + String.valueOf((tempTime % 60));
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(msg) - msg.length();
        // int width = getWindowWidth() - fm.stringWidth(msg) /2;
        g.drawString(msg, width, 200);
    }

    private void drawCongratsScreen(Graphics g) { // draws congrats screen

        congratsScreen.render(g);
    }


    @Override
    public void actionPerformed(ActionEvent e) {     //actions performed by mainTimer


        if (gameState == 0) {
            menu.tick(); // Update the menu
            if (MenuState == 1)
                levels.tick();
            if (MenuState == 2)
                options.tick();
            if (MenuState == 3)
                shop.tick();
        }

        if (gameState == 1) {

            Timers();
            updateSprites();
            checkCollisions();
            checkTimer();

        }

        if (gameState == 2) {

            gameOverScreen.tick();
            backgroundMusic.stop();

        }
        if (gameState == 3) {
            //   stopTimers();
            if (levelsUnlocked == levelNum) {
                levelsUnlocked++;
                saveGame();
            }
            congratsScreen.tick();
            backgroundMusic.stop();
            //congratsSound.play();
        }

        if (gameState == 4) {
            pause.tick();
        }
        repaint();
        /*try {
            Thread.sleep(5);
        }
        catch (Exception f){
            System.out.println(f);
        }*/

    }

    private void updateSprites() {


        //updates player position and animation frame
        player.move();
        player.animationFrame();
        shooter.animationFrame();

        //updates position of background


        if (sprites.get(livesNum + 2).getX() == 0) {
            sprites.remove(livesNum + 1);
            sprites.add(livesNum + 2, new Background(windowWidth - ((Background) sprites.get(livesNum + 1)).getSpeed(), new Random().nextInt(5) + 1));
        }
        ((Background) sprites.get(livesNum + 1)).move();
        ((Background) sprites.get(livesNum + 2)).move();


        //updates position of obstacles and deletes ones that are out of screen

        if (sprites.get(livesNum + 4) instanceof Obstacle) {
            if (sprites.get(livesNum + 4).isVisible()) {
                ((Obstacle) sprites.get(livesNum + 4)).move();
            } else {
                sprites.set(livesNum + 4, null);
            }
        }
        if (sprites.get(livesNum + 5) instanceof Obstacle) {
            if (sprites.get(livesNum + 5).isVisible()) {
                ((Obstacle) sprites.get(livesNum + 5)).move();
            } else {
                sprites.set(livesNum + 5, null);
            }
        }



        //updates position of bullets and deletes ones that are out of screen
        if (sprites.get(livesNum + 7) instanceof Bullet) {

            if (sprites.get(livesNum + 7).isVisible()) {

                ((Bullet) sprites.get(livesNum + 7)).move();
            } else {
                sprites.set(livesNum + 7, null);
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
            gameOverSound.play();
        }

        //checks collision with obstacles

        if (sprites.get(livesNum + 4) instanceof Obstacle) {
            Rectangle rO = sprites.get(livesNum + 4).getBounds();    //creates hit box for obstacle

            if (rP.intersects(rO)) {   //if player touches obstacle
                player.obstacleHit();
                sprites.set(livesNum + 4, null);
                damageSound.play();
            }


            if (rS.intersects(rO)) {  //if shooter touches obstacle
                //later there will be some visual effect added here
                sprites.set(livesNum + 4, null);
            }

        }
        if (sprites.get(livesNum + 5) instanceof Obstacle) {
            Rectangle rO = sprites.get(livesNum + 5).getBounds();    //creates hit box for obstacle

            if (rP.intersects(rO)) {   //if player touches obstacle
                player.obstacleHit();
                sprites.set(livesNum + 5, null);
                damageSound.play();
            }

            if (rS.intersects(rO)) {  //if shooter touches obstacle
                //later there will be some visual effect added here
                sprites.set(livesNum + 5, null);
            }

        }


        //checks collision with bullet
        if (sprites.get(livesNum + 7) instanceof Bullet) {

            Rectangle rB = sprites.get(livesNum + 7).getBounds();  //creates hit box for bullet

            if (rP.intersects(rB)) {  //if player touches bullet

                sprites.set(livesNum + 7, null);
                sprites.remove(0);  //removes one life
                damageSound.play();
                livesNum--;

                if (livesNum == 0) {  //checks if player is still alive
                    gameState = 2;    //game over screen
                    damageSound.stop();
                    gameOverSound.play();
                }

            }
        }


    }

    private void checkTimer() {
        if (levelTime <= 0) {
            gameState = 3;
            //congratsSound.play();
            stopTimers();
        }
    }

    private void initLives() {   //creates 3 heart objects and adds them to lives list

        for (int i = 0; i < 3; i++) {
            sprites.set(i, new Heart(windowWidth - (55 + (i * 55)), 0));
        }
        livesNum = 3;
    }

    public static boolean getSoundOption() {
        return soundOption;
    }

    public static boolean getMusicOption() {
        return musicOption;
    }

    private void initBackgroundMusic() { // method to load background music
        URL url;
        if (musicOption) {
            url = this.getClass().getResource("/backgroundMusic.wav"); // url to background Music file
        } else {
            url = this.getClass().getResource("/soundoff.wav"); // url to background Music file
        }
        backgroundMusic = Applet.newAudioClip(url); // background music object
    }

    private void initSounds() {  //method to load all game sounds

        if (soundOption) {

            URL url = this.getClass().getResource("/damage.wav");   //url to damage sound
            damageSound = Applet.newAudioClip(url);     //damage sound object

            url = this.getClass().getResource("/danger.wav");
            dangerSound = Applet.newAudioClip(url);

            url = this.getClass().getResource("/gameover.wav");
            gameOverSound = Applet.newAudioClip(url);

            //url = this.getClass().getResource("/congratsSound.wav");
            //congratsSound = Applet.newAudioClip(url);
        } else {
            URL url = this.getClass().getResource("/soundoff.wav");
            damageSound = Applet.newAudioClip(url);     //damage sound object

            dangerSound = Applet.newAudioClip(url);

            gameOverSound = Applet.newAudioClip(url);

            //congratsSound = Applet.newAudioClip(url);
        }
    }


    private void initTimers() {   //starts all timers except main timer which is started at the first steps

        levelTime = 20;  //default time for level, will be multiplied by levelNum
        levelTime *= levelNum;

        timersOn = true;

        shooterRandomTimer = new Random().nextInt(420) + 180;

        spritesRandomTimer = new Random().nextInt(40) + 50;

        levelTimer = 0;
        obstacleTimer = 0;
        shooterTimer = 0;


    }

    private void Timers() {

        if (timersOn) {
            levelTimer += 1;
            obstacleTimer += 1;
            shooterTimer += 1;
            if (obstacleTimer == spritesRandomTimer) {
                if (sprites.get(livesNum + 4) == null)
                    sprites.set(livesNum + 4, new Obstacle(windowWidth, windowHeight, new Random().nextInt(4) + 1));
                else
                    sprites.set(livesNum + 5, new Obstacle(windowWidth, windowHeight, new Random().nextInt(4) + 1));
                obstacleTimer = 0;
                spritesRandomTimer = new Random().nextInt(40) + 50;
            }
            if (shooterTimer == shooterRandomTimer) {
                sprites.set(livesNum + 7, new Danger(0, shooter.getY() - 15));    //ads danger icon above the shooter
                dangerSound.play();      //plays danger sounds
            }
            if (shooterTimer == shooterRandomTimer + 60) {
                sprites.set(livesNum + 7, new Bullet(shooter.getWidth(), shooter.getY() + 28));
                shooterRandomTimer = new Random().nextInt(420) + 180;
                shooterTimer = 0;
            }
            if (levelTimer == 60) {
                levelTime -= 1;
                levelTimer = 0;
            }
        }

    }

    private void stopTimers() {  //method that stops all timers

        if (gameState != 3)
            mainTimer.stop();

        timersOn = false;
    }

    private void loadSave() {
        File file = new File("./saveFile.txt");
        try {
            Scanner in = new Scanner(file);
            levelsUnlocked = in.nextInt();
        } catch (Exception e) {
            try {
                PrintWriter writer = new PrintWriter("saveFile.txt", "UTF-8");
                writer.println("1");
                writer.close();
                levelsUnlocked = 1;

            } catch (Exception f) {
                System.out.println("FILE NOT FOUND");
            }
        }

    }

    private void saveGame() {
        try {
            PrintWriter writer = new PrintWriter("saveFile.txt", "UTF-8");
            writer.println(levelsUnlocked);
            writer.close();

        } catch (Exception e) {
            System.out.println("FILE NOT FOUND");
        }

    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static int getLevelsUnlocked() {
        return levelsUnlocked;
    }


    private class TAdapter extends KeyAdapter {  //checks for key inputs

        @Override
        public void keyReleased(KeyEvent e) {

            if (gameState == 0) {
                if (MenuState == 0) {
                    menu.keyReleased(e);
                }
                if (MenuState == 1) {
                    levels.keyReleased(e);
                }
                if (MenuState == 2) {
                    options.keyReleased(e);
                }
                if (MenuState == 3) {
                    shop.keyRelesed(e);
                }

            }

            if (gameState == 1) {
                player.keyReleased(e);
            }

            if (gameState == 2) {
                gameOverScreen.keyReleased(e);
            }

            if (gameState == 3) {
                congratsScreen.keyReleased(e);
            }

            if (gameState == 4) {
                pause.keyReleased(e);
            }


        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (gameState == 1) {
                player.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    gameState = 4;
                    timersOn = false;
                    backgroundMusic.stop();
                }
            }

            if (gameState == 0) {
                if (MenuState == 0) {
                    menu.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                        if (menu.currentSelection == 0) {
                            MenuState = 1;
                            levels.currentSelection = 0;
                            e.setKeyCode(KeyEvent.VK_Z);
                        }


                        if (menu.currentSelection == 1) { // OPTIONS
                            MenuState = 2;
                            options.currentSelection = 0;
                            musicOption = !musicOption;
                        }

                        if (menu.currentSelection == 2) {
                            MenuState = 3;
                            shop.currentSelection = 0;
                        }

                        if (menu.currentSelection == 3) { // CLICK ON EXIT
                            System.exit(1);
                        }
                    }
                }
                if (MenuState == 1) {
                    levels.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (levels.currentSelection == 0) {
                            levelNum = levels.currentSelection + 1;
                            gameState = 1;
                            backgroundMusic.play();
                            initTimers();

                        }
                        if (levels.currentSelection == 1) {

                            levelNum = levels.currentSelection + 1;
                            gameState = 1;
                            backgroundMusic.play();
                            initTimers();


                        }
                        if (levels.currentSelection == 2) {

                            levelNum = levels.currentSelection + 1;
                            gameState = 1;
                            backgroundMusic.play();
                            initTimers();


                        }
                        if (levels.currentSelection == 3) {

                            levelNum = levels.currentSelection + 1;
                            gameState = 1;
                            backgroundMusic.play();
                            initTimers();


                        }
                        if (levels.currentSelection == 4) {

                            levelNum = levels.currentSelection + 1;
                            gameState = 1;
                            backgroundMusic.play();
                            initTimers();


                        }
                        if (levels.currentSelection == 5) {

                            levelNum = levels.currentSelection + 1;
                            gameState = 1;
                            backgroundMusic.play();
                            initTimers();


                        }
                        if (levels.currentSelection == 6) {

                            levelNum = levels.currentSelection + 1;
                            gameState = 1;
                            backgroundMusic.play();
                            initTimers();


                        }
                        if (levels.currentSelection == 7) {

                            gameState = 1;
                            backgroundMusic.play();
                            initTimers();
                        }
                        if (levels.currentSelection == 8) {
                            MenuState = 0;
                        }

                        if (levels.currentSelection != 8) {
                            newGame();
                            closeMenu();
                        }


                    }
                }
                if (MenuState == 2) {
                    options.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {

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
                if (MenuState == 3) {
                    shop.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (shop.currentSelection == 6) {
                            System.out.println("Back to menu");
                            MenuState = 0;
                        }
                    }
                }
            }

            if (gameState == 2) {
                gameOverScreen.keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (gameOverScreen.currentSelection == 0) {
                        gameOverSound.stop();
                        gameState = 1;
                        backgroundMusic.play();
                        newGame();
                        initTimers();
                    }

                    if (gameOverScreen.currentSelection == 1) {
                        gameState = 0;
                        newMenu();
                        closeGame();
                    }

                    if (gameOverScreen.currentSelection == 2) {
                        System.exit(1);
                    }
                }
            }
            if (gameState == 3) {
                congratsScreen.keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (congratsScreen.currentSelection == 0) { // CLICK ON NextLevel
                        gameState = 1;
                        levelNum++;
                        newGame();
                        initTimers();
                        backgroundMusic.play();

                    }

                    if (congratsScreen.currentSelection == 1) { // CLICK ON Menu
                        gameState = 0;
                        MenuState = 0;
                        newMenu();
                        closeGame();
                        menu.currentSelection = 0;
                        e.setKeyCode(KeyEvent.VK_Z);
                    }
                }

            }
            if (gameState == 4) {
                pause.keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (pause.currentSelection == 0) {
                        gameState = 1;
                        backgroundMusic.play();
                        timersOn = true;
                    }
                    if (pause.currentSelection == 1) {
                        gameState = 0;
                        MenuState = 0;
                        newMenu();
                        menu.currentSelection = 0;
                        closeGame();
                        e.setKeyCode(KeyEvent.VK_Z);
                    }
                    if (pause.currentSelection == 2) {
                        System.exit(1);
                    }
                }
            }
        }
    }
}


