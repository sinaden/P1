package Menus;
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

        Graphics2D g2d = (Graphics2D) g;

        if (selected) { // If the button is selected then draw the second font and second color (second font has a bigger size)
         //   Image k = selectemItem;
            int p1 = item.getHeight(null) - selectemItem.getHeight(null);
            g2d.drawImage(selectemItem, picX , picY + p1, null);
            //Fonts.drawString(g, selectedFont, selectedColor,   text , textY);
        }
        else
        g2d.drawImage(selectemItem, picX , picY, null);



    }

}
