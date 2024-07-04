package dev.shashi.agendaplus.controllers.calander.dayview;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import dev.shashi.agendaplus.bo.BOFactory;
import dev.shashi.agendaplus.bo.custom.DayViewBO;
import dev.shashi.agendaplus.controllers.DashBoardController;
import dev.shashi.agendaplus.controllers.calander.DefaultDayViewSidePaneView;
import dev.shashi.agendaplus.controllers.calander.nodes.TaskDisplayPane;
import dev.shashi.agendaplus.dto.TaskDTO;
import dev.shashi.agendaplus.reminders.ReminderManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


import javafx.scene.paint.Color;
import javafx.scene.text.Text;


import javafx.util.Duration;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


import javafx.animation.TranslateTransition;
import javafx.animation.SequentialTransition;

import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;

public class DayViewController {



    DashBoardController dashBoardController;

    @FXML
    private AnchorPane rightViewAncherPAne;

    @FXML
    private JFXButton dayViewBackBtn;

    @FXML
    private JFXButton addTaskBtn;

    @FXML
    private Text dayViewDateText;

    @FXML
    private JFXListView<TaskDTO> taskListView;
    private LocalDate date;

    List<TaskDTO> taskList;

    private int taskId;
    private TaskDTO taskDTO;

    private DayViewBO dayViewBO= (DayViewBO) BOFactory.getBO(BOFactory.BOType.DAYVIEW);


    private String fromeMonthOrWeekView = "today";


    public void setTask(TaskDTO task) {
        this.taskDTO = task;
    }


    public YearMonth getCurrentMonth() {
        return YearMonth.from(date);
    }

    public LocalDate getStartOfWeek() {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }


    public void initialize()  {
        loardDefaultRightViewWithoutTask();
        ReminderManager.initialize();
        try {
            updateTaskView();
        }catch (Exception e){
            System.out.println(e);
        }

    }




    @FXML
    void dayViewBackBtnOnClickAction(ActionEvent event) {
        try {
            if (fromeMonthOrWeekView.equals("today") ){
                dashBoardController.homeButtonOnclick(event);
            } else if (fromeMonthOrWeekView.equals("week")  ) {
                dashBoardController.showCalenderWeekViewOnClick(event);
            } else if (fromeMonthOrWeekView.equals("month")) {
                dashBoardController.showCalenderMonthViewOnClick(event);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public void updateTaskView() throws SQLException, ClassNotFoundException {
        taskList = dayViewBO.getTasks(date);

        // Sort tasks so that completed tasks are at the bottom
        taskList.sort((task1, task2) -> Boolean.compare(task1.isDone(), task2.isDone()));

        setupTaskListView();  // Setup the ListView to display tasks
        taskListView.getItems().setAll(taskList);  // Add all tasks to the ListView
    }


    private void setupTaskListView() {
        taskListView.setCellFactory(listView -> new ListCell<TaskDTO>() {
            @Override
            protected void updateItem(TaskDTO task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                    setTooltip(null);
                } else {
                    AnchorPane taskDisplay = createTaskDisplay(task);
                    setTask(task);
                    setGraphic(taskDisplay);
                    setTooltip(createTooltipForTask(task));


                    playEntryAnimation(taskDisplay);
                    setOnMouseClicked(event -> {
                        loardDefaultRightView();

                        taskId = task.getTaskId();
                        System.out.println("Task ID: " + task.getTaskId());
                        System.out.println("Task title: " + task.getTitle());
                    });
                }
            }
        });
    }


    private void playEntryAnimation(AnchorPane taskDisplay) {
        double startY = taskDisplay.getLayoutY() + taskDisplay.getHeight(); // Start below the viewable area
        taskDisplay.setTranslateY(startY);


        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.5), taskDisplay);
        translateTransition.setFromY(startY);
        translateTransition.setToY(0);

        SequentialTransition sequentialTransition = new SequentialTransition(translateTransition);
        sequentialTransition.play();
    }

    private Tooltip createTooltipForTask(TaskDTO taskDTO) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Title: " + taskDTO.getTitle() + "\nDescription: " + taskDTO.getDescription());
        tooltip.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 16;");
        tooltip.setPrefSize(300.0, 100.0);
        return tooltip;
    }

    private AnchorPane createTaskDisplay(TaskDTO taskDTO) {

        return new TaskDisplayPane(taskDTO, this);
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        dayViewDateText.setFill(Color.WHITE); // Set text color to white
        dayViewDateText.setText(date.toString());
    }


    @FXML
    void updateBtnOnAction(ActionEvent event) {

    }


    public void unloadPopup() {
        rightViewAncherPAne.getChildren().clear();
        loardDefaultRightViewWithoutTask();


    }

    public AnchorPane getRightViewAncherPAne() {
        return rightViewAncherPAne;
    }

    public void setRightViewAncherPAne(AnchorPane rightViewAncherPAne) {
        this.rightViewAncherPAne = rightViewAncherPAne;
    }

    void loardDefaultRightView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/calander/defaultDayViewSidePaneView.fxml"));
            AnchorPane popupContent = loader.load();
            DefaultDayViewSidePaneView controller = loader.getController();
            controller.initialize(taskDTO);
            controller.setDate(date);
            controller.setDayViewController(this);
            popupContent.getStylesheets().add(getClass().getResource("/stylees/crudPopUpStyles.css").toExternalForm());
            rightViewAncherPAne.getChildren().setAll(popupContent); // Set the content of rightViewAncherPAne

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loardDefaultRightViewWithoutTask() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/calander/defaultDayViewSidePaneView.fxml"));
            AnchorPane popupContent = loader.load();
            DefaultDayViewSidePaneView controller = loader.getController();
            controller.initialize();
            controller.setDate(date);
            controller.setDayViewController(this);
            popupContent.getStylesheets().add(getClass().getResource("/stylees/crudPopUpStyles.css").toExternalForm());
            rightViewAncherPAne.getChildren().setAll(popupContent); // Set the content of rightViewAncherPAne
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setDashBoardController(DashBoardController dashBoardController) {
        this.dashBoardController = dashBoardController;
    }

    public void setFromeMonthOrWeekView(String fromeMonthOrWeekView) {
        this.fromeMonthOrWeekView = fromeMonthOrWeekView;
    }


}