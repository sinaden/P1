import java.awt.*;

public class LevelButton extends Rectangle {
    private boolean selected; //each button has a boolean "selected" if it's True then
    // the program would compile the button in a different way (for example bigger font or some extra graphics)
    private final int X;
    private final int Y;
    private final String text;

    private final Font font;
    private final Font selectedFont; // Two fonts. first one is the initiative font (before being selected)
    // and the second one: "selected font" would be used if we want to use a different font for our selected button.

    private final Color color;
    private final Color selectedColor; // // Two colors. first one is the initiative color (before being selected)
    // and the second one: "selected color" would be used if we want to use a different color for our selected button.


    public LevelButton(String text, int X, int Y, Font font, Font selectedFont, Color color,
                       Color selectedColor) {
        this.font = font;
        this.selectedFont = selectedFont;
        this.color = color;
        this.selectedColor = selectedColor;
        this.text = text;
        this.X = X;
        this.Y = Y;
    }

    public void setSelected(boolean selected) { // The method that would change the "selected" variable
        this.selected = selected;
    }

    public void render(Graphics g) {   // The method for drawing button , which would be called at the menu class


        if (selected) { // If the button is selected then draw the second font and second color (second font has a bigger size)
            Fonts.drawString(g, selectedFont, selectedColor, text, X, Y);
        } else
            Fonts.drawString(g, font, color, text, X, Y);
//
//        // set the position of the button based on the text's width
//        FontMetrics fm = g.getFontMetrics();
//        this.x = (Game.getWindowWidth() - fm.stringWidth(text)) /2;
//       // this.y = textY - fm.getHeight(); // here the vertical position of the button would be set
//        this.width = fm.stringWidth(text);
//        this.height = fm.getHeight();
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
//            g2d.drawImage(gunOne, 400 - fm.stringWidth(text) / 2 , 260 - 100 , null);
//            g2d.drawImage(gunTwo, 520 + fm.stringWidth(text) / 2 , 260 - 100 , null);
//
//        }
    }
}
