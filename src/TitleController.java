import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class TitleController {

    @FXML
    private int selection = 0;
    @FXML
    private ImageView spaceBackground;
    @FXML
    private Label startLabel;
    @FXML
    private Label BGMLabel;
    @FXML
    private Label settingsLabel;
    @FXML
    private Label quitLabel;

    @FXML
    public void initialize(){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(5000), spaceBackground);
        translateTransition.setFromY(-800);
        translateTransition.setToY(0);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(0), spaceBackground);
        translateTransition2.setFromY(0);
        translateTransition2.setToY(-800);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, translateTransition2);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();
    }

    @FXML
    public void startLabelEnter(){
        selection = 1;
        startLabel.setTextFill(Color.RED);
    }
    @FXML
    public void startLabelExit(){
        selection = 0;
        startLabel.setTextFill(Color.WHITE);
    }

    @FXML
    public void BGMLabelEnter(){
        selection = 2;
        BGMLabel.setTextFill(Color.RED);
    }
    @FXML
    public void BGMLabelExit(){
        selection = 0;
        BGMLabel.setTextFill(Color.WHITE);
    }

    @FXML
    public void settingsLabelEnter(){
        selection = 3;
        settingsLabel.setTextFill(Color.RED);
    }
    @FXML
    public void settingsLabelExit(){
        selection = 0;
        settingsLabel.setTextFill(Color.WHITE);
    }

    @FXML
    public void quitLabelEnter(){
        selection = 4;
        quitLabel.setTextFill(Color.RED);
    }

    @FXML
    public void quitLabelExit(){
        selection = 0;
        quitLabel.setTextFill(Color.WHITE);
    }

    @FXML
    public void quitLabelClick(){
        System.exit(0);
    }
}
