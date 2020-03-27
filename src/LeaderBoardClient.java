import java.io.*;
import java.net.*;


public class LeaderBoardClient {

    static DataInputStream isFromServer;
    static DataOutputStream osToServer;
    static Socket connectToServer;
    static int[] scores = new int[8];
    static String[] names = new String[8];

    public static void connectToServer() {
        try {
            connectToServer = new Socket("localhost", 8000);

            //connect socketIO streams
            isFromServer = new DataInputStream(connectToServer.getInputStream());
            osToServer = new DataOutputStream(connectToServer.getOutputStream());

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendScore() {
        try {
            osToServer.writeUTF("sendScore");
            osToServer.write(Main.score);
            osToServer.writeUTF(Main.username);
            osToServer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getScores() {
        try {
            osToServer.writeUTF("getScores");
            scores[0] = isFromServer.read();
            names[0]=isFromServer.readUTF();
            scores[1] = isFromServer.read();
            names[1]=isFromServer.readUTF();
            scores[2] = isFromServer.read();
            names[2]=isFromServer.readUTF();
            scores[3] = isFromServer.read();
            names[3]=isFromServer.readUTF();
            scores[4] = isFromServer.read();
            names[4]=isFromServer.readUTF();
            scores[5] = isFromServer.read();
            names[5]=isFromServer.readUTF();
            scores[6] = isFromServer.read();
            names[6]=isFromServer.readUTF();
            scores[7] = isFromServer.read();
            names[7]=isFromServer.readUTF();
            osToServer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}