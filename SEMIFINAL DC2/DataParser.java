import java.util.ArrayList;

public abstract class DataParser {

	ArrayList<Event> events = new ArrayList<Event>();
	ArrayList<Task> tasks = new ArrayList<Task>();
	
	public ArrayList<Event> readFile(String filename){
		readData(filename);
		return events;
	}
	public ArrayList<Task> readtaskFile(String filename){
		readData(filename);
		return tasks;
	}
	abstract void readData(String filename);

	abstract void writeData(String filename, Event event);
	
	abstract void writeTaskData(String filename, Task tast);
	
	public void processData(String info, int smonth, int sday, int syear, int emonth, int eday, int eyear, String timeStart, String timeEnd) {
		events.add(new Event(info,smonth,sday,syear,emonth,eday,eyear, timeStart, timeEnd));
	}
	public void processTaskData(String info, int smonth, int sday, int syear, int emonth, int eday, int eyear, String timeStart, String timeEnd) {
		tasks.add(new Task(info,smonth,sday,syear,emonth,eday,eyear,timeStart,timeEnd));
	}

}
