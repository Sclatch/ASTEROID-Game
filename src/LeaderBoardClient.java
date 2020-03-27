import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*public class LeaderBoardClient extends Application {

    DataOutputStream osToServer;
    DataInputStream isFromServer;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        try {
            // Create a socket to connect to the server
            Socket connectToServer = new Socket("localhost", 8000);

            // Create an input stream to receive data from the server
            isFromServer = new DataInputStream(connectToServer.getInputStream());

            // Create an output stream to send data to the server
            osToServer = new DataOutputStream(connectToServer.getOutputStream());
        }
        catch (IOException ex) {
            ta.appendText(ex.toString() + '\n');
        }
    }

    public static void connectToServer() {
        try {
            // Get the weight from the text field
            double weight = Double.parseDouble(tfWeight.getText().trim());

            // Get the height from the text field
            double height = Double.parseDouble(tfHeight.getText());

            // Send the weight to the server
            osToServer.writeDouble(weight);

            // Send the height to the server
            osToServer.writeDouble(height);

            osToServer.flush();

            // Get BMI from the server
            String report = isFromServer.readUTF();

            ta.appendText("Weight: " + weight + "\nHeight: " + height + "\n");
            ta.appendText(report + '\n');
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/