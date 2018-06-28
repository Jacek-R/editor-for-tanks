package com.editor.layout;

import com.editor.world.Tile;

public class Painter {

    private TileType currentPaint = TileType.EMPTY;

    private boolean spawnOneOnMap;
    private boolean spawnTwoOnMap;
    private boolean eagleOneOnMap;
    private boolean eagleTwoOnMap;

    TileType getCurrentPaint() {
        return currentPaint;
    }

    void setCurrentPaint(TileType currentPaint) {
        this.currentPaint = currentPaint;
    }

    void paint(Tile tile) {
        currentPaint.paint(tile, this);
    }

    boolean isSpawnOneOnMap() {
        return spawnOneOnMap;
    }

    boolean isSpawnTwoOnMap() {
        return spawnTwoOnMap;
    }

    boolean isEagleOneOnMap() {
        return eagleOneOnMap;
    }

    boolean isEagleTwoOnMap() {
        return eagleTwoOnMap;
    }

    void setSpawnOneOnMap(boolean spawnOneOnMap) {
        this.spawnOneOnMap = spawnOneOnMap;
    }

    void setSpawnTwoOnMap(boolean spawnTwoOnMap) {
        this.spawnTwoOnMap = spawnTwoOnMap;
    }

    void setEagleOneOnMap(boolean eagleOneOnMap) {
        this.eagleOneOnMap = eagleOneOnMap;
    }

    void setEagleTwoOnMap(boolean eagleTwoOnMap) {
        this.eagleTwoOnMap = eagleTwoOnMap;
    }

    boolean canMapBeExported() {
        return spawnOneOnMap && spawnTwoOnMap && eagleOneOnMap && eagleTwoOnMap;
    }
}

