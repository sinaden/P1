package Assets;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

public class Shooter extends Sprite {

    ArrayList<Image>shooterRun;
    int animationFrame=0;

    public Shooter(int x, int y){

        super(x,y);     //invokes main method of super class(Sprite)

        initShooter();
    }

    private void initShooter(){

        BufferedImage ii;
        shooterRun = new ArrayList<>();
        URL url;
        String urlS;

        for(int i = 0; i<43; i++) {
            try {
                if (i < 10)
                    urlS = "/shooter/run_0" + i + ".png";
                else
                    urlS = "/shooter/run_" + i + ".png";
                url = this.getClass().getResource(urlS);
                ii = ImageIO.read(url);

                shooterRun.add(ii);
            }
            catch (Exception ignored){

            }
        }
        image = shooterRun.get(0);
        getImageDimensions();
    }

    public void animationFrame(){
        if(animationFrame<shooterRun.size()-1){
            animationFrame++;

        }
        else{
            animationFrame=0;
        }
        setImage();
    }

    private void setImage(){
        image = shooterRun.get(animationFrame);
    }
}
