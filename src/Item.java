import java.awt.*;

public class Item {
// This Class would provide some easier access to relevant
// methods for editing texts. for example changing color, font or size and positions

    public static void drawImage(Graphics g, Image picture, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;

        //ImageIcon igun = new ImageIcon(this.getClass().getResource("/gunSmal.png"));

        //Image Picture = igun.getImage();

        g2d.drawImage(picture, x , y, null);

    }
//
//    public static void drawString(Graphics g, Font f, Color c, String text) {
//        FontMetrics fm = g.getFontMetrics(f);
//        int x = (Game.getWindowWidth() - fm.stringWidth(text)) / 2;
//        int y = (Game.getWindowHeight() - fm.getHeight() / 2) + fm.getAscent();
//        drawString(g, f, c, text, x, y);
//
//    }
//
//    public static void drawString(Graphics g, Font f, Color c, String text, int y) {
//        FontMetrics fm = g.getFontMetrics(f);
//        int x = (Game.getWindowWidth() - fm.stringWidth(text)) / 2;
//        drawString(g, f, c, text, x, y);
//
//    }
}
