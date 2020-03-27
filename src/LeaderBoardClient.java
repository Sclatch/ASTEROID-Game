import java.io.*;
import java.net.*;


public class LeaderBoardClient {

    static DataInputStream isFromServer;
    static DataOutputStream osToServer;
    static int[] scores = new int[8];
    static String[] names = new String[8];

    public LeaderBoardClient() {
        try {
            System.out.println("1");
            Socket connectToServer = new Socket("localhost", 8000);

            //connect socketIO streams
            isFromServer = new DataInputStream(connectToServer.getInputStream());
            osToServer = new DataOutputStream(connectToServer.getOutputStream());
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void connectToServer() {
        try {
            System.out.println("update1");
            scores[0] = isFromServer.readInt();
            names[0] = isFromServer.readUTF();
            scores[1] = isFromServer.readInt();
            names[1] = isFromServer.readUTF();
            scores[2] = isFromServer.readInt();
            names[2] = isFromServer.readUTF();
            scores[3] = isFromServer.readInt();
            names[3] = isFromServer.readUTF();
            scores[4] = isFromServer.readInt();
            names[4] = isFromServer.readUTF();
            scores[5] = isFromServer.readInt();
            names[5] = isFromServer.readUTF();
            scores[6] = isFromServer.readInt();
            names[6] = isFromServer.readUTF();
            scores[7] = isFromServer.readInt();
            names[7] = isFromServer.readUTF();
            System.out.println("flush 1");

            System.out.println("update2");
            osToServer.writeInt(Main.score);
            osToServer.writeUTF(Main.username);
            System.out.println("flush 11");
            osToServer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}