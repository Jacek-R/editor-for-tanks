package com.editor.layout;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

class GridConstraints {

    static void row(GridPane gridPane, double... values) {
        for (double value : values) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(value);
            gridPane.getRowConstraints().add(rowConstraints);
        }
    }

    static void column(GridPane gridPane, double... values) {
        for (double value : values) {
            ColumnConstraints rowConstraints = new ColumnConstraints();
            rowConstraints.setPercentWidth(value);
            gridPane.getColumnConstraints().add(rowConstraints);
        }
    }

}
