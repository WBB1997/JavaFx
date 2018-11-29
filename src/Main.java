import Adapter.PageControl;
import Charactor.Student;
import Util.InfoManager;
import Util.XmlUtil;
import Windows.AddStudentPanel;
import Windows.UpdateStudentPanel;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {
    private boolean flag = false;
    private TextField index;
    private TableView<Student> table;
    private Button search;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("学生成绩管理系统");
        Scene scene = new Scene(new BorderPane(), 600, 400, Color.RED);
        // 上半部分
        HBox top = new HBox();
        top.setSpacing(3);
        ComboBox<String> menu = new ComboBox<>();
        menu.getItems().addAll(((InfoManager) XmlUtil.getBean()).getColumnNames());
        menu.setValue(menu.getItems().get(0));
        TextField input = new TextField();
        // 设定输入框占用所有空余空间
        HBox.setHgrow(input, Priority.ALWAYS);
        search = new Button("查找");
        Button flash = new Button("刷新");
        top.getChildren().addAll(menu, input, search, flash);
        //添加table
        table = new TableView<>();
        //定义数据模型
        ObservableList<Student> data = FXCollections.observableArrayList(((InfoManager) XmlUtil.getBean()).get());
        //创建，添加列
        table.getColumns().addAll(createTableColumn());
        //添加模型
        table.setItems(data);
        //设定自适应列宽
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // 添加右键菜单
        table.setContextMenu(new popMenu());
        table.setId("table-view");
        //下半部分
        HBox bottom = new HBox();
        bottom.setSpacing(3);
        Button first = new Button("首页");
        Button last = new Button("尾页");
        Button prev = new Button("上一页");
        Button next = new Button("下一页");
        Button add = new Button("添加");
        index = new TextField("1");
        index.setPrefColumnCount(2);
        index.setAlignment(Pos.CENTER);
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(first, prev, index, next, add);
        // 添加布局
        ((BorderPane) scene.getRoot()).setTop(top);
        ((BorderPane) scene.getRoot()).setCenter(table);
        ((BorderPane) scene.getRoot()).setBottom(bottom);
        stage.setScene(scene);
        // 加载css样式
//        scene.getStylesheets().add( getClass().getResource("tableview.css") .toExternalForm());
        stage.show();
        // 触发事件
        flash.setOnAction(event -> {
            PageControl.setFirstPage();
            table.setItems(FXCollections.observableArrayList(((InfoManager) XmlUtil.getBean()).get()));
            input.setText("");
            flag = false;
        });
        search.setOnAction(event -> {
            String args = menu.getValue();
            InfoManager infoManager = (InfoManager) XmlUtil.getBean();
            String keyword = input.getText();
            if (keyword.length() > 0) {
                if (!flag)
                    PageControl.setFirstPage();
                table.setItems(FXCollections.observableArrayList((infoManager.get(args, input.getText()))));
                flag = true;
            } else {
                table.setItems(FXCollections.observableArrayList((infoManager.get())));
                flag = false;
            }
            index.setText(Integer.toString(PageControl.getPage() + 1));
        });
        first.setOnAction(event -> {
            PageControl.setFirstPage();
            dosomething();
        });
        last.setOnAction(event -> {
            PageControl.setLastPage();
            dosomething();
        });
        next.setOnAction(event -> {
            PageControl.setNextPage();
            dosomething();
        });
        prev.setOnAction(event -> {
            PageControl.setPrevPage();
            dosomething();
        });
        add.setOnAction(event -> {
            AddStudentPanel addStudentPanel = new AddStudentPanel(((InfoManager) XmlUtil.getBean()).getColumnNames());
            Optional<Student> result;
            boolean flag = true;
            while (flag) {
                result = addStudentPanel.showAndWait();
                if (result.isPresent()) {
                    Student student = result.get();
                    String state = ((InfoManager) XmlUtil.getBean()).add(student);
                    if ("添加成功！".equals(state))
                        flag = false;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText(state);
                    alert.showAndWait();
                }else
                    flag = false;
            }
            dosomething();
        });
    }

    private List<TableColumn<Student, String>> createTableColumn() {
        InfoManager infoManager = (InfoManager) XmlUtil.getBean();
        ArrayList<TableColumn<Student, String>> res = new ArrayList<>();
        List<String> columnNames = infoManager.getColumnNames();
        for (String str : columnNames) {
            TableColumn<Student, String> tmp = new TableColumn<>(str);
            tmp.setCellValueFactory(new PropertyValueFactory<>(str.substring(0, 1) + str.toLowerCase().substring(1)));
            tmp.setMinWidth(tmp.getPrefWidth());
            res.add(tmp);
        }
        return res;
    }

    private void dosomething(){
        if(!flag) {
            table.setItems(FXCollections.observableArrayList((((InfoManager) XmlUtil.getBean()).get())));
            index.setText(Integer.toString(PageControl.getPage() + 1));
        }else
            search.fire();
    }

    private class popMenu extends ContextMenu {
        private popMenu() {
            MenuItem delMenuItem = new MenuItem("删除");
            MenuItem updMenuItem = new MenuItem("更新");
            delMenuItem.setOnAction(event -> {
                ReadOnlyProperty<Student> itemProperty = table.getSelectionModel().selectedItemProperty();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Information");
                alert.setHeaderText("警告");
                alert.setContentText("确定要删除吗？");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && (result.get() == ButtonType.OK)) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText(((InfoManager) XmlUtil.getBean()).delete(itemProperty.getValue()));
                    alert.showAndWait();
                    dosomething();
                }
                event.consume();
            });
            updMenuItem.setOnAction(event -> {
                ReadOnlyProperty<Student> itemProperty = table.getSelectionModel().selectedItemProperty();
                UpdateStudentPanel updateStudentPanel = new UpdateStudentPanel(((InfoManager) XmlUtil.getBean()).getColumnNames(), itemProperty.getValue());
                Optional<Student> result;
                boolean flag = true;
                while (flag) {
                    result = updateStudentPanel.showAndWait();
                    if (result.isPresent()) {
                        Student student = result.get();
                        String state = ((InfoManager) XmlUtil.getBean()).update(itemProperty.getValue(), student);
                        if ("更新成功！".equals(state))
                            flag = false;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText(state);
                        alert.showAndWait();
                    }else
                        flag = false;
                }
                dosomething();
            });

            getItems().add(delMenuItem);
            getItems().add(updMenuItem);
        }
    }
}