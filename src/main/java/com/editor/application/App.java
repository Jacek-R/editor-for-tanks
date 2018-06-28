package com.editor.application;

import com.editor.layout.EditView;
import com.editor.layout.Painter;
import com.editor.world.TileMap;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class App extends Application {

    private int width = 13;
    private int height = 13;

    @Override
    public void start(Stage primaryStage) {
        TileMap tileMap = new TileMap(width, height);
        Painter painter = new Painter();
        EditView editView = new EditView(tileMap, painter, primaryStage);

        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tanks editor");
        primaryStage.setScene(editView.getScene());
        primaryStage.show();

        String bip = "src/main/resources/sound/trololo.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.setCycleCount(Integer.MAX_VALUE);
        mediaPlayer.play();
    }

    public void run(String[] args) {
        launch(args);
    }
}
