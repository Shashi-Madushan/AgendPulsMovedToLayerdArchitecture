package dev.shashi.agendaplus.dao.custom;

import dev.shashi.agendaplus.dao.SuperDAO;
import dev.shashi.agendaplus.entitys.Reminder;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ReminderDAO extends SuperDAO {
    boolean saveReminder(Reminder reminder) throws SQLException, ClassNotFoundException;

    List<Reminder> getReminders(LocalDate filterDate) throws SQLException, ClassNotFoundException;

    boolean setStatus(int taskId, boolean status) throws SQLException, ClassNotFoundException;
}
