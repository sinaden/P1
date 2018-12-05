package Menus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.Objects;
import Game.Game;

@SuppressWarnings("Duplicates")
public class Levels {


        private final Button backToMenu, endlessMode;
        private final LevelButton[] options; // options is an array of several Buttons for the main menu
        // They would be the "PLAY" "OPTIONS" "SHOP" & "EXIT" buttons.
        // check the "Button.java" class for more details on this object. (button)

        private Image menuOne; // The background Image of the menu
        private Image lock;

        public int currentSelection;// Which button the user is on now
        // Numbers would be 0 for "PLAY", 1 for "OPTIONS", 2 for "SHOPS" & 3 for "EXIT"

        private int xd; // if it's +1 the current Selection would go up and if it's -1, it goes down
        private boolean stopHere = false; // If it's true, the menu could not be updated
        // (used for not changing the current selection when user's finger is still on the keyboard.)

        private Font loadFont(){
            try {
                String fName = "/DK Cool Crayon.ttf";
                InputStream is = this.getClass().getResourceAsStream(fName);
                return Font.createFont(Font.TRUETYPE_FONT, is);
            }
            catch (Exception e){
                return null;
            }
        }


        // Constructor
        public Levels() {
            options = new LevelButton[7];

            Font fontSmall= loadFont();
            fontSmall = Objects.requireNonNull(fontSmall).deriveFont(Font.BOLD, 70);
            Font fontBig = loadFont();
            fontBig = Objects.requireNonNull(fontBig).deriveFont(Font.BOLD, 80);

            // Options array would be made here, remember that TTF file for the "DK Cool Crayon"
            // Font should be added to JVM fonts folder

            endlessMode = new Button("Endless mode", 500,
                    fontSmall,
                    fontBig, Color.WHITE, Color.WHITE);

            backToMenu = new Button("Back to menu", 300 + 4 * 76,
                    fontSmall,
                    fontBig, Color.WHITE, Color.WHITE);


            options[0] = new LevelButton("1", 206, 260,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[1] = new LevelButton("2", 206 * 2, 260,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[2] = new LevelButton("3", 206 * 3, 260,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[3] = new LevelButton("4", 206 * 4, 260,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[4] = new LevelButton("5", 309, 360,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[5] = new LevelButton("6", 309 + 206, 360,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);

                options[6] = new LevelButton("7", 309 + 412, 360,
                        fontSmall,
                        fontBig, Color.GRAY, Color.WHITE);


            }

        public void tick() { // Update the menu (pretty much like the Player's move method)
            // , the method is called at the ActionPerformed Method at Game.java

            if (!stopHere) {
                if (xd == -1) { // means it goes to an upper button
                    currentSelection--;
                    stopHere = true;
                }
                if (xd == 1){
                        currentSelection ++;
                        stopHere = true;
                }

            }

            // If the current Selection is less than Zero
            // (i.e it points to a button which is upper than the first button ,"PLAY")
            // then it should be revalued to 3, so it would point to the "Exit" Button.
            if (currentSelection < 0) {
                currentSelection = options.length+1;
            }

            // The same should be done for the other way
            if (currentSelection > options.length+1) {
                currentSelection = 0;
            }
            if(currentSelection>=Game.getLevelsUnlocked() && currentSelection<options.length && xd==1){
                currentSelection=options.length;
            }
            if(currentSelection>=Game.getLevelsUnlocked() && currentSelection<options.length && xd==-1){
                currentSelection=Game.getLevelsUnlocked()-1;
            }
        }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP){ //Up key pressed
                xd = -1;
            }

            if (key == KeyEvent.VK_DOWN){ //Down key pressed
                //currentSelection++;
                //wasPressed2 = true;
                xd = 1;
            }
        }

        public void keyReleased(KeyEvent e){

            xd = 0;
            stopHere = false;
        }

        public void render(Graphics g) { // This method draws the whole menu
            // (is called at the drawMenu method of Game.java)

            Graphics2D g2d = (Graphics2D) g;

            ImageIcon ii = new ImageIcon(this.getClass().getResource("/bboard.png"));   //assigns image icon based on given url
            menuOne = ii.getImage();  //ImageIcon method to assign image icon from ii object to image
            g2d.drawImage(menuOne, 0, 0, null);




            //  int tempTime = levelTime;
            //  msg = "Time: " +String.valueOf((tempTime/60))+":"+String.valueOf((tempTime%60));


            Font fontBig = loadFont();
            fontBig = Objects.requireNonNull(fontBig).deriveFont(Font.BOLD, 80);


            // Drawing the title OPTIONS
            Fonts.drawString(g, fontBig,
                    Color.WHITE, "Level selection", 100);


            // Rendering every Button, And implementing the "currentSelection"
            // to the button by changing the selected variable of that button
            for (int i = 0; i <= options.length+1; i++) {

                if (i<7&&i == currentSelection)
                    options[i].setSelected(true);
                else if(i<7)
                    options[i].setSelected(false);
                if(i<7)
                options[i].render(g);
            }
            if (currentSelection == options.length) {
                endlessMode.setSelected(true);
            }
                else
                    endlessMode.setSelected(false);
                endlessMode.render(g);

            if (currentSelection == options.length+1) {
                backToMenu.setSelected(true);
            }
            else
                backToMenu.setSelected(false);
            backToMenu.render(g);

            ImageIcon i1 = new ImageIcon((this.getClass().getResource("/lock.png")));
            lock = i1.getImage();
          //  g2d.drawImage(lock, 176, 190, null);
            switch (Game.getLevelsUnlocked()) {
                case 1:
                    g2d.drawImage(lock, 182 + 206, 190, null);
                case 2:
                    g2d.drawImage(lock, 188 + 2 * 206, 190, null);
                case 3:
                    g2d.drawImage(lock, 190 + 3 * 206, 190, null);
                case 4:
                    g2d.drawImage(lock, 286, 290, null);
                case 5:
                    g2d.drawImage(lock, 288 + 206, 290, null);
                case 6:
                    g2d.drawImage(lock, 290 + 206 * 2, 290, null);
                case 7:
            }
        }
    }
