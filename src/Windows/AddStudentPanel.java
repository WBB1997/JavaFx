package Windows;

import Charactor.Student;
import Util.InfoManager;
import Util.XmlUtil;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddStudentPanel {
    private Dialog<Pair<String,String>> dialog = new Dialog<>();

    public AddStudentPanel(List<String> columnNames) {
        dialog.setTitle("添加信息");
        dialog.setHeaderText(null);
        StudentUI userInterface = new StudentUI(columnNames);
        dialog.getDialogPane().setContent(userInterface);
        TextField textField[] = userInterface.getFields();
        ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.setResizable(true);
        dialog.getDialogPane().getButtonTypes().addAll(submit,cancel);
        dialog.setResultConverter( param -> {
                if(param == submit){
                    return new Pair<>(textField[0].getText(), textField[1].getText());
                }
                return null;
        });
//        submit.addActionListener(e -> {
//            Student tmp = new Student();
//            InfoManager infoManager = (InfoManager) XmlUtil.getBean();
//            List<String> list = new ArrayList<>();
//            for (JTextField aTextField : textField)
//                list.add(aTextField.getText());
//            tmp.setList(list);
//            JOptionPane.showMessageDialog(null, infoManager.add(tmp), "信息", JOptionPane.WARNING_MESSAGE);
//        });
//        cancel.addActionListener(e -> this.dispose());
//        this.setSize(270, 28 * (textField.length  * 2 + 1));
//        this.setResizable(false);
//        this.setLocationRelativeTo(null);
//        this.setVisible(true);
    }

    public Optional<Pair<String,String>> showAndWait(){
        return dialog.showAndWait();
    }
}
