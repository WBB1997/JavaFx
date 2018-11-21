import Adapter.PageControl;
import Charactor.Student;
import Util.InfoManager;
import Util.XmlUtil;
import Windows.AddStudentPanel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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
            Optional<Pair<String, String>> result = addStudentPanel.showAndWait();
            result.ifPresent(new Consumer<Pair<String, String>>() {
                @Override
                public void accept(Pair<String, String> stringStringPair) {
                    System.out.println(stringStringPair.getKey() + " " +stringStringPair.getValue());
                }
            });
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
            InfoManager infoManager = (InfoManager) XmlUtil.getBean();
            table.setItems(FXCollections.observableArrayList((infoManager.get())));
            index.setText(Integer.toString(PageControl.getPage() + 1));
        }else {
            System.out.println("1");
            search.fire();
        }
    }
}