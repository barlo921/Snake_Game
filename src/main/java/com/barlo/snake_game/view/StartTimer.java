package com.barlo.snake_game.view;


import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class StartTimer {

    private static final int TRANSITION_DURATION_MILLIS = 1000;

    @FXML
    private Text counter;
    @FXML
    ScaleTransition st;
    @FXML
    FadeTransition fd;

    private GameLoop gameLoop;

    private int counterText = 2;

    public StartTimer(final Text counter, final GameLoop gameLoop) {
        this.counter = counter;
        this.gameLoop = gameLoop;

        st = new ScaleTransition(Duration.millis(TRANSITION_DURATION_MILLIS), counter);
        fd = new FadeTransition(Duration.millis(TRANSITION_DURATION_MILLIS), counter);
    }

    public void startTimer() {

        counter.setDisable(false);
        counter.setOpacity(100);

        playScaleTransition();
        playFadeTransition();
        setOnFinish();

    }

    private void playScaleTransition() {
        st.setByX(1.5f);
        st.setByY(1.5f);
        st.setAutoReverse(true);
        st.play();
    }

    private void playFadeTransition() {
        fd.setFromValue(100);
        fd.setToValue(0);
        fd.setAutoReverse(true);
        fd.play();
    }

    private void setOnFinish() {
        st.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (counterText > 0) {

                    counter.setScaleX(1);
                    counter.setScaleY(1);

                    counter.setText(String.valueOf(counterText--));

                    playScaleTransition();
                    playFadeTransition();

                } else {
                    gameLoop.addFood();
                    gameLoop.start();
                }

            }
        });

        fd.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (counterText > 0) {
                    counter.setOpacity(100);
                }
            }
        });

    }

}
