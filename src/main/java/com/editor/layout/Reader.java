package com.editor.layout;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class Reader {

    static Image getImage(String path) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return image;
    }
}
