package dev.shashi.agendaplus.bo.custom.impl;

import dev.shashi.agendaplus.bo.custom.TaskPaneBO;
import dev.shashi.agendaplus.dao.DAOFactory;
import dev.shashi.agendaplus.dao.custom.TaskDAO;

import java.sql.SQLException;

public class TaskPaneBOImpl implements TaskPaneBO {
    private TaskDAO taskDAO = (TaskDAO) DAOFactory.getDAO(DAOFactory.DAOType.TASK);
    public boolean updateTaskStatus(int taskId, boolean doneStatus) throws SQLException, ClassNotFoundException {
        return taskDAO.updateTaskStatus(taskId,doneStatus);
    }
}
