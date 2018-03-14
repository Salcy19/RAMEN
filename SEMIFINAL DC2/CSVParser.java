import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVParser extends DataParser{

	public void readData(String filename) {
		BufferedReader br = null;
		String line = "";
		String comma = ",";
		int smonth,sday,syear,emonth,eday,eyear,timeStart,timeEnd;
		
		try {
			br = new BufferedReader(new FileReader(filename));
			while((line = br.readLine()) != null) {
				String [] calendar = line.split(comma);
				
				String startdate = calendar[0];//parsing the month/day/year
				String [] split = startdate.split("/");
				smonth = Integer.parseInt(split[0]);
				sday = Integer.parseInt(split[1]);
				syear = Integer.parseInt(split[2]);
				
				String enddate = calendar[1];//parsing the month/day/year
				String [] split2 = enddate.split("/");
				emonth = Integer.parseInt(split2[0]);
				eday = Integer.parseInt(split2[1]);
				eyear = Integer.parseInt(split2[2]);
				
				super.processData(calendar[2],smonth,sday,syear,emonth,eday,eyear,calendar[3],calendar[4]);
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
		catch (IOException e) {
			//e.printStackTrace();
		}		
	}
	

	public void readTaskData(String filename) {
		BufferedReader br = null;
		String line = "";
		String comma = ",";
		int smonth,sday,syear,emonth,eday,eyear,timeStart,timeEnd;
		
		try {
			br = new BufferedReader(new FileReader(filename));
			while((line = br.readLine()) != null) {
				String [] calendar = line.split(comma);
				
				String startdate = calendar[0];//parsing the month/day/year
				String [] split = startdate.split("/");
				smonth = Integer.parseInt(split[0]);
				sday = Integer.parseInt(split[1]);
				syear = Integer.parseInt(split[2]);
				
				String enddate = calendar[1];//parsing the month/day/year
				String [] split2 = enddate.split("/");
				emonth = Integer.parseInt(split2[0]);
				eday = Integer.parseInt(split2[1]);
				eyear = Integer.parseInt(split2[2]);
				
				
				super.processTaskData(calendar[2],smonth,sday,syear,emonth,eday,eyear,calendar[3],calendar[4]);
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
		catch (IOException e) {
			//e.printStackTrace();
		}		
	}
	public void writeData(String filename, Event event) {
		String newline = "\n";
		String comma = ",";
		
		FileWriter fileWriter = null;
		try {
			
			fileWriter = new FileWriter (filename, true);
			fileWriter.append(String.valueOf(event.getMonth()));
			fileWriter.append("/");
			fileWriter.append(String.valueOf(event.getDay())); //converting int to string
			fileWriter.append("/");
			fileWriter.append(String.valueOf(event.getYear()));
			fileWriter.append(comma);
			fileWriter.append(event.getInfo());
			fileWriter.append(comma);
			fileWriter.append(event.getColorString());
			fileWriter.append(newline);
			
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
	public void writeTaskData(String filename, Task task) {
		String newline = "\n";
		String comma = ",";
		
		FileWriter fileWriter = null;
		try {
			
			fileWriter = new FileWriter (filename, true);
			fileWriter.append(String.valueOf(task.getsMonth()));
			fileWriter.append("/");
			fileWriter.append(String.valueOf(task.getsDay())); //converting int to string
			fileWriter.append("/");
			fileWriter.append(String.valueOf(task.getsYear()));
			fileWriter.append(comma);
			fileWriter.append(String.valueOf(task.geteMonth()));
			fileWriter.append("/");
			fileWriter.append(String.valueOf(task.geteDay())); //converting int to string
			fileWriter.append("/");
			fileWriter.append(String.valueOf(task.geteYear()));
			fileWriter.append(comma);
			fileWriter.append(task.getInfo());
			fileWriter.append(comma);
			fileWriter.append(task.getColorString());
			fileWriter.append(newline);
			
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}

}
