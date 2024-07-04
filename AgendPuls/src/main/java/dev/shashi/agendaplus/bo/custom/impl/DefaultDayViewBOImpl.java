package dev.shashi.agendaplus.bo.custom.impl;

import dev.shashi.agendaplus.bo.custom.DefaultDayViewBO;
import dev.shashi.agendaplus.dao.DAOFactory;
import dev.shashi.agendaplus.dao.custom.AtachmentDAO;
import dev.shashi.agendaplus.dao.custom.TaskDAO;
import dev.shashi.agendaplus.dto.AtachmentDTO;
import dev.shashi.agendaplus.dto.TaskDTO;
import dev.shashi.agendaplus.entitys.Atachment;
import dev.shashi.agendaplus.entitys.Task;

import java.sql.SQLException;
import java.util.ArrayList;

public class DefaultDayViewBOImpl implements DefaultDayViewBO {
    TaskDAO taskDAO = (TaskDAO) DAOFactory.getDAO(DAOFactory.DAOType.TASK);

    AtachmentDAO atachmentDAO = (AtachmentDAO) DAOFactory.getDAO(DAOFactory.DAOType.ATTACHMENT);

    public boolean updateTask(TaskDTO taskDTO) throws SQLException, ClassNotFoundException {

        return taskDAO.updateTask(new Task(taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.isDone(),taskDTO.getDate()));
    }

    public boolean deleteTask(int id) throws SQLException, ClassNotFoundException {
        return taskDAO.deleteTask(id);
    }

    public boolean deleteAttachmentById(int attachmentId) throws SQLException, ClassNotFoundException {
        return atachmentDAO.deleteAttachmentById(attachmentId);
    }

    public ArrayList<AtachmentDTO> getAtachments(int taskId) throws SQLException, ClassNotFoundException {
        ArrayList<Atachment> atachments = (ArrayList<Atachment>) atachmentDAO.getAtachments(taskId);

        ArrayList<AtachmentDTO> atachmentDTOs = new ArrayList<>();
        for (Atachment atachment : atachments) {
            AtachmentDTO atachmentDTO = new AtachmentDTO(atachment.getTaskId(), atachment.getFilePath());
            atachmentDTOs.add(atachmentDTO);
        }

        return atachmentDTOs;
    }

    public  boolean saveAtachments(AtachmentDTO atachment) throws SQLException, ClassNotFoundException {
        return atachmentDAO.saveAtachments(new Atachment(atachment.getTaskId(),atachment.getFilePath()));
    }
}
