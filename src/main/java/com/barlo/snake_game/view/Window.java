package com.barlo.snake_game.view;


import com.barlo.snake_game.controll.SnakeFXController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Window extends Application {

    /* Basic class for Window. It takes FXML GUI from file */

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SnakeFXMLUI.fxml"));
        SnakeFXController snakeFXController = new SnakeFXController();

        loader.setController(snakeFXController);

        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Snake Game");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        /* EventHandler check's Keyboard events. It ignores all KeyEvents except Arrow Keys */

        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.UP ||
                    event.getCode() == KeyCode.DOWN ||
                    event.getCode() == KeyCode.LEFT ||
                    event.getCode() == KeyCode.RIGHT) GameLoop.setNextDirection(event.getCode());
            }
        });

        primaryStage.show();

    }

}
