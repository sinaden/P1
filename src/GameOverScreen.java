import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;

@SuppressWarnings("Duplicates")
public class GameOverScreen {

        private final Button[] Options; // options is an array of several Buttons for the main menu
        // They would be the "PLAY" "OPTIONS" "SHOP" & "EXIT" buttons.
        // check the "Button.java" class for more details on this object. (button)

        private Image menuOne; // The background Image of the menu

        public int currentSelection;// Which button the user is on now
        // Numbers would be 0 for "PLAY", 1 for "OPTIONS", 2 for "SHOPS" & 3 for "EXIT"

        private int xd; // if it's +1 the current Selection would go up and if it's -1, it goes down
        private boolean stopHere = false; // If it's true, the menu could not be updated
        // (used for not changing the current selection when user's finger is still on the keyboard.)

        public Font loadFont(){
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
        public GameOverScreen() {
            Options = new Button[3];

            Font fontSmall= loadFont();
            fontSmall = fontSmall.deriveFont(Font.BOLD, 70);
            Font fontBig = loadFont();
            fontBig = fontBig.deriveFont(Font.BOLD, 80);

            // Options array would be made here, remember that TTF file for the "DK Cool Crayon"
            // Font should be added to JVM fonts folder

            Options[0] = new Button("Retry", 300,
                    fontSmall, // The text format when it's not selected
                    fontBig,// The text format when it's selected,
                    // the only change is the size
                    Color.WHITE, Color.WHITE); // The colors don't change but we can change them
            // if it's was to be.

            Options[1] = new Button("Menu", 300 + 76,
                    fontSmall,
                    fontBig, Color.WHITE, Color.WHITE);

            Options[2] = new Button("Exit", 300 + 2 * 76,
                    fontSmall,
                    fontBig, Color.WHITE, Color.WHITE);

        }

        public void tick() { // Update the menu (pretty much like the Player's move method)
            // , the method is called at the ActionPerformed Method at Game.java

            if (!stopHere) {
                if (xd == -1) { // means it goes to an upper button
                    currentSelection--;
                    stopHere = true;
                }
                if (xd == 1){
                    currentSelection++;
                    stopHere = true;
                }

            }

            // If the current Selection is less than Zero
            // (i.e it points to a button which is upper than the first button ,"PLAY")
            // then it should be revalued to 3, so it would point to the "Exit" Button.
            if (currentSelection < 0) {
                currentSelection = Options.length - 1;
            }

            // The same should be done for the other way
            if (currentSelection >= Options.length) {
                currentSelection = 0;
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

            int key = e.getKeyCode();
            xd = 0;
            stopHere = false;
        }

        public void render(Graphics g) { // This method draws the whole menu
            // (is called at the drawMenu method of Game.java)

            Graphics2D g2d = (Graphics2D) g;

            ImageIcon ii = new ImageIcon(this.getClass().getResource("/bboard.png"));   //assigns image icon based on given url
            menuOne = ii.getImage();  //ImageIcon method to assign image icon from ii object to image
            g2d.drawImage(menuOne, 0, 0, null);

            // int tempTime = levelTime;
          //  msg = "Time: " +String.valueOf((tempTime/60))+":"+String.valueOf((tempTime%60));


            Font fontBig = loadFont();
            fontBig = fontBig.deriveFont(Font.BOLD, 80);


            // Drawing the title OPTIONS
            Fonts.drawString(g, fontBig,
                    Color.WHITE, "GAME OVER", 100);


            // Rendering every Button, And implementing the "currentSelection"
            // to the button by changing the selected variable of that button
            for (int i = 0; i < Options.length; i++) {

                if (i == currentSelection)
                    Options[i].setSelected(true);
                else
                    Options[i].setSelected(false);
                Options[i].render(g);
            }
        }
    }
