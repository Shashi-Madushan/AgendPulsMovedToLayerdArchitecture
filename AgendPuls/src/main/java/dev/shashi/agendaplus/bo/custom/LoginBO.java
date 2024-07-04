package dev.shashi.agendaplus.bo.custom;

import dev.shashi.agendaplus.bo.SuperBO;

import java.io.IOException;
import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    public boolean checkCredential(String userName, String pw) throws SQLException, IOException, ClassNotFoundException;
}
