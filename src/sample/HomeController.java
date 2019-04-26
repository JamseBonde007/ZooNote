package sample;

import bank_account.BankAccount;
import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sample.loginController.nameSurname;


public class HomeController implements Initializable {

    @FXML
    private Label balance;

    private String money;
    private PreparedStatement statement = null;
    private BankAccount acc;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection connection = ConnectionClass.getConnection();
        System.out.println("CONNECTION INICIATED");
        String sql = "SELECT stav FROM bankovy_ucet WHERE id IS 1";

        try{
            statement = connection.prepareStatement(sql);
            ResultSet setOfData = statement.executeQuery();

            acc = new BankAccount();
            //acc.setId(setOfData.getInt("id"));
            acc.setStav(setOfData.getDouble("stav"));

        }catch (SQLException ex) {
            Logger.getLogger(RequestUsersData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("CONNECTION CLOSED");
                } catch (SQLException ex) {
                    Logger.getLogger(RequestUsersData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RequestUsersData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        System.out.println(acc.getStav());
        money = String.valueOf(acc.getStav());

        balance.setText(money + "€");

    }
}