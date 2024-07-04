package dev.shashi.agendaplus.bo.custom;

import dev.shashi.agendaplus.bo.SuperBO;

import java.sql.SQLException;

public interface TaskPaneBO extends SuperBO {
    public boolean updateTaskStatus(int taskId, boolean doneStatus) throws SQLException, ClassNotFoundException;
}
