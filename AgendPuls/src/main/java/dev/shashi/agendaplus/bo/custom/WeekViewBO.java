package dev.shashi.agendaplus.bo.custom;

import dev.shashi.agendaplus.bo.SuperBO;
import dev.shashi.agendaplus.dto.TaskDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface WeekViewBO extends SuperBO {
    public ArrayList<TaskDTO> getTasks(LocalDate date) throws SQLException, ClassNotFoundException;
}
