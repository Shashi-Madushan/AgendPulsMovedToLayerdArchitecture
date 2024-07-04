package dev.shashi.agendaplus.bo.custom.impl;

import dev.shashi.agendaplus.bo.custom.MonthViewBO;
import dev.shashi.agendaplus.dao.DAOFactory;
import dev.shashi.agendaplus.dao.custom.TaskDAO;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class MonthViewBOImpl implements MonthViewBO {

    private TaskDAO taskDAO = (TaskDAO) DAOFactory.getDAO(DAOFactory.DAOType.TASK);
    public ArrayList<LocalDate> getTaskDates(YearMonth yearMonth, LocalDate firstDayOfMonth) throws SQLException, ClassNotFoundException {
        return  taskDAO.getTaskDates(yearMonth,firstDayOfMonth);
    }
}
