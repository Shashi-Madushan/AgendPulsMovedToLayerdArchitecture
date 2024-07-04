package dev.shashi.agendaplus.dao;



import dev.shashi.agendaplus.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtil {
    public static <T>T execute(String sql, Object... params) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }

        if (sql.toUpperCase().startsWith("SELECT") || sql.toUpperCase().startsWith("WITH")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return (T) resultSet;
        } else {
            return (T) Boolean.valueOf(preparedStatement.executeUpdate() > 0);
        }
    }
}
