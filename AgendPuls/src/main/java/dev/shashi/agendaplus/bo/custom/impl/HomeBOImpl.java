package dev.shashi.agendaplus.bo.custom.impl;

import dev.shashi.agendaplus.bo.custom.HomeBO;
import dev.shashi.agendaplus.dao.DAOFactory;
import dev.shashi.agendaplus.dao.custom.TaskDAO;
import dev.shashi.agendaplus.dto.TaskDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class HomeBOImpl implements HomeBO {
    private TaskDAO taskDAO = (TaskDAO) DAOFactory.getDAO(DAOFactory.DAOType.TASK);

    public int getTaskCount(LocalDate date) throws SQLException, ClassNotFoundException {
        return taskDAO.getTaskCount(date);
    }

    public int getDoneTaskCOunt(LocalDate date) throws SQLException, ClassNotFoundException {
        return taskDAO.getDoneTaskCount(date);
    }
}
