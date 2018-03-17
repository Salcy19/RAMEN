import java.util.Calendar;
import java.util.GregorianCalendar;

public class DesignChallenge2 {

    public static void main(String[] args) {
        // TODO code application logic here
        CalendarView cv = new CalendarView();
        CalendarModel cm = new CalendarModel();
        CalendarControl cc = new CalendarControl();
        AgendaView av = new AgendaView(cv);
        DayView dv = new DayView(cv);
        WeekView wv = new WeekView(cv);
        cv.attachController(cc);
        cv.attachAgendaView(av);
        cv.attachDayView(dv);
        cv.attachWeekView(wv);
        cm.connectDatabase("com.mysql.jdbc.Driver","jdbc:mysql://127.0.0.1:3306/","root","kilokilo09","SWDESPA");
        cm.attachView(cv);
        cc.attachModel(cm);
        cm.attachObserver((ObserverView) av);
        cm.attachObserver((ObserverView) dv);
        cm.attachObserver((ObserverView) wv);
        cv.initialize();
        cv.refreshView();
        cm.timeCheck();

    }
    
}
