package dev.shashi.agendaplus.bo.custom.impl;

import dev.shashi.agendaplus.bo.custom.DayViewBO;
import dev.shashi.agendaplus.dao.DAOFactory;
import dev.shashi.agendaplus.dao.custom.TaskDAO;
import dev.shashi.agendaplus.dto.TaskDTO;
import dev.shashi.agendaplus.entitys.Task;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DayViewBOImpl implements DayViewBO {

    private TaskDAO taskDAO = (TaskDAO) DAOFactory.getDAO(DAOFactory.DAOType.TASK);
    public ArrayList<TaskDTO> getTasks(LocalDate date) throws SQLException, ClassNotFoundException {
        ArrayList<Task> tasks = taskDAO.getTasks(date);
        ArrayList<TaskDTO> taskDTOs = new ArrayList<>();

        for (Task task : tasks) {
            TaskDTO taskDTO = new TaskDTO(task.getTitle(), task.getDescription(), task.isDone(), task.getDate());
            taskDTOs.add(taskDTO);
        }

        return taskDTOs;
    }
}
