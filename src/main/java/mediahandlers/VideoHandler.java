/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Lee Foster
    Date Created:   10/03/2022
    Last Updated:   22/04/2022
 */

package mediahandlers;

import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

/**
 * VideoHandler class handles the creation, destruction and playback of video files.
 */
public class VideoHandler extends BorderPane {

    /**
     * media stores a media resource.
     */
    Media media;

    /**
     * mediaPlayer is the media player.
     */
    MediaPlayer mediaPlayer;

    /**
     * mediaView provides a view of the media in the player.
     */
    MediaView mediaView;

    /**
     * source is the file path stored as a string.
     */
    String source;

    /**
     * mpane is the pane layout of the player.
     */
    Pane mpane;

    /**
     * bar is the media controls at the bottom of the window.
     */
    MediaBar bar;

    /**
     * <p>
     * Video handler constructor.
     * </p>
     *
     * @param filePath Is the file path of the desired media.
     * @param heightIn Desired initial media height.
     * @param widthIn  Desired initial media width.
     */
    public VideoHandler(File filePath, int heightIn, int widthIn) {

        toString(filePath);

        try {
            this.media = new Media(source);
            if (media.getError() == null) {
                media.setOnError(new Runnable() {
                    public void run() {
                        // Handle asynchronous error in Media object.
                    }
                });
                try {
                    this.mediaPlayer = new MediaPlayer(media);
                    if (mediaPlayer.getError() == null) {
                        mediaPlayer.setOnError(new Runnable() {
                            public void run() {
                                // Handle asynchronous error in MediaPlayer object.
                            }
                        });
                        this.mediaView = new MediaView(mediaPlayer);
                        this.mediaView.setPreserveRatio(true);
                        this.mediaView.setSmooth(true);
                        this.mediaView.setFitHeight(heightIn);
                        this.mediaView.setFitWidth(widthIn);
                        this.mediaView.setOnError(new EventHandler<MediaErrorEvent>() {
                            public void handle(MediaErrorEvent t) {
                                // Handle asynchronous error in MediaView.
                            }
                        });
                        mpane = new Pane();
                        mpane.getChildren().add(mediaView);
                        setCenter(mpane);
                        bar = new MediaBar(mediaPlayer);
                        setBottom(bar);
                        setStyle("-fx-background-color:#474746");
                        mediaPlayer.play();
                    } else {
                        // Handle synchronous error creating MediaPlayer.
                    }
                } catch (Exception mediaPlayerException) {
                    // Handle exception in MediaPlayer constructor.
                }
            } else {
                // Handle synchronous error creating Media.
            }
        } catch (Exception mediaException) {
            // Handle exception in Media constructor.
        }
    }

    /**
     * <p>
     * Stores the file path as a string in the source variable.
     * </p>
     *
     * @param filePath Is the file path of the desired media.
     */
    private void toString(File filePath) {
        source = filePath.toURI().toString();
    }

    /**
     * <p>
     * Method returns the mediaView
     * </p>
     *
     * @return mediaView of type MediaView
     */
    public MediaView getMediaView() {
        return mediaView;
    }

    /**
     * <p>
     * Method plays the video.
     * </p>
     */
    public void play() {
        this.mediaPlayer.play();
    }

    /**
     * <p>
     * Method pauses the video.
     * </p>
     */
    public void pause() {
        this.mediaPlayer.pause();
    }

    /**
     * <p>
     * Method stops the video.
     * </p>
     */
    public void stop() {
        this.mediaPlayer.stop();
    }

    /**
     * <p>
     * Method to set the mediaView window height.
     * </p>
     *
     * @param height Desired height of the window.
     */
    public void setFitHeight(int height) {
        this.mediaView.setFitHeight(height);
    }

    /**
     * <p>
     * Method to set the mediaView window width.
     * </p>
     *
     * @param width Desired width of the window.
     */
    public void setFitWidth(int width) {
        this.mediaView.setFitWidth(width);
    }
}