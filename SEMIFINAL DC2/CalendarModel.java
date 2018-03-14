import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarModel{

	GregorianCalendar current = (GregorianCalendar) Calendar.getInstance();
	ArrayList<Event> events = new ArrayList<Event>();
	ArrayList<Event> notnotified = new ArrayList<Event>();
	ArrayList<Event> notifiedrecur = new ArrayList<Event>();
	ArrayList<Event> recurevents = new ArrayList<Event>();
	CalendarControl cc;
	CalendarView cv;
	EventParser ep;
	TaskParser tp;
	ArrayList<Observer> obs = new ArrayList<Observer>();
	
	public void startInstructions() {
        
		ep.loadData();
		tp.loadTaskData();
		refreshCalendar();
		
        Thread instructions = new Thread() {
        	public void run() {
        		while(true) {
        			
            		try {
                		Thread.sleep(1000);           			
            		}catch(InterruptedException e) {} 
            		
            		ArrayList<Event> currentEvents = checkCurrentEvents();
            		
        			if(currentEvents.size() > 0)
        				for(int i = 0 ; i < currentEvents.size() ; i++)
        					update(currentEvents.get(i));
        			
        		}
        	}
        };
        
        instructions.start();
        
	}

	public CalendarModel(CalendarControl cc) {
		this.cc = cc;
	}
	

	public ArrayList<Event> checkCurrentEvents() {
		current = (GregorianCalendar) Calendar.getInstance();
		ArrayList<Event> returnedevents = new ArrayList<Event>();
		for(int i = 0 ; i < notnotified.size(); i++) {
			if(notnotified.get(i).getDay() == current.get(Calendar.DATE) && notnotified.get(i).getMonth() == current.get(Calendar.MONTH)+1 && notnotified.get(i).getYear() == current.get(Calendar.YEAR)) {
				returnedevents.add(notnotified.get(i));	
				notnotified.remove(i);
			}
		}			
		return returnedevents;
	}
	
	public ArrayList<Event> checkEvents(int month, int day, int year) {
		ArrayList<Event> returnedevents = new ArrayList<Event>();
		
		for(int i = 0 ; i < events.size(); i++) {

			if(events.get(i).getDay() == day && events.get(i).getMonth() == month+1 && events.get(i).getYear() == year) {
				returnedevents.add(events.get(i));
			}			
		}		
		return returnedevents;
	}
		
	public int getCurrentYear() {
		return (int) current.get(Calendar.YEAR);
	}

	public void refreshCalendar() {
		cv.refreshCalendar();
	}
	
	public void update(Event event) {
		for(int i = 0 ; i < obs.size() ; i++)
			obs.get(i).update(event);
	}
	public void addTask(Task task) {
			tp.saveTaskData(task);
		refreshCalendar();
	}
	public void addEvent(Event event) {
			ep.saveEventData(event);
		refreshCalendar();
	}
	
	public void attachObserver(Observer ob) {
		obs.add(ob);
	}

	public void loadEvent(Event event) {
		addEvent(event);
	}
	public void loadTask(Task task) {
		addTask(task);
	}
	
	public void update(Occasion occ) {
		if(occ.getMode()==true)
			addEvent(new Event(occ.getInfo(),occ.getsMonth(),occ.getsDay(),occ.getsYear(),occ.geteMonth(),occ.geteDay(),occ.geteYear(),occ.gettimestart(),occ.gettimeend()));
		addTask(new Task(occ.getInfo(),occ.getsMonth(),occ.getsDay(),occ.getsYear(),occ.geteMonth(),occ.geteDay(),occ.geteYear(),occ.gettimestart(),occ.gettimeend()));
			
	}
}
