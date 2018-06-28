package com.editor.layout;

import com.editor.world.Tile;
import com.editor.world.TileMap;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EditView {

    private static final double PADDING = 4;

    private TileMap tileMap;
    private Painter painter;
    private ImageView currentPaintBox;

    private static final double MAP_WEIGHT = 70;
    private static final double MENU_WEIGHT = 30;

    private static final double SCREEN_HEIGHT = 100;

    private static final double MENU_BUTTONS = 5;

    private Window stage;
    private Media boom;

    public EditView(TileMap tileMap, Painter painter, Window stage) {
        this.tileMap = tileMap;
        this.painter = painter;
        this.stage = stage;
        String bip = "src/main/resources/sound/boom.mp3";
        boom = new Media(new File(bip).toURI().toString());
    }

    public Scene getScene() {
        return new Scene(createRoot());
    }

    private Parent createRoot() {
        GridPane gridPane = new GridPane();
        GridConstraints.column(gridPane, MAP_WEIGHT, MENU_WEIGHT);
        GridConstraints.row(gridPane, SCREEN_HEIGHT);

        GridPane map = createMapView();
        GridPane menu = createMenuView();
        gridPane.addRow(0, map, menu);
        return gridPane;
    }

    private GridPane createMenuView() {
        GridPane gridPane = new GridPane();
        double constraintValue = SCREEN_HEIGHT / TileType.values().length + MENU_BUTTONS;
        int i = 0;
        for (TileType tileType : TileType.values()) {
            GridConstraints.row(gridPane, constraintValue);
            gridPane.add(createMenuRow(tileType, tileType.toString()), 0, i);
            i++;
        }
        for (int j = 0; j < MENU_BUTTONS; j++) {
            GridConstraints.row(gridPane, constraintValue);
        }
        createCurrentPaintView();
        gridPane.add(createCurrentPaintView(), 0, i++);
        gridPane.add(createFillButton(), 0, i++);
        gridPane.add(createClearButton(), 0, i++);
        gridPane.add(createExportButton(), 0, i++);
        gridPane.add(createImportButton(), 0, i);
        return gridPane;
    }

    private Button createClearButton() {
        Button button = new Button("Clear map");
        button.setOnMousePressed(event -> {
            for (int x = 0; x < tileMap.getWidth(); x++) {
                for (int y = 0; y < tileMap.getHeight(); y++) {
                    TileType.EMPTY.paint(tileMap.getTile(x, y), painter);
                }
            }
        });
        return button;
    }

    private Button createFillButton() {
        Button button = new Button("Fill map with current paint");
        button.setOnMousePressed(event -> {
            for (int x = 0; x < tileMap.getWidth(); x++) {
                for (int y = 0; y < tileMap.getHeight(); y++) {
                    painter.paint(tileMap.getTile(x, y));
                }
            }
        });
        return button;
    }

    private Button createExportButton() {
        Button button = new Button("Export map");
        button.setOnMousePressed(event -> {
            if (painter.canMapBeExported()) {
                showSaveFileDialog();
            } else {
                showImportImpossibleAlert();
            }
        });
        return button;
    }

    private Button createImportButton() {
        Button button = new Button("Import map");
        button.setOnMousePressed(event -> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TM files (*.tm)", "*.tm");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                tileMap.importFromJSON(readFile(file), painter);
            }
        });
        return button;
    }

    private String readFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuilder.append(text);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

    private void showSaveFileDialog() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TM files (*.tm)", "*.tm");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveTextToFile(tileMap.exportAsJSON(), file);
        }
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void showImportImpossibleAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("The map can't be exported. You need to set player's spawn points and eagles");

        alert.showAndWait();
    }

    private HBox createCurrentPaintView() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(PADDING));
        currentPaintBox = new ImageView(painter.getCurrentPaint().getImage());
        Label label = new Label("Current paint");
        hBox.getChildren().addAll(currentPaintBox, label);
        return hBox;
    }

    private HBox createMenuRow(TileType tileType, String message) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(PADDING));
        ImageView imageView = new ImageView(tileType.getImage());
        hBox.setOnMouseClicked(event -> {
            painter.setCurrentPaint(tileType);
            currentPaintBox.setImage(tileType.getImage());
        });
        Label label = new Label(message);
        hBox.getChildren().addAll(imageView, label);
        return hBox;
    }

    private GridPane createMapView() {
        GridPane gridPane = setMapPane();

        for (int x = 0; x < tileMap.getWidth(); x++) {
            for (int y = 0; y < tileMap.getHeight(); y++) {
                Tile tile = tileMap.getTile(x, y);
                StackPane stackPane = wrapImageViewInStackPane(tile, tile.getImageView());
                gridPane.add(stackPane, x, y);
            }
        }
        return gridPane;
    }

    private GridPane setMapPane() {
        double GAP_SIZE = 1;
        GridPane gridPane = new GridPane();
        gridPane.setVgap(GAP_SIZE);
        gridPane.setHgap(GAP_SIZE);
        gridPane.setPadding(new Insets(PADDING));
        gridPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    private StackPane wrapImageViewInStackPane(Tile tile, ImageView imageView) {
        StackPane stackPane = new StackPane(imageView);
        imageView.setOnMouseClicked(createImageViewListener(tile));
        stackPane.setMinSize(0, 0);
        imageView.fitHeightProperty().bind(stackPane.heightProperty());
        imageView.fitWidthProperty().bind(stackPane.widthProperty());
        return stackPane;
    }

    private EventHandler<MouseEvent> createImageViewListener(Tile tile) {
        return event -> {
            painter.paint(tile);
            playExplosion();
        };
    }

    private void playExplosion() {
        new Thread(() -> {
            MediaPlayer mediaPlayer = new MediaPlayer(boom);
            mediaPlayer.play();
            mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
        }).start();
    }

}
