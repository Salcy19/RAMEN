import java.awt.Color;
import java.util.Calendar;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Arturo III
 */
public class DesignChallenge1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CalendarControl cc = new CalendarControl();
        CalendarView cv = new CalendarView(cc);
        CalendarModel cm = new CalendarModel(cc);
        EventParser epp = new EventParser(cm);
        TaskParser tpp = new TaskParser(cm);
        cm.startInstructions();
         
    }
}
