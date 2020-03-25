import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Asteroids extends GameSceneController{

    private ImageView asteroids;

    public Asteroids(ImageView image) {
        asteroids = image;
    }

    @FXML
    protected void fallingAsteroids(){

        asteroids.setTranslateY(asteroids.getTranslateY() + 10);

        if(asteroids.getTranslateY() > 800){
            asteroids.setTranslateY(0);
        }
    }

    private ImageView getAsteroids(ImageView asteroids){
        return asteroids;
    }

    private int integerGenerator(int min,int max){
        return (int) (Math.random() * ((max - min) + 1) - min);
    }
}
