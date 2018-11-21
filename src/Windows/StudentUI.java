package Windows;


import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


class StudentUI extends BorderPane {
    private TextField fields[];
    private int size;

    StudentUI(List<String> arrayString) {
        size = arrayString.size();
        Label[] labels = new Label[size];
        fields = new TextField[size];
        for (int i = 0; i < size; i++)
            labels[i] = new Label(arrayString.get(i));
        for (int i = 0; i < size; i++)
            fields[i] = new TextField();
        VBox innerPanelCenter = new VBox();
        innerPanelCenter.setSpacing(3);
        innerPanelCenter.setAlignment(Pos.CENTER_LEFT);
        for (int i = 0; i < size; i++) {
            innerPanelCenter.getChildren().add(labels[i]);
            innerPanelCenter.getChildren().add(fields[i]);
        }
        setCenter(innerPanelCenter);
    }

    TextField[] getFields() {
        return fields;
    }

    void clearFields() {
        for (int i = 0; i < size; i++)
            fields[i].setText("");
    }

    void setFieldValues(List<String> strings) throws IllegalArgumentException {
        if (strings.size() != size)
            throw new IllegalArgumentException("数据个数不匹配");
        for (int i = 0; i < size; i++)
            fields[i].setText(strings.get(i));
    }

    List<String> getFieldValues() {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < size; i++)
            values.add(fields[i].getText());
        return values;
    }
}
