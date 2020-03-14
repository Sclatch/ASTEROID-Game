import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Space Breakout");
        stage.setWidth(1280);
        stage.setHeight(800);
        //stage.setFullScreen(true);

        Intro intro = new Intro();
        intro.play(stage);

//        HBox hBox = new HBox();
//        hBox.setPadding(new Insets(50, 50, 50, 50));
//        hBox.setAlignment(Pos.CENTER);
//        Scene scene = new Scene(hBox);
//        stage.setScene(scene);
//        stage.show();
    }
}
