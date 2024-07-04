package dev.shashi.agendaplus.bo.custom;

import dev.shashi.agendaplus.bo.SuperBO;

import java.sql.SQLException;

public interface RegisterBO extends SuperBO {
    public boolean saveUser(String userName, String password, int secQuestionNo, String secQuesAnswer) throws SQLException, ClassNotFoundException;
}
