import java.awt.Color;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Event extends Occasion{

	private int month;
	private int day;
	private int year;
	private String info;
	private Color color;
	private String colorString;
	private int durationFrom;
	private int durationTo;
	Calendar c = new GregorianCalendar();
	
	public Event(String info, int smonth, int sday, int syear, int emonth, int eday, int eyear, String timeStart, String timeEnd) {
		super(info, smonth, sday, syear, emonth, eday, eyear, timeStart, timeEnd);
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}

	public int getYear() {
		return year;
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
	public boolean getIsDone() {
		if(c.get(Calendar.YEAR)==this.year&&c.get(Calendar.MONTH)==this.month)
			return true;
		return false;
	}
}
