package dev.shashi.agendaplus.controllers.calander;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import dev.shashi.agendaplus.bo.BOFactory;
import dev.shashi.agendaplus.bo.custom.DefaultDayViewBO;
import dev.shashi.agendaplus.controllers.calander.dayview.DayViewController;
import dev.shashi.agendaplus.dto.AtachmentDTO;
import dev.shashi.agendaplus.dto.TaskDTO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DefaultDayViewSidePaneView {

    DayViewController dayViewController;

    @FXML
    private JFXButton addAtachmets;


    @FXML
    private Label timeText;


    @FXML
    private Label taskTimeLbl;

    @FXML
    private JFXListView<Hyperlink> atachmentsListView;

    @FXML
    private JFXButton addTaskBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private Label descText;

    @FXML
    private TextField description;

    @FXML
    private JFXTextArea noteTextArea;

    @FXML
    private Label noteTxt;

    @FXML
    private TextField taskNAme;

    @FXML
    private Label taskNameTxt;

    @FXML
    private JFXButton updateBtn;

    private LocalDate date;

    TaskDTO taskDTO;

    private DefaultDayViewBO defaultDayViewBO = (DefaultDayViewBO) BOFactory.getBO(BOFactory.BOType.DEFAULTDAYVIEW);


    public void initialize(TaskDTO task) {
        this.taskDTO = task;
        taskDetailsShow(taskDTO);


    }

    public void initialize() {

        taskDetailsHide();
    }

    @FXML
    void AddAtachementsOnClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All files (*.*)", "*.*");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String path = file.getAbsolutePath();
            String fileName = file.getName();
            try {
                defaultDayViewBO.saveAtachments(new AtachmentDTO(taskDTO.getTaskId(), path));

            }catch (Exception e){
                e.printStackTrace();
            }

            Hyperlink fileLink = new Hyperlink(fileName);
            fileLink.setContextMenu(createFileContextMenu(fileLink, path, -1)); // Assuming -1 is a placeholder for actual attachment ID
            atachmentsListView.getItems().add(fileLink);


        }
    }

    @FXML
    void addTaskBtnOnaction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/calander/createTaskView.fxml"));
            Pane popupContent = loader.load();
            CreateTaskViewController controller = loader.getController();
            controller.setDate(date);
            controller.setDayViewController(this.dayViewController);

            popupContent.getStylesheets().add(getClass().getResource("/stylees/crudPopUpStyles.css").toExternalForm());

            dayViewController.getRightViewAncherPAne().getChildren().setAll(popupContent);
        } catch (IOException ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Loading Error");
            errorAlert.setContentText("Failed to load the task creation view.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    void cancelBtnOnAction(ActionEvent event) {
        taskDetailsHide();
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this task?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted =defaultDayViewBO.deleteTask(taskDTO.getTaskId());
            if (isDeleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Task Deletion");
                alert.setHeaderText(null);
                alert.setContentText("Task has been successfully deleted.");
                alert.showAndWait();
                dayViewController.initialize(); // Refresh the view
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Task Deletion");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete the task.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        // Update task details
        taskDTO.setTitle(taskNAme.getText());
        taskDTO.setDescription(description.getText());

        // Update the task in the database

        boolean isTaskUpdated = defaultDayViewBO.updateTask(taskDTO);
        if (isTaskUpdated) {
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Update Success");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("Task details updated successfully.");
            infoAlert.showAndWait();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Update Failure");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Failed to update task details.");
            errorAlert.showAndWait();
        }




        dayViewController.initialize();
    }


    private void taskDetailsShow(TaskDTO task) {
        taskNAme.setText(task.getTitle());
        description.setText(task.getDescription());
        noteTextArea.setText(task.getDescription()); // Assuming duplication is intentional
        updateBtn.setVisible(true);
        deleteBtn.setVisible(true);
        taskNAme.setVisible(true);
        description.setVisible(true);
        noteTextArea.setVisible(true);
        descText.setVisible(true);
        noteTxt.setVisible(true);
        taskNameTxt.setVisible(true);
        cancelBtn.setVisible(true);
        taskTimeLbl.setVisible(true);
        timeText.setVisible(true);
        addAtachmets.setVisible(true);
        atachmentsListView.getItems().clear();
        atachmentsListView.setVisible(true);
        displayAttachments(task.getTaskId());
    }

    private void taskDetailsHide() {
        taskTimeLbl.setVisible(false);
        timeText.setVisible(false);
        addAtachmets.setVisible(false);
        taskNAme.setVisible(false);
        description.setVisible(false);
        noteTextArea.setVisible(false);
        updateBtn.setVisible(false);
        deleteBtn.setVisible(false);
        descText.setVisible(false);
        noteTxt.setVisible(false);
        taskNameTxt.setVisible(false);
        cancelBtn.setVisible(false);
        atachmentsListView.setVisible(false);
    }

    public void setDayViewController(DayViewController dayViewController) {
        this.dayViewController = dayViewController;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private void displayAttachments(int taskId) {
        try {
            List<AtachmentDTO> attachments = defaultDayViewBO.getAtachments(taskId);
            atachmentsListView.getItems().clear();
            for (AtachmentDTO attachment : attachments) {
                if (attachment.getFilePath() != null) { // Check if the filePath is not null
                    File file = new File(attachment.getFilePath());
                    String fileName = file.getName();
                    Hyperlink fileLink = new Hyperlink(fileName);
                    fileLink.setContextMenu(createFileContextMenu(fileLink, attachment.getFilePath(), attachment.getAtachmentId()));
                    atachmentsListView.getItems().add(fileLink);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ContextMenu createFileContextMenu(Hyperlink fileLink, String filePath, int attachmentId) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(e -> {
            try {
                Desktop.getDesktop().open(new File(filePath));
            } catch (IOException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Cannot Open File");
                errorAlert.setContentText("The file could not be opened.");
                errorAlert.showAndWait();
            }
        });

        MenuItem removeItem = new MenuItem("Remove");
        removeItem.setOnAction(e -> {
            try {
                atachmentsListView.getItems().remove(fileLink);
                boolean isDeleted = defaultDayViewBO.deleteAttachmentById(attachmentId); // Use the attachment ID here
                if (!isDeleted) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Deletion Error");
                    errorAlert.setContentText("Failed to delete the attachment from the database.");
                    errorAlert.showAndWait();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });

        contextMenu.getItems().addAll(openItem, removeItem);
        return contextMenu;
    }
}