package dev.shashi.agendaplus.dao.custom.impl;

import dev.shashi.agendaplus.dao.SQLUtil;
import dev.shashi.agendaplus.dao.custom.ReminderDAO;
import dev.shashi.agendaplus.entitys.Reminder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReminderDAOImpl implements ReminderDAO {
    @Override
    public boolean saveReminder(Reminder reminder) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO reminders (task_id, taskTitle, reminderDate,reminderTime) VALUES (?, ?, ?,?)",
                reminder.getTaskId(),
                reminder.getTaskTitle(),
                new java.sql.Date(reminder.getDate().getTime()),
                reminder.getReminderTime()
        );
    }

    @Override
    public List<Reminder> getReminders(LocalDate filterDate) throws SQLException, ClassNotFoundException {
        List<Reminder> reminders = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT * FROM reminders WHERE reminderDate = ? AND shown = 0 AND reminderTime > NOW()",java.sql.Date.valueOf(filterDate));
        while (rs.next()) {
            reminders.add(new Reminder(rs.getInt("task_id"),
                    rs.getString("taskTitle"),
                    rs.getDate("reminderDate").toLocalDate(),
                    rs.getTime("reminderTime").toLocalTime()));
        }
        return reminders;
    }

    @Override
    public boolean setStatus(int taskId, boolean status) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE reminders SET shown = ? WHERE task_id = ?",status,taskId);
    }
}
