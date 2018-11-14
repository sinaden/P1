import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class Shop {

    private Image shopPic; // The background Image of the menu
    private Image powerUpPic;
    private Image newImage;

    public Font loadFont(){
        try {
            String fName = "/DK Cool Crayon.ttf";
            InputStream is = this.getClass().getResourceAsStream(fName);
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font;
        }
        catch (Exception e){
            return null;
        }
    }


    public Shop() {
        Font fontSmall= loadFont();
        fontSmall = fontSmall.deriveFont(Font.BOLD, 70);
        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);

    }

    public void render(Graphics g) { // This method draws the whole menu
        // (is called at the drawMenu method of Game.java)

        Graphics2D g2d = (Graphics2D) g;

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/shop4.png"));   //assigns image icon based on given url
        shopPic = ii.getImage();  //ImageIcon method to assign image icon from ii object to image
        g2d.drawImage(shopPic, 0, 0, null);


        // drawing The title "THE QUITE KID"
        //Font f = new Font("");


        Font fontBig = loadFont();
        fontBig = fontBig.deriveFont(Font.BOLD, 80);

        Fonts.drawString(g, fontBig,
                Color.WHITE, " \"THE QUIET KID\" ", 100);

        //
//        Fonts.drawString(g, new Font("DK Cool Crayon", Font.BOLD, 75),
//                Color.WHITE, " \"Welcome to the shop\" ", 100);


        ImageIcon i2 = new ImageIcon(this.getClass().getResource("/vand1.png"));   //assigns image icon based on given url
        powerUpPic = i2.getImage();
        // 49 * 120
        g2d.drawImage(powerUpPic, 500, 380, null);

        //New Image;
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/vand2.png"));
        powerUpPic = icon.getImage();
        //powerUpPic = powerUpPic.getScaledInstance(50 * 2, 120 * 2, Image.SCALE_DEFAULT);

        g2d.drawImage(powerUpPic, 500 - 50, 380 - 15, null);


//        // Rendereing every Button, And implementing the "currentSelection"
//        // to the button by changing the selected variable of that button
//        for (int i = 0; i < options.length; i++) {
//
//            if (i == currentSelection)
//                options[i].setSelected(true);
//            else
//                options[i].setSelected(false);
//            options[i].render(g);
//        }

    }

}
