package dev.shashi.agendaplus.dao;

import dev.shashi.agendaplus.dao.custom.impl.*;

public class DAOFactory {
    public  enum DAOType{
        ATTACHMENT , NOTE,REMINDER,TASK ,USER

    }

    public static SuperDAO getDAO(DAOType daoType) {
        switch (daoType) {
            case NOTE:
                return new NoteDAOImpl();
            case TASK:
                return new TaskDAOImpl();
            case REMINDER:
                return new ReminderDAOImpl();
            case ATTACHMENT:
                return new AtachmentDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }

    }
}
