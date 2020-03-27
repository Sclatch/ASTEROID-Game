import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;


public class LeaderBoardServer extends Application {
    private TextArea ta = new TextArea();
    static int[] scores = new int[8];
    static int score = 0;
    static String[] names = new String[8];
    static String name;

    @Override
    public void start(Stage primaryStage) {
        ta.setWrapText(true);
        //Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(ta), 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Leaderboard Server");
        primaryStage.show();
        Thread t1 = new Thread(() -> connectToClient());
        t1.start();
    }

    private void save() {
        try {
            //get text data from f if it is valid
            PrintWriter output = new PrintWriter(new File("src/leaderboard.csv"));
            for (int i = 0; i < 8; i++) {
                //write new line of data
                output.println(scores[i]);
                output.println(names[i]);
            }
            output.close();
        }
        //if text file is invalid
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try {
            //get text data from f if it is valid
            Scanner input = new Scanner(new File("src/leaderboard.csv"));
            int i = 0;
            while (input.hasNextLine()) {
                //parse each line for data
                scores[i] = Integer.parseInt(input.nextLine());
                names[i] = input.nextLine();
                i++;
            }
            input.close();
        }
        //if text file is invalid
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectToClient() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            Platform.runLater(() -> ta.appendText("Server started at " +
                    new Date() + '\n'));

            Socket connectToClient = serverSocket.accept();

            //Display the client number
            Platform.runLater(() -> ta.appendText("Connected to a client at " +
                    new Date() + '\n'));

            //Create data input and output streams
            DataInputStream isFromClient = new DataInputStream(connectToClient.getInputStream());
            DataOutputStream osToClient = new DataOutputStream(connectToClient.getOutputStream());

            //Continuously server the client
            while (true) {

                load();
                PrintWriter writer = new PrintWriter(osToClient, true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(isFromClient));
                for(int i=0; i<8; i++) {
                    writer.println(scores[i]);
                    writer.println(names[i]);
                }

                save();

                score = Integer.parseInt(reader.readLine());
                name = reader.readLine();

                for (int i = 0; i < 8; i++) {
                    if (scores[i] < score) {
                        for (int j = 7; j > i; j--) {
                            scores[j] = scores[j - 1];
                            names[j] = names[j-1];
                        }
                        scores[i] = score;
                        names[i] = name;
                        break;
                    }
                }
                save();

                load();

                Platform.runLater( () -> {

                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
