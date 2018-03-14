import java.util.ArrayList;

public class TaskParser{

	CalendarModel cm;
	DataParser csv = new CSVParser();
	
	public TaskParser(CalendarModel cm) {
		this.cm = cm;
	}
	
	public void readCSV(String filename, boolean recur) {
		ArrayList<Task> tasks = csv.readtaskFile(filename);
		for(int i = 0 ; i < tasks.size() ; i++)
			cm.addTask(tasks.get(i));
	}

	
	public void saveTaskData(Task task){
		csv.writeTaskData("Task.csv", task);
	}
	
	public void loadTaskData() {
		ArrayList<Task> tasks = csv.readtaskFile("Task.csv");
		for(int i = 0 ; i < tasks.size() ; i++)
				cm.loadTask(tasks.get(i));
	}
	
	public void addTask(String info, int smonth, int sday, int syear, int emonth, int eday, int eyear, String timeStart, String timeEnd, String color) {
		cm.addEvent(new Event(info,smonth,sday,syear,emonth,eday,eyear,timeStart,timeEnd));
	}
}