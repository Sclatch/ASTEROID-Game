import java.io.*;
import java.net.*;


public class LeaderBoardClient {

    static DataInputStream isFromServer;
    static DataOutputStream osToServer;
    static int[] scores = new int[]{0,0,0,0,0,0,0,0};

    static String[] names = new String[]{"","","","","","","",""};

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
            PrintWriter writer = new PrintWriter(osToServer, true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(isFromServer));

            for(int i=0; i<8; i++) {
                scores[i]=Integer.parseInt(reader.readLine());
                names[i]=reader.readLine();
            }
            System.out.println("update2");
            writer.println(Integer.toString(Main.score));
            writer.println(Main.username);

            System.out.println("flush 11");
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}