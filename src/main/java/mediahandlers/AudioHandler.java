package mediahandlers;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;

/**
 * AudioHandler class extends the MediaManager class and creates an Audio object.
 */
public class AudioHandler extends BorderPane {

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

    public MediaView audioView;

    public Media media;

    public Pane mpane;

    private File filePath;

    private  MediaBar bar;

    /**
     * <p>
     * Constructor for the AudioHandler class.
     * </p>
     *
     * @param filePathIn The string of the path where the audio file is located.
     */
    public AudioHandler(File filePathIn) {
        this.filePath = filePathIn;

        int heightIn = 40;
        int widthIn = 400;

        Media audioFile = new Media(new File(String.valueOf(this.filePath)).toURI().toString());
        this.audioPlayer = new MediaPlayer(audioFile);

        this.audioView = new MediaView(audioPlayer);
        this.audioView.setFitHeight(heightIn);
        this.audioView.setFitWidth(widthIn);

        mpane = new Pane();
        mpane.getChildren().add(audioView);
        setCenter(mpane);
        bar = new MediaBar(audioPlayer);
        setBottom(bar);
    }

    /**
     * <p>
     * loads the audio and sets the start time and the autoplay parameters of the audioPlayer.
     * </p>
     */
    public void load() {
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
