package dev.shashi.agendaplus.bo.custom;

import dev.shashi.agendaplus.bo.SuperBO;
import dev.shashi.agendaplus.dto.AtachmentDTO;
import dev.shashi.agendaplus.dto.TaskDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DefaultDayViewBO extends SuperBO {
    public boolean updateTask(TaskDTO taskDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteTask(int id) throws SQLException, ClassNotFoundException ;

    public boolean deleteAttachmentById(int attachmentId) throws SQLException, ClassNotFoundException;

    public ArrayList<AtachmentDTO> getAtachments(int taskId) throws SQLException, ClassNotFoundException;

    public  boolean saveAtachments(AtachmentDTO atachment) throws SQLException, ClassNotFoundException;
}
