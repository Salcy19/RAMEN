import java.util.ArrayList;

public class CalendarControl{

	ArrayList<Event> events = new ArrayList<Event>();
	ArrayList<Task> tasks = new ArrayList<Task>();
	ArrayList<Occasion> occ = new ArrayList<Occasion>();
	ArrayList<Event> notnotified = new ArrayList<Event>();
	ArrayList<Event> notifiedrecur = new ArrayList<Event>();
	ArrayList<Event> recurevents = new ArrayList<Event>();
	CalendarModel cm;
	CalendarView cv;
	EventParser ep;
	
	public ArrayList<Event> checkDateEvent(int month, int day, int year) {
		return cm.checkEvents(month, day, year);
	}
	
	public boolean addEvent(Event event) {
		if(!checkOverlapEvent(event)) {
			events.add(event);	
			notnotified.add(event);
			return true;
		}
		return false;
	}
	
	public boolean checkOverlapRecurEvent(Event event) {
		for(int i = 0 ; i < recurevents.size(); i++) {
			if(recurevents.get(i).getInfo().equals(event.getInfo()) && recurevents.get(i).getDay() == event.getDay() && recurevents.get(i).getMonth() == event.getMonth() && recurevents.get(i).getYear() == event.getYear() && recurevents.get(i).getColorString().equals(event.getColorString())) {
				return true;
			}			
		}		
		return false;
	}
	
	public boolean checkOverlapEvent(Event event) {
		for(int i = 0 ; i < events.size(); i++) {
			if(events.get(i).getInfo().equals(event.getInfo()) && events.get(i).getDay() == event.getDay() && events.get(i).getMonth() == event.getMonth() && events.get(i).getYear() == event.getYear() && events.get(i).getColorString().equals(event.getColorString())) {
				return true;
			}			
		}		
		return false;
	}
	

	public void attachParser(EventParser ep) {
		this.ep = ep;
	}
	
	public void attachModel(CalendarModel cm) {
		this.cm = cm;
	}
	
	public void attachView(CalendarView cv) {
		this.cv = cv;
	}
	
	public void updateViews(int month, int day, int year, boolean event, boolean task) {
		cm.update(new Occasion(month, day, year, event, task));
	}
	public void updateDateTitle(int year, int month, int day) {
		
	}
	public void addOccasion(String info, String smonth, String sday, String syear, String emonth, String eday, String eyear, String timestart, String timeend, boolean event, boolean task) {
			occ.add(new Occasion(info, Integer.parseInt(smonth), Integer.parseInt(sday), Integer.parseInt(syear), Integer.parseInt(emonth), Integer.parseInt(eday), Integer.parseInt(eyear), timestart, timeend, event, task));
	}
}
