package Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RollNoSelection implements Initializable {

    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    loadRollno();
    }

    @FXML
    private ComboBox<Integer> rollnoDropdown;

    @FXML
    private Button submitBtn;

    @FXML
    private Button backBtn;

    //Load RollNo in Combobox
    @FXML
    public void loadRollno()
    {
        Connection con = null;
        con = Database.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT RollNo FROM students";
            preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                rollnoDropdown.getItems().add(rs.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //Submit Button
    @FXML
    private void submitRollno(ActionEvent event) throws Exception
    {
        Calculations.getRollNo(rollnoDropdown.getValue());
        Parent table = FXMLLoader.load(getClass().getResource("ReportCard.fxml"));
        Scene tableScene = new Scene(table);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Student Marks Analyzing System - Report Card");
        tableScene.getStylesheets().add
                (Login.class.getResource("theme.css").toExternalForm());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.hide();
        stage.setScene(tableScene);
        stage.show();
    }

    //Back Button
    @FXML
    private void back(ActionEvent event) throws Exception{
        Parent table = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene tableScene = new Scene(table);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Student Marks Analyzing System - Login");
        tableScene.getStylesheets().add
                (Login.class.getResource("theme.css").toExternalForm());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.hide();
        stage.setScene(tableScene);
        stage.show();
    }
}
