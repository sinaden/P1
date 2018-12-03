public class Obstacle extends Sprite {

    private final int OBSTACLE_SPEED = 15;

    public Obstacle(int x, int y, int index){

        super (x, y);   //invokes main method of super class(Sprite)

        initSpike(index);
    }

    private void initSpike(int i){

        switch (i){
            case 1:
                loadImage("/table.png");      //gives path to the file with picture to method from Sprite class that will load the image
                break;
            case 2:
                loadImage("/tableBooks.png");
                break;
            case 3:
                loadImage("/books.png");
                break;
            case 4:
                loadImage("/backpack.png");
                break;
        }
        getImageDimensions();    //method from Sprite class
        y-=60+height;
        if(i==1 || i==2){
            height-=110;
        }
    }

    public void move(){     //changes x position of obstacle

        x -= OBSTACLE_SPEED;

        if (x < 0 - getWidth()){     //checks if the image is still on the screen
            visible = false;
        }
    }
}