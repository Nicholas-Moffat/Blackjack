package blackjack;
import java.io.File;
import javax.sound.sampled.*;
import java.io.IOException;

public class Audio 
{
    private Clip background_music = null;
    private Clip card_flip = null;
    private Clip fart = null;
    private String card_flip_path = "CS-210-Data-Structures/blackjack/card_flip.wav";
    private String background_path = "CS-210-Data-Structures/blackjack/background.wav";
    private String fart_path = "CS-210-Data-Structures/blackjack/fart.wav";

    public Audio()
    {
        load_audio();
    }

    public void load_audio()
    {
        try
        {
            File card_flip_file = new File(card_flip_path);
            File music_file = new File(background_path);
            File fart_file = new File(fart_path);
            AudioInputStream music_stream = AudioSystem.getAudioInputStream(music_file);
            AudioInputStream card_stream = AudioSystem.getAudioInputStream(card_flip_file);
            AudioInputStream fart_stream = AudioSystem.getAudioInputStream(fart_file);
            background_music = AudioSystem.getClip();
            card_flip = AudioSystem.getClip();
            fart = AudioSystem.getClip();
            background_music.open(music_stream);
            card_flip.open(card_stream);
            fart.open(fart_stream);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            System.out.println("Error loading music " + e.getMessage());
        }
    }

    public void card_flip()
    {
        if (card_flip != null)
        {
            if (card_flip.isRunning())
            {
                card_flip.stop();
            }
            card_flip.setFramePosition(0);
            card_flip.start();
        }
    }

    public void fart()
    {
        if (fart != null)
        {
            if (fart.isRunning())
            {
                fart.stop();
            }
            fart.setFramePosition(0);
            fart.start();
        }
    }

    public void play_background()
    {
        if (background_music != null)
        {
            background_music.start();
            background_music.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop()
    {
        if (background_music != null)
        {
            background_music.stop();
        }
    }
}
