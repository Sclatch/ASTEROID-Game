import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConstantSettings {

    public static int[] settingsValues = new int[3];

    public static void init(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/Misc/settings"));
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                settingsValues[i] = Integer.parseInt(line);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            settingsValues = new int[]{5, 5, 15};
        }
    }
}
