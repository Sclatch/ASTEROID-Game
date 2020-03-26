import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Music{
    private File soundFile = null;
    private Clip clip = null;

    public void setSoundFile(String filename) {
        soundFile = new File("src/Misc/Music/" + filename + ".wav");
    }

    public void openSoundFile() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying(){
        return clip.isRunning();
    }

    public void play() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play(long time) {
        clip.setMicrosecondPosition(time);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void stop() {
        if(clip.isActive()) {
            clip.stop();
            clip.close();
        }
    }

    public long pause() {
        if(clip.isActive()) {
            long t = clip.getMicrosecondPosition();
            clip.stop();
            clip.close();
            return t;
        }
        return 0;
    }
}