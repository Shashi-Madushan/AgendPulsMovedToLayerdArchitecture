package dev.shashi.agendaplus.reminders;

import dev.shashi.agendaplus.bo.BOFactory;
import dev.shashi.agendaplus.bo.custom.ReminderBO;
import dev.shashi.agendaplus.controllers.HomeController;
import dev.shashi.agendaplus.dto.ReminderDTO;

import javafx.application.Platform;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.plushaze.traynotification.notification.Notifications;

import com.github.plushaze.traynotification.notification.TrayNotification;
public class ReminderManager {
    private static LocalDate today = LocalDate.now();


    public static List<ReminderDTO> reminders = new ArrayList<ReminderDTO>();

    public static HomeController homeController;

    static ReminderBO reminderBO = (ReminderBO) BOFactory.getBO(BOFactory.BOType.REMINDES);

    public static void initialize() {
        loadRemindersFromDatabase();
    }

    private static void loadRemindersFromDatabase() {
        try {
            reminders =reminderBO.getReminders(today);
            System.out.println("Number of reminders loaded: " + reminders.size());
            scheduleReminders();
        } catch (SQLException e) {
            System.err.println("Failed to load reminders from database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

// Import the necessary classes for TrayNotification


    private static void scheduleReminders() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        for (ReminderDTO reminderDTO : reminders) {
            if (!reminderDTO.isShown()) {
                long delay = calculateDelay(reminderDTO.getDate().toLocalDate(), reminderDTO.getReminderTime());
                scheduler.schedule(() -> {
                    String title = "Reminder";
                    String message = "Task: " + reminderDTO.getTaskTitle() + " - Time: " + reminderDTO.getReminderTime().toString();
                    showAlert(title, message);
                    Notifications notification = Notifications.SUCCESS;
                    TrayNotification tray = new TrayNotification(title, message, notification);
                    tray.showAndWait();

                    reminderDTO.setShown(true);
                    reminders.remove(reminderDTO);
                    Platform.runLater(() -> {
                        homeController.setRemindersListView();
                    });

                    try {
                        reminderBO.setStatus(reminderDTO.getTaskId(), true);

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }, delay, TimeUnit.MILLISECONDS);
            }
        }
    }

    private static void showAlert(String title, String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private static long calculateDelay(LocalDate date, LocalTime time) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        long delay = java.time.Duration.between(now.atDate(today), time.atDate(date)).toMillis();
        return delay > 0 ? delay : 0;
    }

    public static List<ReminderDTO> getReminders() {
        return reminders;
    }

}