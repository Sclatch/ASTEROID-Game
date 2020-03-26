import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TitleController {

    private TranslateTransition titleTranslate;

    private static boolean playingPreview1 = false;
    private static boolean playingPreview2 = false;
    private static boolean playingPreview3 = false;
    private static long menuThemeTime;

    @FXML
    private VBox titleVBox;
    @FXML
    private GridPane ScoreGridPane;
    @FXML
    private GridPane BGMGridPane, settingsGridPane;
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
    private Label musicVolUP, musicVolDN, effectVolUP, effectVolDN, sensitivityUP, sensitivityDN, musicVolLabel, effectVolLabel, sensitivityLabel;
    @FXML
    private Label settingsMusicLabel, settingsEffectLabel, settingsSensitivityLabel;
    @FXML
    private Label score1,score2,score3,score4,score5,score6,score7,score8,score9,score10;
    @FXML
    private Label player1,player2,player3,player4,player5,player6,player7,player8,player9,player10;

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
        settingsGridPane.setDisable(true);

        BGMTitle1.setOpacity(0);
        BGMTitle2.setOpacity(0);
        BGMTitle3.setOpacity(0);
        BGMPreview1.setOpacity(0);
        BGMPreview2.setOpacity(0);
        BGMPreview3.setOpacity(0);
        BGMSelect1.setOpacity(0);
        BGMSelect2.setOpacity(0);
        BGMSelect3.setOpacity(0);
        musicVolDN.setOpacity(0);
        musicVolLabel.setOpacity(0);
        musicVolUP.setOpacity(0);
        effectVolDN.setOpacity(0);
        effectVolLabel.setOpacity(0);
        effectVolUP.setOpacity(0);
        sensitivityDN.setOpacity(0);
        sensitivityLabel.setOpacity(0);
        sensitivityUP.setOpacity(0);
        settingsMusicLabel.setOpacity(0);
        settingsEffectLabel.setOpacity(0);
        settingsSensitivityLabel.setOpacity(0);

        ScoreGridPane.setOpacity(0);
        ScoreGridPane.setDisable(true);
        score1.setOpacity(0);
        score2.setOpacity(0);
        score3.setOpacity(0);
        score4.setOpacity(0);
        score5.setOpacity(0);
        score6.setOpacity(0);
        score7.setOpacity(0);
        score8.setOpacity(0);
        score9.setOpacity(0);
        score10.setOpacity(0);

        player1.setOpacity(0);
        player2.setOpacity(0);
        player3.setOpacity(0);
        player4.setOpacity(0);
        player5.setOpacity(0);
        player6.setOpacity(0);
        player7.setOpacity(0);
        player8.setOpacity(0);
        player9.setOpacity(0);
        player10.setOpacity(0);
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

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(25000), spaceBackground);
        translateTransition.setFromY(-800);
        translateTransition.setToY(0);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(0), spaceBackground);
        translateTransition2.setFromY(0);
        translateTransition2.setToY(-800);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, translateTransition2);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        //start playing music for main menu
        if(!Main.firstStartup) {
            Main.music.stop();
            Main.music.setSoundFile("menuMusic");
            Main.music.openSoundFile();
            Main.music.play();
        }

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
    private void startLabelClick(MouseEvent event) {
        FadeTransition fadeTransition = new Intro().fadeOut(((Node)event.getSource()).getParent().getParent());
        fadeTransition.play();
        fadeTransition.setOnFinished(actionEvent -> {
            Scene gameScene = null;
            try {
                gameScene = FXMLLoader.load(getClass().getResource("GameScene.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            gameScene.getRoot().setOpacity(0);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(gameScene);
            FadeTransition fadeTransition2 = new Intro().fadeIn(gameScene.getRoot());
            fadeTransition2.play();
            stage.show();
        });
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
            score1.setOpacity(1);
            score2.setOpacity(1);
            score3.setOpacity(1);
            score4.setOpacity(1);
            score5.setOpacity(1);
            score6.setOpacity(1);
            score7.setOpacity(1);
            score8.setOpacity(1);
            score9.setOpacity(1);
            score10.setOpacity(1);

            player1.setOpacity(1);
            player2.setOpacity(1);
            player3.setOpacity(1);
            player4.setOpacity(1);
            player5.setOpacity(1);
            player6.setOpacity(1);
            player7.setOpacity(1);
            player8.setOpacity(1);
            player9.setOpacity(1);
            player10.setOpacity(1);
            ScoreGridPane.setOpacity(1);
            ScoreGridPane.setDisable(false);

            setScoreText();
        });
    }
    @FXML
    public void BGMLabelClick(){
        isolateLabel(BGMLabel);
        titleVBox.setDisable(true);
        titleTranslate = menuTransitionEnter(-182);
        titleTranslate.play();
        menuThemeTime = Main.music.pause();
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
    public void BGMPreview1Click() {
        if(!playingPreview1) {
            //if main menu music is playing, save it's timestamp
            if(playingPreview2 || playingPreview3) {
                Main.music.stop();
            }
            playingPreview1=true;
            playingPreview2=false;
            playingPreview3=false;

            Main.music.setSoundFile("LostFuture");
            Main.music.openSoundFile();
            Main.music.play();

        }
        else {
            playingPreview1=false;
            Main.music.stop();
        }
    }
    @FXML
    public void BGMPreview2Click() {
        if(!playingPreview2) {
            //if main menu music is playing, save it's timestamp
            if(playingPreview1 || playingPreview3) {
                Main.music.stop();
            }
            playingPreview1=false;
            playingPreview2=true;
            playingPreview3=false;

            Main.music.setSoundFile("SpaceFlight");
            Main.music.openSoundFile();
            Main.music.play();

        }
        else {
            playingPreview2=false;
            Main.music.stop();
        }
    }
    public void BGMPreview3Click() {
        if(!playingPreview3) {
            //if main menu music is playing, save it's timestamp
            if(playingPreview1 || playingPreview2) {
                Main.music.stop();
            }
            playingPreview1=false;
            playingPreview2=false;
            playingPreview3=true;

            Main.music.setSoundFile("Stardust");
            Main.music.openSoundFile();
            Main.music.play();

        }
        else {
            playingPreview3=false;
            Main.music.stop();
        }
    }
    @FXML
    public void BGMSelect1Click() {
        Main.selectedMusic = "LostFuture";
    }
    @FXML
    public void BGMSelect2Click() {
        Main.selectedMusic = "SpaceFlight";
    }
    @FXML
    public void BGMSelect3Click() {
        Main.selectedMusic = "Stardust";
    }
    @FXML
    public static void resetMusic() {
        menuThemeTime=0;
        playingPreview1=false;
        playingPreview2=false;
        playingPreview3=false;
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
            settingsGridPane.setDisable(false);
            musicVolDN.setOpacity(1);
            musicVolLabel.setOpacity(1);
            musicVolUP.setOpacity(1);
            effectVolDN.setOpacity(1);
            effectVolLabel.setOpacity(1);
            effectVolUP.setOpacity(1);
            sensitivityDN.setOpacity(1);
            sensitivityLabel.setOpacity(1);
            sensitivityUP.setOpacity(1);
            settingsMusicLabel.setOpacity(1);
            settingsEffectLabel.setOpacity(1);
            settingsSensitivityLabel.setOpacity(1);
        });
    }
    @FXML
    public void setScoreText(){
        ArrayList<Label> scoreList= new ArrayList<Label>(Arrays.asList(score1,score2,score3,score4,score5,score6,score7,score8,score9,score10));
        ArrayList<Label> playerList= new ArrayList<Label>(Arrays.asList(player1,player2,player3,player4,player5,player6,player7,player8,player9,player10));
        String csvFile = "src/misc/scores.csv";

        String line = "";

        int i = 0;
        try {

            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] csvVal = line.split(",");


                scoreList.get(i).setText(csvVal[1]);
                playerList.get(i).setText(csvVal[0]);

                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void backLabelClick(){
        reset();
        if(playingPreview1 || playingPreview2 || playingPreview3) {
            Main.music.stop();
        }
        if (!Main.music.isPlaying()) {
            Main.music.setSoundFile("menuMusic");
            Main.music.openSoundFile();
            Main.music.play(menuThemeTime);
        }

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
