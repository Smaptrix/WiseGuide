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
     * mediaPlayer provides the controls for playing media.
     */
    MediaPlayer mediaPlayer;

    /**
     * mediaView provides a view of the media in the player.
     */
    MediaView mediaView;


    /**
     * mediaPane is the pane layout of the player.
     */
    Pane mediaPane;

    /**
     * controlBar is the media controls at the bottom of the window.
     */
    MediaBar controlBar;

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

        mediaConstructor(toString(filePath));

        mediaPlayerConstructor(media);

        mediaViewConstructor(mediaPlayer, heightIn, widthIn);

        mediaBarConstructor();
    }

    /**
     * <p>
     * Returns the given file path as a string.
     * </p>
     *
     * @param filePath Is the file path of the desired media.
     * @return filePath as a string.
     */
    private String toString(File filePath) {
        return filePath.toURI().toString();
    }

    /**
     * <p>
     * Creates a Media object from the file at the given string.
     * </p>
     *
     * @param source Media file path as a string.
     */
    private void mediaConstructor(String source) {
        try {
            this.media = new Media(source);

            if (media.getError() == null) {
                media.setOnError(new Runnable() {
                    public void run() {
                        // Handle asynchronous error in Media object.
                        System.out.println("Media object is null!");
                    }
                });
            } else {
                // Handle synchronous error creating Media.
                System.out.println("Media object constructor failed!");
            }
        } catch (Exception mediaException) {
            // Handle exception in Media constructor.
            System.out.println("Media object constructor failed!");
        }
    }

    /**
     * <p>
     * Creates a MediaPlayer using the given media object.
     * </p>
     *
     * @param media The media object the player is created for.
     */
    private void mediaPlayerConstructor(Media media) {
        try {
            this.mediaPlayer = new MediaPlayer(media);

            if (mediaPlayer.getError() == null) {
                mediaPlayer.setOnError(new Runnable() {
                    public void run() {
                        // Handle asynchronous error in MediaPlayer object.
                        System.out.println("MediaPlayer object is null!");
                    }
                });
            } else {
                // Handle synchronous error creating MediaPlayer.
                System.out.println("MediaPlayer object constructor failed!");
            }
        } catch (Exception mediaPlayerException) {
            // Handle exception in MediaPlayer constructor.
            System.out.println("MediaPlayer object constructor failed!");
        }
    }

    /**
     * <p>
     * Creates a MediaView of the given size for the given MediaPlayer.
     * </p>
     *
     * @param mediaPlayer The media player the media view is created for.
     * @param heightIn    Desired height of the media view.
     * @param widthIn     Desired width of the media view.
     */
    private void mediaViewConstructor(MediaPlayer mediaPlayer, int heightIn, int widthIn) {
        try {
            this.mediaView = new MediaView(mediaPlayer);
            this.mediaView.setPreserveRatio(true);
            this.mediaView.setSmooth(true);
            this.mediaView.setFitHeight(heightIn);
            this.mediaView.setFitWidth(widthIn);

            this.mediaView.setOnError(new EventHandler<MediaErrorEvent>() {
                public void handle(MediaErrorEvent t) {
                    // Handle asynchronous error in MediaView.
                    System.out.println("MediaView object is null!");
                }
            });

        } catch (Exception mediaViewException) {
            // Handle exception in MediaPlayer constructor.
            System.out.println("MediaView object constructor failed!");
        }
    }

    /**
     * <p>
     * Creates the media control bar for the media view and media player.
     * </p>
     */
    private void mediaBarConstructor() {
        try {
            this.mediaPane = new Pane();
            this.mediaPane.getChildren().add(mediaView);
            setCenter(mediaPane);
        } catch (Exception mediaPaneException) {
            // Handle exception in MediaPane constructor.
            System.out.println("MediaPane object constructor failed!");
        }
        try {
            this.controlBar = new MediaBar(mediaPlayer);
            setBottom(controlBar);
            setStyle("-fx-background-color:#474746");
        } catch (Exception barPaneException) {
            // Handle exception in MediaBar constructor.
            System.out.println("MediaBar object constructor failed!");
        }
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