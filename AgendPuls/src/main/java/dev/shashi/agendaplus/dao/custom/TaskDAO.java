package dev.shashi.agendaplus.dao.custom;

import dev.shashi.agendaplus.dao.SuperDAO;
import dev.shashi.agendaplus.entitys.Task;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public interface TaskDAO extends SuperDAO {
    ArrayList<Task> getTasks(LocalDate date) throws SQLException, ClassNotFoundException;

    int getTaskCount(LocalDate date) throws SQLException, ClassNotFoundException;

    int getDoneTaskCount(LocalDate date) throws SQLException, ClassNotFoundException;

    int saveTask(Task task);

    boolean deleteTask(int taskId) throws SQLException, ClassNotFoundException;

    boolean updateTaskStatus(int taskId, boolean doneStatus) throws SQLException, ClassNotFoundException;

    ArrayList<LocalDate> getTaskDates(YearMonth yearMonth, LocalDate firstDayOfMonth) throws SQLException, ClassNotFoundException;

    ArrayList<LocalDate> getTaskDates(LocalDate startOfWeek) throws SQLException, ClassNotFoundException;

    boolean updateTask(Task task) throws SQLException, ClassNotFoundException;
}
