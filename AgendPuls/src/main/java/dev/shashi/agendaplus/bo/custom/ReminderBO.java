package dev.shashi.agendaplus.bo.custom;

import dev.shashi.agendaplus.bo.SuperBO;
import dev.shashi.agendaplus.dto.ReminderDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ReminderBO extends SuperBO {
    public  boolean saveReminder(ReminderDTO reminder) throws SQLException, ClassNotFoundException;

    public  void setStatus(int taskId, boolean status) throws SQLException, ClassNotFoundException;

    public List<ReminderDTO> getReminders(LocalDate filterDate) throws SQLException, ClassNotFoundException;
}
