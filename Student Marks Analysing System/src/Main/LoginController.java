package Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    /*
     *FXML DECLARATIONS
     */
    @FXML
    private TextField usernameText;

    @FXML
    private TextField passwordText;

    @FXML
    private Label loginLabel;

    @FXML
    private Label errorText;

    @FXML
    private Button reportBtn;

    //Main Button
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        con = Database.getConnection();

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        String sql = "SELECT * FROM login WHERE USERID = ? and PASS = ?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                errorText.setText("Enter Correct Details");
            } else {

                if (username.equals("admin")) {
                    Parent table = FXMLLoader.load(getClass().getResource("StudentTable.fxml"));
                    Scene tableScene = new Scene(table);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Student Marks Analyzing System - Students Edit");
                    tableScene.getStylesheets().add
                            (Login.class.getResource("theme.css").toExternalForm());
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
                    stage.hide();
                    stage.setScene(tableScene);
                    stage.show();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Report Button
    @FXML
    private void reportBtnClicked(ActionEvent event) throws Exception {
        Parent table = FXMLLoader.load(getClass().getResource("RollNoSelection.fxml"));
        Scene tableScene = new Scene(table);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Student Marks Analyzing System - Reports");
        tableScene.getStylesheets().add
                (Login.class.getResource("theme.css").toExternalForm());
        stage.hide();
        stage.setScene(tableScene);
        stage.show();
    }
}
