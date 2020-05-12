package Engine;

import java.util.ArrayList;

public class Timer {

    private ArrayList<Alarm> alarms;

    public Timer() {
        alarms = new ArrayList<Alarm>();
    }

    public void update() {
        for (Alarm alarm : alarms) {
            alarm.update();
        }
    }

    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
    }

    public void removeAlarm(Alarm alarm) {
        alarms.remove(alarm);
    }

}
