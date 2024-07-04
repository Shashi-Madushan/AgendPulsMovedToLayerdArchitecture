package dev.shashi.agendaplus.bo.custom.impl;

import dev.shashi.agendaplus.bo.custom.CreateTAskViewBO;
import dev.shashi.agendaplus.dao.DAOFactory;
import dev.shashi.agendaplus.dao.custom.AtachmentDAO;
import dev.shashi.agendaplus.dao.custom.ReminderDAO;
import dev.shashi.agendaplus.dao.custom.TaskDAO;
import dev.shashi.agendaplus.db.DbConnection;
import dev.shashi.agendaplus.dto.AtachmentDTO;
import dev.shashi.agendaplus.dto.ReminderDTO;
import dev.shashi.agendaplus.dto.TaskDTO;
import dev.shashi.agendaplus.entitys.Atachment;
import dev.shashi.agendaplus.entitys.Reminder;

import dev.shashi.agendaplus.dao.TaskRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class CreateTaskViewBOImpl implements CreateTAskViewBO {
    ReminderDAO reminderDAO = (ReminderDAO) DAOFactory.getDAO(DAOFactory.DAOType.REMINDER);
    AtachmentDAO atachmentDAO = (AtachmentDAO) DAOFactory.getDAO(DAOFactory.DAOType.ATTACHMENT);
    TaskDAO taskDAO = (TaskDAO) DAOFactory.getDAO(DAOFactory.DAOType.TASK);






    public  boolean trySaveTest(TaskDTO task, ArrayList<AtachmentDTO> atachmentsDTO, ReminderDTO reminder) throws SQLException {
        boolean result = false;


        Connection conn = DbConnection.getInstance().getConnection();

        try {
            conn.setAutoCommit(false); // disable auto-commit
            int taskId = TaskRepo.saveTask(task);
            if (taskId != -1) {
                boolean allAttachmentsSaved = true;
                for (AtachmentDTO atachment : atachmentsDTO) {
                    atachment.setTaskId(taskId);

                    if (!atachmentDAO.saveAtachments(new Atachment(atachment.getTaskId(),atachment.getFilePath()))) {
                        allAttachmentsSaved = false;
                        break;
                    }
                }
                if (allAttachmentsSaved) {
                    reminder.setTaskId(taskId);
                    if (reminderDAO.saveReminder(new Reminder(reminder.getTaskId(), reminder.getTaskTitle(),reminder.getDate().toLocalDate(),reminder.getReminderTime()))) {
                        conn.commit(); // commit all changes
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            conn.setAutoCommit(true); // re-enable auto-commit
        }

        return result;
    }
}
