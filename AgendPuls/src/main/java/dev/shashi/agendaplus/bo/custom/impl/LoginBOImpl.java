package dev.shashi.agendaplus.bo.custom.impl;

import dev.shashi.agendaplus.bo.custom.LoginBO;
import dev.shashi.agendaplus.dao.DAOFactory;
import dev.shashi.agendaplus.dao.custom.UserDAO;

import java.io.IOException;
import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDAO(DAOFactory.DAOType.USER);
    public boolean checkCredential(String userName, String pw) throws SQLException, IOException, ClassNotFoundException {
     return userDAO.checkCredential(userName,pw);
    }
}
