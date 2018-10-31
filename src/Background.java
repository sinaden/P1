public class Background extends Sprite {

    private final int BACKGROUND_SPEED=4;

    Background(int x, int y, int index){

        super(x, y);
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
