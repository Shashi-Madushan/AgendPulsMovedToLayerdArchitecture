package dev.shashi.agendaplus.dao.custom.impl;

import dev.shashi.agendaplus.dao.SQLUtil;
import dev.shashi.agendaplus.dao.custom.AtachmentDAO;
import dev.shashi.agendaplus.entitys.Atachment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtachmentDAOImpl implements AtachmentDAO {
    @Override
    public boolean saveAtachments(Atachment atachment) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO attachments (file_path ,task_id ) VALUES (?, ?)",atachment.getFilePath(),atachment.getTaskId());
    }

    @Override
    public List<Atachment> getAtachments(int taskId) throws SQLException, ClassNotFoundException {
        List<Atachment> atachments = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT * FROM attachments WHERE task_id = ?",taskId);
        while (rs.next()) {
            Atachment atachment = new Atachment(taskId, rs.getString("file_path"));
            atachment.setAtachmentId(rs.getInt("attachment_id"));
            atachments.add(atachment);
        }
        return atachments;
    }

    @Override
    public boolean deleteAttachmentById(int attachmentId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM attachments WHERE attachment_id = ?",attachmentId);
    }
}
