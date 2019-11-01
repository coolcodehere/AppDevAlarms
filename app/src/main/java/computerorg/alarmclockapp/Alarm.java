package computerorg.alarmclockapp;

import java.util.Date;

public class Alarm {

    String alarmName;
    Date triggerDate;

    public Alarm(String alarmName, Date triggerDate) {
        this.alarmName = alarmName;
        this.triggerDate = triggerDate;
    }
}
