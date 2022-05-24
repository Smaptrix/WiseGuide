/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Lee Foster
    Date Created:   21/04/2022
    Last Updated:   22/04/2022
 */

package mediahandlers;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

/**
 * MediaBar class handles the media control buttons.
 */
public class MediaBar extends HBox {

    /**
     * time is the duration slider.
     */
    Slider time = new Slider();

    /**
     * vol is the volume slider.
     */
    Slider vol = new Slider();

    /**
     * PlayButton is the play/pause button.
     */
    Button PlayButton = new Button(">");

    /**
     * volume is the label for the volume slider.
     */
    Label volume = new Label("Volume: ");

    /**
     * player is the media player.
     */
    MediaPlayer player;

    /**
     * <p>
     *     Default media control bar constructor.
     * </p>
     *
     * @param play Is the media player object that the bar is built on.
     */
    public MediaBar(MediaPlayer play) {

        player = play;

        /**
         * Sets bar position.
         */
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 10, 5, 10));

        /**
         * Setting up volume slider.
         */
        vol.setPrefWidth(70);
        vol.setMinWidth(30);
        vol.setValue(100);
        HBox.setHgrow(time, Priority.ALWAYS);
        PlayButton.setPrefWidth(30);

        /**
         * Adding components to bar.
         */
        getChildren().add(PlayButton);
        getChildren().add(time);
        getChildren().add(volume);
        getChildren().add(vol);

        /**
         * Function to play the media depending on the state.
         */
        PlayButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                MediaPlayer.Status status = player.getStatus(); // To get the status of Player
                if (status == Status.PLAYING) {


                    if (player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())) {


                        player.seek(player.getStartTime());
                        player.play();
                    } else {

                        player.pause();

                        PlayButton.setText(">");
                    }
                }
                if (status == MediaPlayer.Status.HALTED || status == MediaPlayer.Status.STOPPED || status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.READY) {
                    player.play(); // Start the video
                    PlayButton.setText("||");
                }
            }
        });

        /**
         * Time slider update functionality.
         */
        player.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updatesValues();
            }
        });

        /**
         * Time slider jump to different part of the video functionality.
         */
        time.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (time.isPressed()) {
                    player.seek(player.getMedia().getDuration().multiply(time.getValue() / 100));
                }
            }
        });

        /**
         * Volume slider functionality.
         */
        vol.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (vol.isPressed()) {
                    player.setVolume(vol.getValue() / 100);
                }
            }
        });
    }

    /**
     * <p>
     *     Updates the time slider values.
     * </p>
     */
    protected void updatesValues() {
        Platform.runLater(new Runnable() {
            public void run() {
                time.setValue(player.getCurrentTime().toMillis() /
                        player.getTotalDuration()
                                .toMillis()
                        * 100);
            }
        });
    }
}
