package com.editor.world;

import com.editor.layout.TileType;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile {

    private TileType tileType;
    transient private ImageView imageView;

    private int x;
    private int y;

    Tile(int x, int y) {
        tileType = TileType.EMPTY;
        imageView = new ImageView(tileType.getImage());
        this.x = x;
        this.y = y;
    }

    Tile(int x, int y, TileType tileType) {
        this.x = x;
        this.y = y;
        this.tileType = tileType;
        imageView = new ImageView(tileType.getImage());
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImage(Image image) {
        Platform.runLater(() -> imageView.setImage(image));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
