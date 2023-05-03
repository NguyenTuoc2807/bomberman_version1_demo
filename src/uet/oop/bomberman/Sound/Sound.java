package uet.oop.bomberman.Sound;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {
    public static Media media;
    public static Media ingame;
    public static AudioClip level;
    public static AudioClip placebomb;
    public static AudioClip explosion;
    public static Media heading;
    public static AudioClip die;
    public static AudioClip takePower;

    private static MediaPlayer backgroundPlayer;

    public static void playSfx(AudioClip audioClip) {
        audioClip.play();
    }

    public static void playSfx(Media media) {
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void playBackground(Media media) {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
        backgroundPlayer = new MediaPlayer(media);
        backgroundPlayer.play();
    }

    public static void stopBackground() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
            backgroundPlayer = null;
        }
    }


    public static void loadMedia() {
        placebomb = new AudioClip(new File("res/sound/placebomb.wav").toURI().toString());
        explosion = new AudioClip(new File("res/sound/explosion.wav").toURI().toString());
        heading = new Media(new File("res/sound/heading.mp3").toURI().toString());
        die = new AudioClip(new File("res/sound/die.wav").toURI().toString());
        ingame = new Media(new File("res/sound/ingame.mp3").toURI().toString());
        takePower = new AudioClip(new File("res/sound/takepower.wav").toURI().toString());
    }

}
