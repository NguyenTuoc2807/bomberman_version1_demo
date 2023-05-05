package uet.oop.bomberman.Sound;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {
    public static Media inGame = new Media(new File("res/sound/ingame.mp3").toURI().toString());
    public static AudioClip level = new AudioClip(new File("res/Sound/level.wav").toURI().toString());
    public static AudioClip placeBomb = new AudioClip(new File("res/sound/placebomb.wav").toURI().toString());

    public static AudioClip explosion = new AudioClip(new File("res/sound/explosion.wav").toURI().toString());
    public static Media heading = new Media(new File("res/sound/heading.mp3").toURI().toString());
    public static AudioClip die = new AudioClip(new File("res/sound/die.wav").toURI().toString());
    public static AudioClip takePower = new AudioClip(new File("res/sound/takepower.wav").toURI().toString());

    private static final MediaPlayer headingPlayer = new MediaPlayer(heading);
    private static final MediaPlayer inGamePlayer = new MediaPlayer(inGame);

    public static void playSfx(Media media) {
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    public static void playSfx(AudioClip audio) {
        audio.play();
    }

    public static void playInGame() {
        inGamePlayer.play();
    }

    public static void playHeading() {
        headingPlayer.play();
    }

    public static void stopHeading() {
        headingPlayer.stop();
    }

    public static void stopInGame() {
        inGamePlayer.stop();
    }

}
