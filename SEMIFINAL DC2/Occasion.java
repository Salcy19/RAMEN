
	import java.awt.Color;

	public class Occasion {

		private int smonth;
		private int sday;
		private int syear;
		private int emonth;
		private int eday;
		private int eyear;
		private String timestart;
		private String timeend;
		private String info;
		private Color color;
		private String colorString;
		private boolean event;
		private boolean task;

		
		public Occasion(String info, int smonth, int sday, int syear, int emonth, int eday,
				int eyear, String timestart, String timeend, boolean event, boolean task) {
			this.info = info;
			this.smonth = smonth;
			this.sday = sday;
			this.syear = syear;
			this.emonth = emonth;
			this.eday = eday;
			this.eyear = eyear;
			this.timestart = timestart;
			this.timeend = timeend;
			this.event = event;
			this.task = task;
		}

		public Occasion(String info, int smonth, int sday, int syear, int emonth, int eday, int eyear,
				String timeStart, String timeEnd) {
			this.info = info;
			this.smonth = smonth;
			this.sday = sday;
			this.syear = syear;
			this.emonth = emonth;
			this.eday = eday;
			this.eyear = eyear;
			this.timestart = timeStart;
			this.timeend = timeEnd;
		}
		
		public Occasion(int smonth, int sday, int syear, boolean event, boolean task) {
			this.smonth = smonth;
			this.sday = sday;
			this.syear = syear;
			this.event = event;
			this.task = task;
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
		
		public String gettimestart() {
			return timestart;
		}
		
		public String gettimeend() {
			return timeend;
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

		public void seteMonth(int m) {
			this.emonth=m;
		}
		
		
		public void seteDay(int d) {
			this.eday=d;
		}

		public void seteYear(int y) {
			this.eyear=y;
		}
		
		public void setsMonth(int m) {
			this.smonth=m;
		}
		
		
		public void setsDay(int d) {
			this.sday=d;
		}

		public void setsYear(int y) {
			this.syear=y;
		}
		
		public void setInfo(String i) {
			this.info=i;
		}
		
		public void setColor(Color c) {
			this.color=c;
		}
		public void setColorString(String c) {
			this.colorString=c;
		}
		public boolean getMode() {
			if(event==true)
				return true;
			return false;
		}
		
	}
