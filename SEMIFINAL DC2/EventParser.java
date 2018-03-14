import java.util.ArrayList;

public class EventParser{

	CalendarModel cm;
	DataParser csv = new CSVParser();
	DataParser csv2 = new CSVParser();
	DataParser csv3 = new CSVParser();
	
	public EventParser(CalendarModel cm) {
		this.cm = cm;
	}
	
	public void readCSV(String filename, boolean recur) {
		ArrayList<Event> events = csv.readFile(filename);
		for(int i = 0 ; i < events.size() ; i++)
				cm.addEvent(events.get(i));
	}

	
	public void saveEventData(Event event){
		csv.writeData("Plans.csv",event);
	}
	
	public void loadData() {
		ArrayList<Event> events = csv.readFile("Events.csv");
		for(int i = 0 ; i < events.size() ; i++)
				cm.loadEvent(events.get(i));
	}
	
	public void addEvent(String info, int smonth, int sday, int syear, int emonth, int eday, int eyear, String timeStart, String timeEnd, String color) {
		cm.addEvent(new Event(info,smonth,sday,syear,emonth,eday,eyear,timeStart,timeEnd));
	}

}
