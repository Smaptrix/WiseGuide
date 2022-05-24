package mediahandlers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

/**
 * AudioHandler class extends the MediaManager class and creates an Audio object.
 */
public class AudioHandler extends MediaManager{

    /**
     * autoplay allows the audio to be played upon loading the object (default is set to false).
     */
    private boolean autoplay = false;

    /**
     * startTime is the starting time of the audio clip in milliseconds (default is set to 0).
     */
    private int startTime = 0;

    /**
     * audioPlayer is a MediaPlayer object and controls the audio playback.
     */
    private MediaPlayer audioPlayer;


    /**
     * <p>
     * Constructor for the AudioHandler class.
     * </p>
     *
     * @param filePath The string of the path where the audio file is located.
     */
    public AudioHandler(String filePath) {
        super();
        setFilePath(filePath);
    }

    /**
     * <p>
     * loads in the audio file and sets the parameters of the audioPlayer (autoplay and startTime).
     * </p>
     */
    @Override
    public void load() {
        Media audioFile = new Media(new File(this.filePath).toURI().toString());
        this.audioPlayer = new MediaPlayer(audioFile);
        this.audioPlayer.setAutoPlay(this.autoplay);
        this.audioPlayer.setStartTime(Duration.millis(this.startTime));

    }

    /**
     * <p>
     * sets startTime.
     * </p>
     */
    public void setStartTime(int timeMilliSeconds) {
        this.startTime = timeMilliSeconds;
    }

    /**
     * <p>
     * gets startTime.
     * </p>
     */
    public int getStartTime() {
        return this.startTime;
    }

    /**
     * <p>
     * sets autoplay.
     * </p>
     */
    public void setAutoplay(boolean autoplay) {
        this.autoplay = autoplay;
    }

    /**
     * <p>
     * gets autoplay.
     * </p>
     */
    public boolean getAutoplay() {
        return this.autoplay;
    }

    /**
     * <p>
     * resumes the audio from where it was stopped.
     * </p>
     */
    public void play() {
        this.audioPlayer.play();
    }

    /**
     * <p>
     * pauses the audio.
     * </p>
     */
    public void pause() {
        this.audioPlayer.pause();
    }
}
