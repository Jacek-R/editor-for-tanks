package com.editor.layout;

import com.editor.world.Tile;
import javafx.scene.image.Image;

public enum TileType {
    WALL("src/main/resources/img/wall.png") {
        @Override
        public String toString() {
            return "Wall";
        }
    }, EMPTY("src/main/resources/img/empty.png") {
        @Override
        public String toString() {
            return "Empty";
        }
    }, WATER("src/main/resources/img/water.png") {
        @Override
        public String toString() {
            return "Water";
        }
    }, SOLID_WALL("src/main/resources/img/solid_wall.png") {
        @Override
        public String toString() {
            return "Solid wall";
        }
    }, FOREST("src/main/resources/img/forest.png") {
        @Override
        public String toString() {
            return "Forest";
        }
    },
    EAGLE_ONE("src/main/resources/img/eagle_one.png") {
        @Override
        public void paint(Tile tile, Painter painter) {
            if (!painter.isEagleOneOnMap()) {
                super.paint(tile, painter);
                painter.setEagleOneOnMap(true);
            }
        }

        @Override
        public String toString() {
            return "Eagle one";
        }
    }, EAGLE_TWO("src/main/resources/img/eagle_two.png") {
        @Override
        public void paint(Tile tile, Painter painter) {
            if (!painter.isEagleTwoOnMap()) {
                super.paint(tile, painter);
                painter.setEagleTwoOnMap(true);
            }
        }

        @Override
        public String toString() {
            return "Eagle two";
        }
    }, TANK_ONE_SPAWN("src/main/resources/img/spawn_one.png") {
        @Override
        public void paint(Tile tile, Painter painter) {
            if (!painter.isSpawnOneOnMap()) {
                super.paint(tile, painter);
                painter.setSpawnOneOnMap(true);
            }
        }

        @Override
        public String toString() {
            return "Spawn one";
        }
    }, TANK_TWO_SPAWN("src/main/resources/img/spawn_two.png") {
        @Override
        public void paint(Tile tile, Painter painter) {
            if (!painter.isSpawnTwoOnMap()) {
                super.paint(tile, painter);
                painter.setSpawnTwoOnMap(true);
            }
        }

        @Override
        public String toString() {
            return "Spawn two";
        }
    };

    private Image image;

    TileType(String imagePath) {
        image = Reader.getImage(imagePath);
    }

    public Image getImage() {
        return image;
    }

    public void paint(Tile tile, Painter painter) {
        TileType previousTile = tile.getTileType();
        checkIfSpecialTileIsPainted(painter, previousTile);
        tile.setTileType(this);
        tile.setImage(this.getImage());
    }

    private void checkIfSpecialTileIsPainted(Painter painter, TileType previousTile) {
        switch (previousTile) {
            case EAGLE_ONE:
                painter.setEagleOneOnMap(false);
                break;
            case EAGLE_TWO:
                painter.setEagleTwoOnMap(false);
                break;
            case TANK_ONE_SPAWN:
                painter.setSpawnOneOnMap(false);
                break;
            case TANK_TWO_SPAWN:
                painter.setSpawnTwoOnMap(false);
                break;
            default:
        }
    }
}
