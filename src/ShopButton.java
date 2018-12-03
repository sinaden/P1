import java.awt.*;

public class ShopButton extends Rectangle {
    private boolean selected; //each button has a boolean "selected" if it's True then
    // the program would compile the button in a different way (for example bigger font or some extra graphics)
    private final Image item;
    private final Image selectemItem; // The background Image of the menu

    private final int picX;
    private final int picY;

    public ShopButton(Image item, Image selectemItem, int picX, int picY) {
        this.item = item;
        this.selectemItem = selectemItem;
        this.picX = picX;
        this.picY = picY;
    }

    public void setSelected(boolean selected) { // The method that would change the "selected" variable
        this.selected = selected;
    }

    public void render(Graphics g) {   // The method for drawing button , which would be called at the menu class


        if (selected) { // If the button is selected then draw the second font and second color (second font has a bigger size)
         //   Image k = selectemItem;
            int p1 = item.getHeight(null) - selectemItem.getHeight(null);
            Item.drawImage(g, selectemItem, picX, picY + p1);
            //Fonts.drawString(g, selectedFont, selectedColor,   text , textY);
        }
        else
            Item.drawImage(g, item, picX, picY);

        // set the position of the button based on the text's width

     /*
                                        FontMetrics fm = g.getFontMetrics();
                                        this.x = (Game.getWindowWidth() - fm.stringWidth(text)) /2;
                                        this.y = textY - fm.getHeight(); // here the vertical position of the button would be set
                                        this.width = fm.stringWidth(text);
                                        this.height = fm.getHeight();
*/

//        this.width =
//        if (selected) { // Adding more graphical features to the selected button
//            // (two guns at the sides of the text// run the game to understand that)
//
//            Graphics2D g2d = (Graphics2D) g;
//
//            ImageIcon igun = new ImageIcon(this.getClass().getResource("/gunSmal.png"));
//
//            Image gunOne = igun.getImage();
//
//            ImageIcon jgun = new ImageIcon(this.getClass().getResource("/gunSmalFlip.png"));
//
//            Image gunTwo = jgun.getImage();
//
//            g2d.drawImage(gunOne, 400 - fm.stringWidth(text) / 2 , textY - 100 , null);
//            g2d.drawImage(gunTwo, 520 + fm.stringWidth(text) / 2 , textY - 100 , null);
//
//        }

    }

}
