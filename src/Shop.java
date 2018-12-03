import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;

@SuppressWarnings("Duplicates")
public class Shop {

    private final ShopButton[] options;
    private final Button backToMenu;

    private Image shopPic; // The background Image of the menu


    public int currentSelection;// Which button the user is on now
    // Numbers would be 0 for "PLAY", 1 for "OPTIONS", 2 for "SHOPS" & 3 for "EXIT"

    private int xd; // if it's +1 the current Selection would go up and if it's -1, it goes down
    private boolean stopHere = false; // If it's true, the menu could not be updated
    // (used for not changing the currentselected when user's finger is still on the keyboard.)




//    private Image powerUpPic;
//    private Image newImage;

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


    public Shop() {
        options = new ShopButton[6]; // Here Options Sizes can be changed.
        Font fontSmall= loadFont();
        fontSmall = fontSmall.deriveFont(Font.BOLD, 70);
        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);



        backToMenu = new Button("Back To Menu", 300 + 4 * 76,
                //new Font("DK Cool Crayon", Font.BOLD, 70), // The text format when it's not selected
                fontSmall,
                fontBig,// The text format when it's selected,

                // the only change is the size
                Color.WHITE, Color.WHITE);

        ImageIcon i2 = new ImageIcon(this.getClass().getResource("/vand1.png"));
        Image Picture = i2.getImage();
        // 49 * 120
        //g2d.drawImage(powerUpPic, 500, 380, null);
        //New Image;
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/vand2.png"));
        Image SelectedPicture = icon.getImage();

        options[0] = new ShopButton(Picture, SelectedPicture, 350 - Picture.getWidth(null) / 2 , 170);

        i2 = new ImageIcon(this.getClass().getResource("/fries1.png"));
        Picture = i2.getImage();
        // 49 * 120
        //g2d.drawImage(powerUpPic, 500, 380, null);
        //New Image;
        icon = new ImageIcon(this.getClass().getResource("/fries2.png"));
        SelectedPicture = icon.getImage();

        options[1] = new ShopButton(Picture, SelectedPicture, 310 + 2 * 100 - Picture.getWidth(null) / 2, 170);

        i2 = new ImageIcon(this.getClass().getResource("/skate1.png"));
        Picture = i2.getImage();
        // 49 * 120
        //g2d.drawImage(powerUpPic, 500, 380, null);
        //New Image;
        icon = new ImageIcon(this.getClass().getResource("/skate2.png"));
        SelectedPicture = icon.getImage();

        options[2] = new ShopButton(Picture, SelectedPicture, 350 + 3 * 100 - Picture.getWidth(null) / 2, 180);

        i2 = new ImageIcon(this.getClass().getResource("/non1.png"));
        Picture = i2.getImage();
        // 49 * 120
        //g2d.drawImage(powerUpPic, 500, 380, null);
        //New Image;
        icon = new ImageIcon(this.getClass().getResource("/non2.png"));
        SelectedPicture = icon.getImage();

        options[3] = new ShopButton(Picture, SelectedPicture, 350 - Picture.getWidth(null) / 2, 150 + 180);
        i2 = new ImageIcon(this.getClass().getResource("/non1.png"));
        Picture = i2.getImage();
        // 49 * 120
        //g2d.drawImage(powerUpPic, 500, 380, null);
        //New Image;
        icon = new ImageIcon(this.getClass().getResource("/non2.png"));
        SelectedPicture = icon.getImage();

        options[4] = new ShopButton(Picture, SelectedPicture, 400 + 100 - Picture.getWidth(null) / 2, 150 + 180);

        i2 = new ImageIcon(this.getClass().getResource("/non1.png"));
        Picture = i2.getImage();
        // 49 * 120
        //g2d.drawImage(powerUpPic, 500, 380, null);
        //New Image;
        icon = new ImageIcon(this.getClass().getResource("/non2.png"));
        SelectedPicture = icon.getImage();

        options[5] = new ShopButton(Picture, SelectedPicture, 420 + 2 * 100 - Picture.getWidth(null) / 2, 150 + 180);

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
            currentSelection = options.length;
        }

        // The same should be done for the other way

        if (currentSelection > options.length) {
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


    public void keyRelesed(KeyEvent e){

        int key = e.getKeyCode();
        xd = 0;
        stopHere = false;
    }



    public void render(Graphics g) { // This method draws the whole menu
        // (is called at the drawMenu method of Game.java)

        Graphics2D g2d = (Graphics2D) g;

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/shopNew2.png"));   //assigns image icon based on given url
        shopPic = ii.getImage();  //ImageIcon method to assign image icon from ii object to image
        g2d.drawImage(shopPic, 0, 0, null);


        // drawing The title "THE QUITE KID"
        //Font f = new Font("");


        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);

        Fonts.drawString(g, fontBig,
                Color.WHITE, " \"THE SHOP\" ", 87);

        //
//        Fonts.drawString(g, new Font("DK Cool Crayon", Font.BOLD, 75),
//                Color.WHITE, " \"Welcome to the shop\" ", 100);

//
//        ImageIcon i2 = new ImageIcon(this.getClass().getResource("/vand1.png"));   //assigns image icon based on given url
//        powerUpPic = i2.getImage();
//        // 49 * 120
//        g2d.drawImage(powerUpPic, 500, 380, null);
//
//        //New Image;
//        ImageIcon icon = new ImageIcon(this.getClass().getResource("/vand2.png"));
//        powerUpPic = icon.getImage();
//        //powerUpPic = powerUpPic.getScaledInstance(50 * 2, 120 * 2, Image.SCALE_DEFAULT);
//
//        g2d.drawImage(powerUpPic, 500 - 50, 380 - 15, null);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        // Rendereing every Button, And implementing the "currentSelection"
//        // to the button by changing the selected variable of that button
        for (int i = 0; i < options.length; i++) {
            if (i == currentSelection)
                options[i].setSelected(true);
            else
                options[i].setSelected(false);
            options[i].render(g);
        }

        if (currentSelection == options.length) {
            backToMenu.setSelected(true);
        }
        else
            backToMenu.setSelected(false);
        backToMenu.render(g);
    }

}
