package game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
	
	private static Clip clipLoop;
	private static Clip clipPlay;
	
	public static boolean thrustSound = false;
	public static boolean soundLoop = false;
	
    public static void playLoop(String filename) {
        try
        {
        	clipLoop = AudioSystem.getClip();
            clipLoop.open(AudioSystem.getAudioInputStream(new File(filename)));
            clipLoop.start();
            clipLoop.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
    
    public static void play(String filename) {
        try
        {
        	clipPlay = AudioSystem.getClip();
            clipPlay.open(AudioSystem.getAudioInputStream(new File(filename)));
            clipPlay.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
    
    public static void stop() {
        try
        {
            clipLoop.stop();
            clipLoop.close();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
}

