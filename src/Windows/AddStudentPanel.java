package Windows;

import Charactor.Student;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddStudentPanel {
    private Dialog<Student> dialog = new Dialog<>();

    public AddStudentPanel(List<String> columnNames) {
        dialog.setTitle("添加");
        dialog.setHeaderText(null);
        StudentUI userInterface = new StudentUI(columnNames);
        dialog.getDialogPane().setContent(userInterface);
        TextField textField[] = userInterface.getFields();
        ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.setResizable(true);
        dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);
        dialog.setResultConverter(param -> {
            if (param == submit) {
                Student res = new Student();
                List<Object> tmp = new ArrayList<>();
                for (TextField aTextField : textField)
                    tmp.add(aTextField.getText());
                res.setList(tmp);
                return res;
            }
            return null;
        });
    }

    public Optional<Student> showAndWait() {
        return dialog.showAndWait();
    }
}
