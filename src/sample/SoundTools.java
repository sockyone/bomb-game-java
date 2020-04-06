package sample;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public final class SoundTools {
    private static String musicFile = "src/sample/sound/move.wav";

    public static class SoundManager {
        private boolean isRunning;
        private boolean isContinuously;
        private Clip sound;
        public SoundManager(String url,boolean isContinuously) {
            this.isContinuously = isContinuously;
            try {
                AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(SoundTools.class.getResource(url));
                sound= AudioSystem.getClip();
                sound.open(audioInputStream);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            isRunning = false;
        }
        public void play() {
            if (isContinuously)
                sound.loop(Clip.LOOP_CONTINUOUSLY);
            else sound.loop(1);
            sound.start();
            isRunning = true;
        }
        public void stop() {
            sound.stop();
            isRunning = false;
        }
        public boolean isRunning() {
            return isRunning;
        }
    }
    public final static SoundManager MOVESOUND = new SoundManager("sound/move.wav",true);
    public final static SoundManager BOMBPUTSOUND = new SoundManager("sound/set_boom.wav",false);
    public final static SoundManager ITEMSOUND = new SoundManager("sound/item.wav",false);
    public final static SoundManager BOMBBANG = new SoundManager("sound/boom_bang.wav",false);

    private SoundTools() {}

}
