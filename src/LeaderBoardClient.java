import java.io.*;
import java.net.*;


public class LeaderBoardClient {

    static DataInputStream isFromServer;
    static DataOutputStream osToServer;
    static int[] scores = new int[]{0,0,0,0,0,0,0,0};
    static String[] names = new String[]{"","","","","","","",""};
    static PrintWriter writer;
    static BufferedReader reader;

    public LeaderBoardClient() {
        try {
            System.out.println("1");
            Socket connectToServer = new Socket("localhost", 8000);

            //connect socketIO streams
            isFromServer = new DataInputStream(connectToServer.getInputStream());
            osToServer = new DataOutputStream(connectToServer.getOutputStream());

            writer = new PrintWriter(osToServer, true);
            reader = new BufferedReader(new InputStreamReader(isFromServer));
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] getScores() {
        return scores;
    }

    public String[] getNames() {
        return names;
    }

    public void connectToServer() {
        try {
            for(int i=0; i<8; i++) {
                scores[i]=Integer.parseInt(reader.readLine());
                names[i]=reader.readLine();
            }
            writer.println(Main.score);
            writer.println(Main.username);

            System.out.println(Main.score + " " + Main.username);
            for(int i=0; i<8; i++) {
                System.out.println(scores[i]+ " " +names[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}