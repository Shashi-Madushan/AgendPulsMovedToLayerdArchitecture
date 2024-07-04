package dev.shashi.agendaplus.bo.custom;

import dev.shashi.agendaplus.bo.SuperBO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public interface MonthViewBO extends SuperBO {
    public ArrayList<LocalDate> getTaskDates(YearMonth yearMonth, LocalDate firstDayOfMonth) throws SQLException, ClassNotFoundException;
}
