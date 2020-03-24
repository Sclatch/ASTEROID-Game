import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class TitleController {

    private TranslateTransition titleTranslate;

    @FXML
    private VBox titleVBox;
    @FXML
    private GridPane BGMGridPane;
    @FXML
    private ImageView spaceBackground;
    //Main menu elements
    @FXML
    private Label startLabel, leaderboardLabel, BGMLabel, settingsLabel, quitLabel;
    //Universal menu elements
    @FXML
    private Label backLabel;
    //BGM menu elements
    @FXML
    private Label BGMTitle1, BGMTitle2, BGMTitle3, BGMPreview1, BGMPreview2, BGMPreview3, BGMSelect1, BGMSelect2, BGMSelect3;
    //Settings menu elements
    @FXML
    private Label settingsTitle1, settingsTitle2, settingsTitle3;
    @FXML
    private void isolateLabel(Label label){
        startLabel.setOpacity(0);
        leaderboardLabel.setOpacity(0);
        BGMLabel.setOpacity(0);
        settingsLabel.setOpacity(0);
        quitLabel.setOpacity(0);
        label.setOpacity(1);
    }
    @FXML
    private void reset(){
        backLabel.setOpacity(0);
        backLabel.setDisable(true);
        BGMGridPane.setDisable(true);
        BGMTitle1.setOpacity(0);
        BGMTitle2.setOpacity(0);
        BGMTitle3.setOpacity(0);
        BGMPreview1.setOpacity(0);
        BGMPreview2.setOpacity(0);
        BGMPreview3.setOpacity(0);
        BGMSelect1.setOpacity(0);
        BGMSelect2.setOpacity(0);
        BGMSelect3.setOpacity(0);
        //        settingsTitle1.setOpacity(0);
//        settingsTitle2.setOpacity(0);
//        settingsTitle3.setOpacity(0);
    }
    @FXML
    private void goBack(){
        reset();
        startLabel.setOpacity(1);
        leaderboardLabel.setOpacity(1);
        BGMLabel.setOpacity(1);
        settingsLabel.setOpacity(1);
        quitLabel.setOpacity(1);
    }
    @FXML
    private TranslateTransition menuTransitionEnter(int toHeight){
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), titleVBox);
        transition.setFromY(0);
        transition.setToY(toHeight);
        return transition;
    }
    @FXML
    private TranslateTransition menuTransitionExit(){
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), titleVBox);
        transition.setFromY(titleVBox.getTranslateY());
        transition.setToY(0);
        return transition;
    }
    @FXML
    public void initialize(){
        goBack();

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
    private void labelEnter(MouseEvent event) {
        ((Label)event.getTarget()).setTextFill(Color.RED);
    }
    @FXML
    private void labelExit(MouseEvent event) {
        ((Label)event.getTarget()).setTextFill(Color.WHITE);
    }
    @FXML
    private void leaderboardLabelClick(){
        isolateLabel(leaderboardLabel);
        titleVBox.setDisable(true);
        titleTranslate = menuTransitionEnter(-135);
        titleTranslate.play();
        titleTranslate.setOnFinished(actionEvent -> {
            backLabel.setOpacity(1);
            backLabel.setDisable(false);
        });
    }
    @FXML
    public void BGMLabelClick(){
        isolateLabel(BGMLabel);
        titleVBox.setDisable(true);
        titleTranslate = menuTransitionEnter(-182);
        titleTranslate.play();
        titleTranslate.setOnFinished(actionEvent -> {
            backLabel.setOpacity(1);
            backLabel.setDisable(false);
            BGMTitle1.setOpacity(1);
            BGMTitle2.setOpacity(1);
            BGMTitle3.setOpacity(1);
            BGMPreview1.setOpacity(1);
            BGMPreview2.setOpacity(1);
            BGMPreview3.setOpacity(1);
            BGMSelect1.setOpacity(1);
            BGMSelect2.setOpacity(1);
            BGMSelect3.setOpacity(1);
            BGMGridPane.setDisable(false);
        });
    }
    @FXML
    public void settingsLabelClick(){
        isolateLabel(settingsLabel);
        titleVBox.setDisable(true);
        titleTranslate = menuTransitionEnter(-235);
        titleTranslate.play();
        titleTranslate.setOnFinished(actionEvent -> {
            backLabel.setOpacity(1);
            backLabel.setDisable(false);
//        settingsTitle1.setOpacity(1);
//        settingsTitle2.setOpacity(1);
//        settingsTitle3.setOpacity(1);
        });
    }
    @FXML
    private void backLabelClick(){
        reset();
        titleTranslate = menuTransitionExit();
        titleTranslate.play();
        titleTranslate.setOnFinished(actionEvent -> {
            goBack();
            titleVBox.setDisable(false);
        });
    }
    @FXML
    public void quitLabelClick(){
        System.exit(0);
    }

}
