package dev.shashi.agendaplus.bo.custom;

import dev.shashi.agendaplus.bo.SuperBO;

import java.sql.SQLException;
import java.time.LocalDate;

public interface HomeBO extends SuperBO {
    public int getTaskCount(LocalDate date) throws SQLException, ClassNotFoundException;

    public int getDoneTaskCOunt(LocalDate date) throws SQLException, ClassNotFoundException;
}
