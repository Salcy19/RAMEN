import java.awt.Color;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Task extends Occasion{

	private int smonth;
	private int sday;
	private int syear;
	private int emonth;
	private int eday;
	private int eyear;
	private String info;
	private Color color;
	private String colorString;
	private int durationFrom;
	private int durationTo;
	private boolean done;
	Calendar c = new GregorianCalendar();
	
	public Task(String info, int smonth, int sday, int syear, int emonth, int eday, int eyear, String timeStart, String timeEnd) {
		super(info, smonth, sday, syear, emonth, eday, eyear, timeStart, timeEnd);
	}
	
	public int getsMonth() {
		return smonth;
	}
	
	public int getsDay() {
		return sday;
	}

	public int getsYear() {
		return syear;
	}
	public int geteMonth() {
		return emonth;
	}
	
	public int geteDay() {
		return eday;
	}

	public int geteYear() {
		return eyear;
	}
	public String getInfo() {
		return info;
	}
	
	public Color getColor() {
		return color;
	}

	public String getColorString() {
		return colorString;
	}	
	public int getDurationFrom() {
		return durationFrom;
	}
	public int getDurationTo() {
		return durationTo;
	}
	public void setDone() {
		this.done = true;
	}
	public boolean getIsDone() {
		if(this.done==true)
			return true;
		return false;
	}
}
