package dev.shashi.agendaplus.dao.custom;

import dev.shashi.agendaplus.dao.SuperDAO;
import dev.shashi.agendaplus.entitys.Atachment;

import java.sql.SQLException;
import java.util.List;

public interface AtachmentDAO extends SuperDAO {
    boolean saveAtachments(Atachment atachment) throws SQLException, ClassNotFoundException;

    List<Atachment> getAtachments(int taskId) throws SQLException, ClassNotFoundException;

    boolean deleteAttachmentById(int attachmentId) throws SQLException, ClassNotFoundException;
}
