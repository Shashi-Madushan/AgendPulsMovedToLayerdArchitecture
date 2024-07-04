package dev.shashi.agendaplus.bo.custom;

import dev.shashi.agendaplus.bo.SuperBO;
import dev.shashi.agendaplus.dto.AtachmentDTO;
import dev.shashi.agendaplus.dto.ReminderDTO;
import dev.shashi.agendaplus.dto.TaskDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CreateTAskViewBO extends SuperBO {
    public  boolean trySaveTest(TaskDTO task, ArrayList<AtachmentDTO> atachments, ReminderDTO reminder) throws SQLException;
}
