
package Main;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StudentTableController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField rollnoText;
    @FXML
    private TextField nameText;
    @FXML
    private Button backBtn;
    @FXML
    private Button savebtn;
    @FXML
    private TableView<StudentDetails> tableUser;
    @FXML
    private TableColumn<StudentDetails, Integer> columnRollno;
    @FXML
    private TableColumn<StudentDetails, String> columnName;
    @FXML
    private TableColumn<StudentDetails, String> columnEnglish;
    @FXML
    private TableColumn<StudentDetails, String> columnHindi;
    @FXML
    private TableColumn<StudentDetails, String> columnMathematics;
    @FXML
    private TableColumn<StudentDetails, String> columnScience;
    @FXML
    private Button btnLoad;

    //Initialize observable list to hold out database data
    private ObservableList<StudentDetails> data;
    private Connection con = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = Database.getConnection();

        //Update the table to allow for the fields to be editable

        tableUser.setEditable(true);
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnEnglish.setCellFactory(TextFieldTableCell.forTableColumn());
        columnHindi.setCellFactory(TextFieldTableCell.forTableColumn());
        columnMathematics.setCellFactory(TextFieldTableCell.forTableColumn());
        columnScience.setCellFactory(TextFieldTableCell.forTableColumn());

        loadDataFromDatabase();
    }

    /**
     * These methods will allow the user to double click on a cell and update
     * the MARKS of the STUDENT
     */
    public void changeName(TableColumn.CellEditEvent edittedCell) {
        StudentDetails studentSelected = tableUser.getSelectionModel().getSelectedItem();
        studentSelected.setName(edittedCell.getNewValue().toString());
    }

    public void changeEnglishMarks(TableColumn.CellEditEvent edittedCell) {
        StudentDetails studentSelected = tableUser.getSelectionModel().getSelectedItem();
        studentSelected.setEnglish(edittedCell.getNewValue().toString());


    }

    public void changeHindiMarks(TableColumn.CellEditEvent edittedCell) {
        StudentDetails studentSelected = tableUser.getSelectionModel().getSelectedItem();
        studentSelected.setHindi(edittedCell.getNewValue().toString());
    }

    public void changeMathematicsMarks(TableColumn.CellEditEvent edittedCell) {
        StudentDetails studentSelected = tableUser.getSelectionModel().getSelectedItem();
        studentSelected.setMathematics(edittedCell.getNewValue().toString());
    }

    public void changeScienceMarks(TableColumn.CellEditEvent edittedCell) {
        StudentDetails studentSelected = tableUser.getSelectionModel().getSelectedItem();
        studentSelected.setScience(edittedCell.getNewValue().toString());
    }

    //Save Button
    @FXML
    private void saveData(ActionEvent Event) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        for (int i = 0; i < tableUser.getItems().size(); i++) {
            try {
                String sql = "UPDATE students SET  Name = ?, English = ?, Hindi = ?, Mathematics = ?, Science = ? where RollNo = ?";
                preparedStatement = con.prepareStatement(sql);
                if(Integer.parseInt(columnEnglish.getCellData(i))>100 || Integer.parseInt(columnHindi.getCellData(i))>100 || Integer.parseInt(columnMathematics.getCellData(i))>100 || Integer.parseInt(columnScience.getCellData(i))>100)
                {
                    Stage window = new Stage();
                    //Block events to other windows
                    window.initModality(Modality.APPLICATION_MODAL);
                    window.setTitle("Error");
                    window.setMinWidth(250);

                    Label label = new Label();
                    label.setText("Invalid Marks Input");
                    Button closeButton = new Button("Close");
                    closeButton.setOnAction(e -> window.close());

                    VBox layout = new VBox(10);
                    layout.getChildren().addAll(label, closeButton);
                    layout.setAlignment(Pos.CENTER);

                    //Display window and wait for it to be closed before returning
                    Scene scene = new Scene(layout);
                    scene.getStylesheets().add
                        (Login.class.getResource("theme.css").toExternalForm());
                    window.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
                    window.setScene(scene);
                    window.showAndWait();
                    break;
                }
                preparedStatement.setString(1, columnName.getCellData(i));
                preparedStatement.setString(2, columnEnglish.getCellData(i));
                preparedStatement.setString(3, columnHindi.getCellData(i));
                preparedStatement.setString(4, columnMathematics.getCellData(i));
                preparedStatement.setString(5, columnScience.getCellData(i));
                preparedStatement.setInt(6, columnRollno.getCellData(i));
                preparedStatement.executeUpdate();
                } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    //Load Data To Table From Database
    private void loadDataFromDatabase() {
        try {
            data = FXCollections.observableArrayList();
            // Execute query and store result in a resultset
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM students");
            while (rs.next()) {
                //get string from database
                data.add(new StudentDetails(rs.getInt(1) , rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
        
        //Set cell value factory to tableview.
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnRollno.setCellValueFactory(new PropertyValueFactory<>("rollno"));
        columnEnglish.setCellValueFactory(new PropertyValueFactory<>("english"));
        columnHindi.setCellValueFactory(new PropertyValueFactory<>("hindi"));
        columnMathematics.setCellValueFactory(new PropertyValueFactory<>("mathematics"));
        columnScience.setCellValueFactory(new PropertyValueFactory<>("science"));
        
        tableUser.setItems(null);
        tableUser.setItems(data);

    }

    //Add Button
    @FXML
    public void addBtnClicked(ActionEvent Event)
    {
        StudentDetails newStudent = new StudentDetails();
        newStudent.setRollno(Integer.parseInt(rollnoText.getText()));
        newStudent.setName(nameText.getText());
        tableUser.getItems().add(newStudent);

        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO students (RollNo, Name, English, Hindi, Mathematics, Science) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(rollnoText.getText()));
            preparedStatement.setString(2, nameText.getText());
            preparedStatement.setString(3, "NA");
            preparedStatement.setString(4, "NA");
            preparedStatement.setString(5, "NA");
            preparedStatement.setString(6, "NA");
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }
        rollnoText.clear();
        nameText.clear();
    }

    //Delete Button
    @FXML
    public void deleteBtnClicked(ActionEvent Event)
    {
        StudentDetails selectedStudent;
        selectedStudent = tableUser.getSelectionModel().getSelectedItem();
        int rollnoRow = selectedStudent.getRollno();

        PreparedStatement preparedStatement = null;
        try {
            String sql = "DELETE FROM students WHERE RollNo = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, rollnoRow);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }
        loadDataFromDatabase();

    }

    //Back Button
    @FXML
    private void backToLogin(ActionEvent event) throws Exception
    {
        Parent table = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene tableScene = new Scene(table);
        tableScene.getStylesheets().add
                (Login.class.getResource("theme.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Student Marks Analyzing System - Login");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.hide();
        stage.setScene(tableScene);
        stage.show();
    }

}
