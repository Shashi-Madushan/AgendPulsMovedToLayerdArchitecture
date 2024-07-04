package dev.shashi.agendaplus.bo.custom.impl;

import dev.shashi.agendaplus.bo.custom.ReminderBO;
import dev.shashi.agendaplus.dao.DAOFactory;
import dev.shashi.agendaplus.dao.custom.ReminderDAO;
import dev.shashi.agendaplus.dto.ReminderDTO;
import dev.shashi.agendaplus.entitys.Reminder;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReminderBOImpl implements ReminderBO {
    ReminderDAO reminderDAO = (ReminderDAO) DAOFactory.getDAO(DAOFactory.DAOType.REMINDER);

    public  boolean saveReminder(ReminderDTO reminder) throws SQLException, ClassNotFoundException {
      return   reminderDAO.saveReminder(new Reminder(reminder.getTaskId(), reminder.getTaskTitle(), reminder.getDate().toLocalDate(),reminder.getReminderTime()));
    }
    public  void setStatus(int taskId, boolean status) throws SQLException, ClassNotFoundException {
        reminderDAO.setStatus(taskId, status);
    }
    public List<ReminderDTO> getReminders(LocalDate filterDate) throws SQLException, ClassNotFoundException {
        List<Reminder> reminders = reminderDAO.getReminders(filterDate);

        List<ReminderDTO> reminderDTOs = new ArrayList<>();
        for (Reminder reminder : reminders) {
            ReminderDTO reminderDTO = new ReminderDTO(reminder.getTaskId(), reminder.getTaskTitle(), reminder.getDate().toLocalDate(),reminder.getReminderTime());
            reminderDTO.setReminderTime(reminder.getReminderTime());
            reminderDTOs.add(reminderDTO);
        }

        return reminderDTOs;
    }


}
