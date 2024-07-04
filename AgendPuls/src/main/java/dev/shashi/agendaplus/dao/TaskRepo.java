package dev.shashi.agendaplus.dao;

import dev.shashi.agendaplus.db.DbConnection;
import dev.shashi.agendaplus.dto.TaskDTO;
import dev.shashi.agendaplus.entitys.Task;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class TaskRepo {


    public static int saveTask(TaskDTO task) {
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











}