package dev.shashi.agendaplus.dao.custom;

import dev.shashi.agendaplus.dao.SuperDAO;

import java.io.IOException;
import java.sql.SQLException;

public interface UserDAO extends SuperDAO {
    public boolean checkCredential(String userName, String pw) throws SQLException, IOException, ClassNotFoundException;
}
