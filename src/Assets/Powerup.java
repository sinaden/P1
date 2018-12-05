package Assets;
public class Powerup extends Sprite {

    private final int SPRITE_SPEED = 15;

    public Powerup(int x, int y, int index) {

        super(x, y);


        initPowerup(index);
    }

    private void initPowerup(int i){

        switch (i){
            case 1:
                loadImage("/banana.png");
                break;
            case 2:
                loadImage("/drink.png");
                break;
            case 3:
                loadImage("/skate.png");
                break;
        }
        getImageDimensions();
        y-=60+height;
    }

    public void move(){

        x-= SPRITE_SPEED;

        if(x < 0 - getWidth()){
            visible = false;
        }
    }
}
