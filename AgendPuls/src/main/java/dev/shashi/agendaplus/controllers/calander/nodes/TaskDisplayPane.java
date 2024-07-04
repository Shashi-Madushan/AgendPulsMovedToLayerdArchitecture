package dev.shashi.agendaplus.controllers.calander.nodes;

import com.jfoenix.controls.JFXCheckBox;
import dev.shashi.agendaplus.bo.BOFactory;
import dev.shashi.agendaplus.bo.custom.TaskPaneBO;
import dev.shashi.agendaplus.controllers.calander.dayview.DayViewController;
import dev.shashi.agendaplus.dto.TaskDTO;
import dev.shashi.agendaplus.entitys.Task;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;



public class TaskDisplayPane extends AnchorPane {
    private TaskDTO taskDTO;
    private DayViewController dayViewController;
    private Text taskTitle; // Declare taskTitle as an instance variable

    private TaskPaneBO taskPaneBO= (TaskPaneBO) BOFactory.getBO(BOFactory.BOType.TASKPANE);

    public TaskDisplayPane(TaskDTO task, DayViewController dayViewController) {
        this.taskDTO = task;
        this.dayViewController = dayViewController;
        setupTaskTitle();
        setupDoneCheckBox();
        setupHoverEffect();
        setInitialStyle();
        updateTaskStyle();
    }

    private void setupTaskTitle() {
        taskTitle = new Text(taskDTO.getTitle().toUpperCase()); // Initialize taskTitle here
        taskTitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
        taskTitle.setFill(Color.BLACK);
        double anchorPaneHeight = 50;
        double taskTitleHeight = 20;
        AnchorPane.setLeftAnchor(taskTitle, 10.0);
        AnchorPane.setTopAnchor(taskTitle, (anchorPaneHeight - taskTitleHeight) / 2);
        this.getChildren().add(taskTitle);
    }

    private void setupDoneCheckBox() {
        JFXCheckBox doneCheckBox = new JFXCheckBox();
        doneCheckBox.setSelected(taskDTO.isDone());
        double anchorPaneHeight = 50;
        double taskTitleHeight = 20;
        AnchorPane.setRightAnchor(doneCheckBox, 10.0);
        AnchorPane.setTopAnchor(doneCheckBox, (anchorPaneHeight - taskTitleHeight) / 2);
        this.getChildren().add(doneCheckBox);

        doneCheckBox.setOnAction(event -> {
            boolean doneStatus = doneCheckBox.isSelected();
            try {
                taskPaneBO.updateTaskStatus (taskDTO.getTaskId(), doneStatus);
            }catch (Exception e){
                e.printStackTrace();
            }

            taskDTO.setDone(doneStatus);
            updateTaskStyle();
            dayViewController.initialize();
        });
    }

    private void setupHoverEffect() {
        this.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.setStyle("-fx-background-color: #f5da95; -fx-border-color: #FFA500; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 0; -fx-border-width: 2;");
            } else {
                updateTaskStyle(); // Call updateTaskStyle here to ensure correct color is maintained
            }
        });
    }

    private void setInitialStyle() {
        this.setPrefHeight(50);
        updateTaskStyle(); // Ensure initial style uses the correct background color
    }

    private void updateTaskStyle() {
        String backgroundColor = taskDTO.isDone() ? "gray" : "white";
        taskTitle.setFill(taskDTO.isDone() ? Color.WHITE : Color.BLACK); // Update the fill color based on task completion
        this.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-color: #FFA500; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 0; -fx-border-width: 2;");
    }
}