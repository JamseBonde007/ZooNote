package sample;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class usersController implements Initializable {

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> surnameColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> typeColumn;
    @FXML

    private ImageView deleteBtn;
    private ResultSet data, resultSetSize;
    private User userArray[];
    private String lastSelectedUsername = "",selectedUsername;
    private User selectedItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection connection = ConnectionClass.getConnection();
        System.out.println("CONNECTION INICIATED");
        String sqlQuery = "SELECT * FROM pouzivatel";
        String countQuery = "SELECT Count(*) FROM pouzivatel";

        try {
            PreparedStatement preparedCountStatement = connection.prepareStatement(countQuery);
            resultSetSize = preparedCountStatement.executeQuery();
            resultSetSize.next();
            PreparedStatement preparedQuery = connection.prepareStatement(sqlQuery);
            data = preparedQuery.executeQuery();
            insertIntoTable();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                System.out.println("CONNECTION CLOSED");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        deleteBtn.setVisible(false);
    }

    private void insertIntoTable() {
        try {
            Connection connection = ConnectionClass.getConnection();
            userArray = new User[Integer.parseInt(resultSetSize.getString(1))];
            data.next();
            for (int i = 0; i < userArray.length; i++) {
                if (!data.isClosed()) {
                    userArray[i] = new User(data.getString(5), data.getString(6), data.getString(2), data.getString(7), data.getString(4));
                    data.next();
                }
            }
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
            surnameColumn.setCellValueFactory(new PropertyValueFactory<>("Surname"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
            ObservableList<User> userObservableListList = FXCollections.observableArrayList(userArray);
            usersTable.setItems(userObservableListList);
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        }

    public void onAddUserBtnClick() throws IOException {
        Stage stage = (Stage) usersTable.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LayoutOther/AddUser.fxml"));
        stage.setTitle("Pridanie Pouzivatela");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showFromTable() {
        try {
            lastSelectedUsername = selectedUsername;
            selectedUsername = usersTable.getSelectionModel().getSelectedItem().getUsername();
            selectedItem = usersTable.getSelectionModel().getSelectedItem();
            deleteBtn.setVisible(true);
        } catch (NullPointerException e) {
            deleteBtn.setVisible(false);
            selectedUsername = "";
        }
        if (selectedUsername.equals(lastSelectedUsername)) {
            deleteBtn.setVisible(false);
            lastSelectedUsername = selectedUsername;
            selectedUsername = "";
            usersTable.getSelectionModel().clearSelection();
        }
    }

    public void deleteUser(){
        try {
            Connection connection = ConnectionClass.getConnection();
            System.out.println("CONNECTION INICIATED");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Upozornenie");
            alert.setHeaderText("Naozaj si prajete zmazat pouzivatela "+selectedUsername+"?");
            /*alert.setContentText("Are you ok with this?");*/

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                String deleteQuery = "DELETE  FROM pouzivatel WHERE username = ?";
                PreparedStatement preparedStatementToDelete = connection.prepareStatement(deleteQuery);
                preparedStatementToDelete.setString(1,selectedUsername);
                preparedStatementToDelete.executeUpdate();
            } else {
                System.out.println("dialog closed");
            }
            usersTable.getItems().remove(selectedItem);
            connection.close();
            System.out.println("CONNECTION CLOSED");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}



