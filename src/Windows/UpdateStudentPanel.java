package Windows;

import Charactor.Student;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UpdateStudentPanel{
    private Dialog<Student> dialog = new Dialog<>();

    public UpdateStudentPanel(List<String> columnNames, Student student) {
        dialog.setTitle("更新信息");
        dialog.setHeaderText(null);
        StudentUI userInterface = new StudentUI(columnNames);
        dialog.getDialogPane().setContent(userInterface);
        userInterface.setFieldValues(student.getList());
        TextField[] textField = userInterface.getFields();
        textField[0].setDisable(true);
        ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.setResizable(true);
        dialog.getDialogPane().getButtonTypes().addAll(submit, cancel);
        dialog.setResultConverter(param -> {
            if (param == submit) {
                Student res = new Student();
                List<String> tmp = new ArrayList<>();
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
