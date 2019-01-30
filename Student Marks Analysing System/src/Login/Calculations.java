package Login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class Calculations implements Initializable {
    static int RollNo;

    //Get RollNo From Select RollNo Combobox
    public static void getRollNo(int roll)
    {
        RollNo = roll;
    }

    @FXML
    private Label name;

    @FXML
    private Button backBtn;

    @FXML
    private Label rollno;

    @FXML
    private Label totalmarks;

    @FXML
    private Label percentage;

    @FXML
    private Label result;

    @FXML
    public TableView<Student> table;

    @FXML
    private TableColumn<Student, String> subjectColumn;

    @FXML
    private TableColumn<Student, Integer> marksColumn;

    @FXML
    private TableColumn<Student, String> resultColumn;

    //Back Button
    @FXML
    private void back(ActionEvent event) throws Exception{
        Parent table = FXMLLoader.load(getClass().getResource("RollNoSelection.fxml"));
        Scene tableScene = new Scene(table);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Student Marks Analyzing System - Select RollNo");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.hide();
        stage.setScene(tableScene);
        stage.show();
    }

    public ObservableList<Student> loadTable()
    {
        ObservableList<Student> student = FXCollections.observableArrayList();
        student.add(new Student("English",english,subjectResult(english)));
        student.add(new Student("Hindi",hindi,subjectResult(hindi)));
        student.add(new Student("Mathematics",mathematics,subjectResult(mathematics)));
        student.add(new Student("Science",science,subjectResult(science)));
        return student;
    }

    //Declarations
    int english, hindi, mathematics, science;
    String Name;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        marks();
        display();

        table.setItems(loadTable());
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        marksColumn.setCellValueFactory(new PropertyValueFactory<>("marks"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
    }

    //Get Marks From Database
    public void marks()
    {
        con = Database.getConnection();
        try {
            String sql = "SELECT Name, English, Hindi, Mathematics, Science FROM students WHERE RollNo = "+ RollNo;
            preparedStatement = con.prepareStatement(sql);
            rs =  preparedStatement.executeQuery();
            if(rs.next()) {
                Name = rs.getString("Name");
                english = Integer.parseInt(rs.getString("English"));
                hindi = Integer.parseInt(rs.getString("Hindi"));
                mathematics = Integer.parseInt(rs.getString("Mathematics"));
                science = Integer.parseInt(rs.getString("Science"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Total Marks
    private int totalMarks()
    {
        return english + hindi + mathematics + science ;
    }

    //Percentage
    private int Percentage()
    {
        return (totalMarks()*100)/400;
    }
    private String subjectResult(int marks)
    {
        if(marks>=33)
        {
            return "PASS";
        }
        else
        {
            return "FAIL";
        }
    }

    //Result
    private String Result()
    {
        if(Percentage()>=33)
        {
            return "PASS";
        }
        else
        {
            return "FAIL";
        }
    }

    //Display Values
    public void display()
    {
        name.setText(Name);
        rollno.setText(String.valueOf(RollNo));
        totalmarks.setText(String.valueOf(totalMarks()));
        result.setText(Result());
        percentage.setText(String.valueOf(Percentage())+"%");

    }

}


