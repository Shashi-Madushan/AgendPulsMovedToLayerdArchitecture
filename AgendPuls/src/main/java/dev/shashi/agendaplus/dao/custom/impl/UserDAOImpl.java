package dev.shashi.agendaplus.dao.custom.impl;

import dev.shashi.agendaplus.dao.SQLUtil;
import dev.shashi.agendaplus.dao.custom.UserDAO;
import dev.shashi.agendaplus.db.DbConnection;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    public boolean checkCredential(String userName, String pw) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT password FROM user WHERE userName = ?",userName);

        if (resultSet.next()) {
            String dbPw = resultSet.getString("password");
            if (pw.equals(dbPw)) {
               return true;
            } else {
                new Alert(Alert.AlertType.ERROR, "Sorry! Password is incorrect!").show();
            }
        } else {

            new Alert(Alert.AlertType.INFORMATION, "Sorry! User Name can't be found!").show();

        }
        return false;
    }

}
