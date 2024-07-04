package dev.shashi.agendaplus.bo;

import dev.shashi.agendaplus.bo.custom.impl.*;

public class BOFactory {
    public enum BOType{
       REGISTER, LOGIN , HOME,DAYVIEW, MONTHVIEW,WEEKVIEW ,DEFAULTDAYVIEW,CREATETASKVIEW,TASKPANE, REMINDES,
    }

    public static SuperBO getBO(BOType boType){
        switch (boType){
            case HOME :
                return new HomeBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case DAYVIEW:
                return new DayViewBOImpl();
            case WEEKVIEW:
                return new WeekViewBOImpl();
            case MONTHVIEW:
                return new MonthViewBOImpl();
            case REGISTER:
                return new RegisterBOImpl();
            case TASKPANE:
                return new TaskPaneBOImpl();
            case CREATETASKVIEW:
                return new CreateTaskViewBOImpl();
            case DEFAULTDAYVIEW:
                return new DefaultDayViewBOImpl();
            case REMINDES:
                return new ReminderBOImpl();
            default:
                return null;

        }

    }
}
