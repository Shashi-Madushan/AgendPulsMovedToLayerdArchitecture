package dev.shashi.agendaplus.bo.custom.impl;

import dev.shashi.agendaplus.bo.custom.RegisterBO;
import dev.shashi.agendaplus.dao.SQLUtil;
import dev.shashi.agendaplus.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterBOImpl implements RegisterBO {
    public boolean saveUser(String userName, String password, int secQuestionNo, String secQuesAnswer) throws SQLException, ClassNotFoundException {

         return SQLUtil.execute("INSERT INTO user VALUES(?, ?, ?,?)",userName,password,secQuestionNo,secQuesAnswer);

    }
}
