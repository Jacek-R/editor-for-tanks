package com.editor.world;

import com.editor.layout.Painter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class TileMap {

    private int width;
    private int height;

    private Tile[][] tiles;

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        createEmptyMap();
    }

    private void createEmptyMap() {
        tiles = new Tile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = new Tile(x, y);
            }
        }
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String exportAsJSON() {
        ArrayList<Tile> tiles = new ArrayList<>(width * height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles.add(getTile(x, y));
            }
        }
        return new Gson().toJson(tiles);
    }

    public void importFromJSON(String json, Painter painter) {
        ArrayList<Tile> tiles = new ArrayList<>(width * height);
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonElements = jsonParser.parse(json).getAsJsonArray();

        for (int i = 0; i < jsonElements.size(); i++) {
            tiles.add(gson.fromJson(jsonElements.get(i), Tile.class));
        }

        for (Tile t : tiles) {
            Tile tile = getTile(t.getX(), t.getY());
            t.getTileType().paint(tile, painter);
        }
    }
}
