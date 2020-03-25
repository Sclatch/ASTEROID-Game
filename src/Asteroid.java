import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Asteroid {
    @FXML private ImageView asteroid1;
    @FXML private ImageView asteroid2;
    @FXML private ImageView asteroid3;
    @FXML private ImageView asteroid4;
    ImageView[] asteroidSkins = new ImageView[4];
    //asteroidSkins[0]=asteroid1;

    //[asteroid1, asteroid2, asteroid3, asteroid4]
    private ImageView asteroids;

    public Asteroid() {
        int skinCode = (int)Math.floor(Math.random()*(4));
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

}
