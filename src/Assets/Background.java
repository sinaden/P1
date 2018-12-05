package Assets;
public class Background extends Sprite {

    private final int BACKGROUND_SPEED=8;

    public Background(int x, int index){

        super(x, 257);
        initBackground(index);
    }

    private void initBackground(int index){

        String name = "/bg"+index+".png";

        loadImage(name);
        getImageDimensions();
    }

    public void move(){

        x-=BACKGROUND_SPEED;

        if(x+width<0){
            setVisible(false);
        }
    }

    public int getSpeed(){
        return BACKGROUND_SPEED;
    }
}
