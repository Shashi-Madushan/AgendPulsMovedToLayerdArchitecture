package dev.shashi.agendaplus.dao.custom.impl;

import dev.shashi.agendaplus.dao.SQLUtil;
import dev.shashi.agendaplus.dao.custom.TaskDAO;
import dev.shashi.agendaplus.db.DbConnection;
import dev.shashi.agendaplus.dto.TaskDTO;
import dev.shashi.agendaplus.entitys.Task;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class TaskDAOImpl implements TaskDAO {
    @Override
    public ArrayList<Task> getTasks(LocalDate date) throws SQLException, ClassNotFoundException {
        ArrayList<Task> taskList = new ArrayList<>();
        ResultSet rs =  SQLUtil.execute("SELECT task_id, title, description, status FROM tasks WHERE date = ?",date);
        while (rs.next()) {
            Task task = new Task( rs.getString("title"), rs.getString("description"),rs.getBoolean("status"), date);
            task.setTaskId(rs.getInt("task_id"));
            taskList.add(task);
        }
        return taskList;
    }

    @Override
    public int getTaskCount(LocalDate date) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) FROM tasks WHERE date = ?",date);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;

    }

    @Override
    public int getDoneTaskCount(LocalDate date) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.execute("SELECT count(*) FROM tasks WHERE date = ? AND status = TRUE",date);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public int saveTask(TaskDTO task) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int taskID = -1; // Initialize taskID to -1 to indicate failure
        try {
            conn = DbConnection.getInstance().getConnection();
            String sql = "INSERT INTO tasks (title, description, status, date) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setBoolean(3, task.isDone());
            pstmt.setObject(4, task.getDate());

            int affectedRows = pstmt.executeUpdate();
            System.out.println("task saved");
            if (affectedRows > 0) {
                ResultSet resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    taskID = resultSet.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskID;
    }

    @Override
    public boolean deleteTask(int taskId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM tasks WHERE task_id = ?",taskId);
    }

    @Override
    public boolean updateTaskStatus(int taskId, boolean doneStatus) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE tasks SET status = ? WHERE task_id = ?",doneStatus,taskId);
    }

    @Override
    public ArrayList<LocalDate> getTaskDates(YearMonth yearMonth, LocalDate firstDayOfMonth) throws SQLException, ClassNotFoundException {
        ArrayList<LocalDate> taskDays = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT DISTINCT date FROM tasks WHERE date BETWEEN ? AND ?",java.sql.Date.valueOf(firstDayOfMonth),java.sql.Date.valueOf(yearMonth.atEndOfMonth()));
        while (rs.next()) {
            LocalDate date = rs.getObject("date", LocalDate.class);
            taskDays.add(date);
        }
        return taskDays;
    }

    @Override
    public ArrayList<LocalDate> getTaskDates(LocalDate startOfWeek) throws SQLException, ClassNotFoundException {
        ArrayList<LocalDate> taskDays = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT DISTINCT date FROM tasks WHERE date >= ?",java.sql.Date.valueOf(startOfWeek));
        while (rs.next()) {
            LocalDate date = rs.getObject("date", LocalDate.class);
            taskDays.add(date);
        }
        return taskDays;
    }

    @Override
    public boolean updateTask(Task task) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE tasks SET title = ?, description = ?, status = ?, date = ? WHERE task_id = ?",task.getTitle(),task.getDescription(),task.isDone(),task.getDate(),task.getTaskId());
    }
}
