
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

public class CalendarView{

	private int yearBound, monthBound, dayBound, yearToday, monthToday, dayToday;
	private int currentSelectedMonth, currentSelectedDay, currentSelectedYear;
	
	CalendarControl cc;
	EventAdderView ea;
	DayView dv;
	WeekView wv;
	AgendaView av;
	
        /**** Swing Components ****/
    public JLabel monthLabel;
	public JButton btnPrev, btnNext;
    public JComboBox<String> cmbYear;
	public JFrame frmMain;
	public Container pane;
	public JScrollPane scrollCalendarTable;
	public JPanel calendarPanel;
        
        /**** Calendar Table Components ***/
	public JTable calendarTable;
    public DefaultTableModel modelCalendarTable;
    private JPanel buttonPanel;
    private JPanel yearPanel;
    private JPanel filterPanel;
    private JPanel interactPanel;
    private JPanel infoBorderPanel;
    private JPanel datePanel;
    private JLabel lblTitle;
    private JButton btnToday;
    private JLabel lblCurrentDate;
    private JButton btnTime;
    private JButton btnAgenda;
    private JPanel infoPanel;
    private JCheckBox eventFilter;
    private JCheckBox taskFilter;
    private JButton btnMarkDone;
    private JButton btnDelete;
    private JRadioButton rdbtnDay;
    private JRadioButton rdbtnWeek;
    
    public void attachController(CalendarControl cc) {
    	this.cc = cc;
    }
    
    public void initialize() {
    	
		ea = new EventAdderView(cc);
		ea.initialize();
		ea.updateCurrentDate(currentSelectedYear, currentSelectedMonth, currentSelectedDay);
    	
		for (int i = yearBound-100; i <= yearBound+100; i++)
        {
			cmbYear.addItem(String.valueOf(i));
		}    	

		monthToday = monthBound;
		yearToday = yearBound;
    	
		btnToday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GregorianCalendar cal = new GregorianCalendar();
				monthToday = cal.get(GregorianCalendar.MONTH);
				yearToday = cal.get(GregorianCalendar.YEAR);
				dayToday = cal.get(GregorianCalendar.DATE);
				refreshCalendar();
				cc.updateDateTitle(yearToday,monthToday,dayToday);
				refreshCalendar();
				cc.updateViews(currentSelectedYear, currentSelectedMonth, currentSelectedDay, eventFilter.isSelected(), taskFilter.isSelected(), rdbtnDay.isSelected(), rdbtnWeek.isSelected());
			}
		});
		taskFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc.updateViews(currentSelectedYear, currentSelectedMonth, currentSelectedDay, eventFilter.isSelected(), taskFilter.isSelected(), rdbtnDay.isSelected(), rdbtnWeek.isSelected());
			}
		});
		
		eventFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc.updateViews(currentSelectedYear, currentSelectedMonth, currentSelectedDay, eventFilter.isSelected(), taskFilter.isSelected(), rdbtnDay.isSelected(), rdbtnWeek.isSelected());
			}
		}); 
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc.deleteIsDone(av.getSelectedOccasion());
				cc.refreshCalendarDays();
				cc.refreshCalendar(monthToday, yearToday);
				cc.updateViews(currentSelectedYear, currentSelectedMonth, currentSelectedDay, eventFilter.isSelected(), taskFilter.isSelected(), rdbtnDay.isSelected(), rdbtnWeek.isSelected());
			}
		});
		
		btnMarkDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc.updateIsDone(av.getSelectedOccasion(),true);
				cc.updateViews(currentSelectedYear, currentSelectedMonth, currentSelectedDay, eventFilter.isSelected(), taskFilter.isSelected(), rdbtnDay.isSelected(), rdbtnWeek.isSelected());
			}
		});	
		
		calendarTable.addMouseListener(new MouseAdapter() {  
		    public void mouseClicked(MouseEvent evt)  
		    {  
		        int col = calendarTable.getSelectedColumn();  
		        int row = calendarTable.getSelectedRow(); 
		        try {
			        if(modelCalendarTable.getValueAt(row, col) != null) {
			        	String day = modelCalendarTable.getValueAt(row, col).toString().replace("<html>","");
			            day = day.replace("<font color = \"red\">&#160","");
			            day = day.trim();
					    refreshCalendar();
				        cc.updateDateTitle(yearToday,monthToday,Integer.parseInt(day));
					    refreshCalendar();		
					    cc.updateViews(currentSelectedYear, currentSelectedMonth, currentSelectedDay, eventFilter.isSelected(), taskFilter.isSelected(), rdbtnDay.isSelected(), rdbtnWeek.isSelected());
			        }		        	
		        }catch(Exception e){}
		    }
		});

		rdbtnDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc.updateViews(currentSelectedYear, currentSelectedMonth, currentSelectedDay, eventFilter.isSelected(), taskFilter.isSelected(), rdbtnDay.isSelected(), rdbtnWeek.isSelected());
				cc.refreshCalendar(monthToday, yearToday);
				if(infoPanel.getComponent(0) instanceof AgendaView)
					showAgendaView();
				else if(infoPanel.getComponent(0) instanceof DayView || infoPanel.getComponent(0) instanceof WeekView)
					showTimeView();
			}
		});

		rdbtnWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc.updateViews(currentSelectedYear, currentSelectedMonth, currentSelectedDay, eventFilter.isSelected(), taskFilter.isSelected(), rdbtnDay.isSelected(), rdbtnWeek.isSelected());
				cc.refreshCalendar(monthToday, yearToday);
				if(infoPanel.getComponent(0) instanceof AgendaView)
					showAgendaView();
				else if(infoPanel.getComponent(0) instanceof DayView || infoPanel.getComponent(0) instanceof WeekView)
					showTimeView();
			}
		});		
		
    	refreshCalendar();
		cc.updateDateTitle(yearToday, monthToday, dayToday);
		refreshCalendar();
		cc.updateViews(currentSelectedYear, currentSelectedMonth, currentSelectedDay, eventFilter.isSelected(), taskFilter.isSelected(), rdbtnDay.isSelected(), rdbtnWeek.isSelected());
		showAgendaView();
		frmMain.setVisible(true);
    }
    
    public void update() {
    	ea.updateCurrentDate(currentSelectedYear, currentSelectedMonth, currentSelectedDay);
    	disableSelectButtons();
		infoPanel.revalidate();
		refreshView();
    }
    
	public void updateNotification(boolean success, String type) {
		if(success) {
			JOptionPane.showMessageDialog((Component) ea,type+" added successfully."); 	
			ea.clearInputs();
		}else {
			JOptionPane.showMessageDialog((Component) ea,type+" overlaps with existing schedule.","Overlap", JOptionPane.ERROR_MESSAGE);			
		}
	}
    
    public void showEventAdder() {
    	((Component) ea).setVisible(true);
		disableSelectButtons();
    	infoPanel.removeAll();
		infoPanel.add((Component) ea);
		infoPanel.revalidate();
		refreshView();
    }
    
    public void showTimeView() {
    	cc.updateViews(currentSelectedYear, currentSelectedMonth, currentSelectedDay, eventFilter.isSelected(), taskFilter.isSelected(), rdbtnDay.isSelected(), rdbtnWeek.isSelected());
		infoPanel.removeAll();
		if(rdbtnWeek.isSelected())
			infoPanel.add((Component) wv);
		else if(rdbtnDay.isSelected())
			infoPanel.add((Component) dv);
		infoPanel.revalidate();
		refreshView();    	
    }
    
    public void showAgendaView() {
    	cc.updateViews(currentSelectedYear, currentSelectedMonth, currentSelectedDay, eventFilter.isSelected(), taskFilter.isSelected(), rdbtnDay.isSelected(), rdbtnWeek.isSelected());
    	infoPanel.removeAll();
		infoPanel.add((Component) av);
		infoPanel.revalidate();
		refreshView();   	
    }
    
    public void refreshView() {
    	frmMain.repaint();
    }
    
    public void updateDateTitle(int currentSelectedYear, int currentSelectedMonth, int currentSelectedDay) {
    	this.currentSelectedYear = currentSelectedYear;
    	this.currentSelectedMonth = currentSelectedMonth;
    	this.currentSelectedDay = currentSelectedDay;
    	dv.updateCurrent(currentSelectedYear, currentSelectedMonth, currentSelectedDay);
    	wv.updateCurrent(currentSelectedYear, currentSelectedMonth, currentSelectedDay);
    	String currentDate = new String();
    	currentDate += monthLabel.getText();
    	currentDate += " "+currentSelectedDay;
    	currentDate += ", "+currentSelectedYear;
    	lblCurrentDate.setText(currentDate);
    }

    public void refreshCalendar() {
    	cc.refreshCalendar(monthToday, yearToday);
    }    
    
    public void refreshCalendar(int month, int year, ArrayList<Integer> days){
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som, i, j;
			
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		
		btnPrev.setContentAreaFilled(false);
		
		if (month == 0 && year <= yearBound-100)
                    btnPrev.setEnabled(false);
		if (month == 11 && year >= yearBound+100)
                    btnNext.setEnabled(false);
                
		monthLabel.setText(months[month]);

		cmbYear.setSelectedItem(""+year);
		
		for (i = 0; i < 6; i++)
			for (j = 0; j < 7; j++)
				modelCalendarTable.setValueAt(null, i, j);
		
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		for (i = 1; i <= nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			boolean isMarked = false;
			for(int x = 0 ; x < days.size() && !isMarked; x++) {
				if(days.get(x) == i) {
					modelCalendarTable.setValueAt("<html><font color = \"red\">&#160 "+i, row, column);
					isMarked = true;
				}
			}
			if(!isMarked)
				modelCalendarTable.setValueAt("  "+i, row, column);
        }
		Calendar c = new GregorianCalendar(currentSelectedYear, currentSelectedMonth, currentSelectedDay);
		List<String> selectedDates = new ArrayList<>();
		if(rdbtnWeek.isSelected())
			for(int x = 0 ; x < 7 ; x++) {
				selectedDates.add(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+" "+c.get(Calendar.DATE));
				c.add(Calendar.DATE, 1);
			}
		else if(rdbtnDay.isSelected())
			selectedDates.add(currentSelectedYear+"-"+currentSelectedMonth+" "+currentSelectedDay);
		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer(month, year, selectedDates));
		calendarTable.clearSelection();
    }
        
	public CalendarView()
        {
		
		try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
		catch (Exception e) {}
                
		frmMain = new JFrame ("Productivity Tool");
		frmMain.setTitle("DC2");
		frmMain.getContentPane().setBackground(SystemColor.textHighlight);
                frmMain.setSize(1000, 600);
		pane = frmMain.getContentPane();
		pane.setLayout(null);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		modelCalendarTable = new DefaultTableModel()
                {
                    public boolean isCellEditable(int rowIndex, int mColIndex)
                    {
                        return false;
                    }
                };
                
		frmMain.setResizable(false);
		
		GregorianCalendar cal = new GregorianCalendar();
		dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
		monthBound = cal.get(GregorianCalendar.MONTH);
		yearBound = cal.get(GregorianCalendar.YEAR);
		monthToday = monthBound; 
		yearToday = yearBound;
		dayToday = dayBound;
		
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<7; i++){
			modelCalendarTable.addColumn(headers[i]);
		}
		
		interactPanel = new JPanel();
		interactPanel.setBackground(SystemColor.activeCaption);
		interactPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		interactPanel.setBounds(10, 0, 974, 70);
		frmMain.getContentPane().add(interactPanel);
		
		lblTitle = new JLabel("Calendar");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		btnToday = new JButton("Go to Today");

		
		lblCurrentDate = new JLabel();
		lblCurrentDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		btnTime = new JButton("Time");
		btnTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTimeView();
			}
		});
		
		btnAgenda = new JButton("Agenda");
		btnAgenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAgendaView();
			}
		});
		
		ButtonGroup selectView = new ButtonGroup();
		
		rdbtnDay = new JRadioButton("View by day");
		rdbtnDay.setBackground(SystemColor.activeCaption);
		rdbtnWeek = new JRadioButton("View by week");
		rdbtnWeek.setBackground(SystemColor.activeCaption);
		
		selectView.add(rdbtnDay);
		selectView.add(rdbtnWeek);
		
		rdbtnDay.setSelected(true);
		
		GroupLayout gl_interactPanel = new GroupLayout(interactPanel);
		gl_interactPanel.setHorizontalGroup(
			gl_interactPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_interactPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_interactPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitle)
						.addGroup(gl_interactPanel.createSequentialGroup()
							.addComponent(rdbtnDay)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnWeek)))
					.addGap(133)
					.addComponent(lblCurrentDate, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnToday)
					.addGap(247)
					.addComponent(btnTime, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(2)
					.addComponent(btnAgenda)
					.addContainerGap())
		);
		gl_interactPanel.setVerticalGroup(
			gl_interactPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_interactPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_interactPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_interactPanel.createSequentialGroup()
							.addComponent(lblTitle)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_interactPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnDay)
								.addComponent(rdbtnWeek)))
						.addGroup(gl_interactPanel.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_interactPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnTime)
								.addComponent(btnAgenda)
								.addComponent(btnToday))
							.addComponent(lblCurrentDate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
					.addGap(54))
		);
		interactPanel.setLayout(gl_interactPanel);
		
		infoBorderPanel = new JPanel();
		infoBorderPanel.setBackground(SystemColor.activeCaption);
		infoBorderPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		infoBorderPanel.setBounds(338, 77, 646, 483);
		frmMain.getContentPane().add(infoBorderPanel);
		
		infoPanel = new JPanel();
		infoPanel.setBackground(SystemColor.activeCaption);
		infoPanel.setLayout(null);
		btnMarkDone = new JButton("Mark Done");
		
		btnDelete = new JButton("Delete Event/Task");
		GroupLayout gl_infoBorderPanel = new GroupLayout(infoBorderPanel);
		gl_infoBorderPanel.setHorizontalGroup(
			gl_infoBorderPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_infoBorderPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_infoBorderPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoBorderPanel.createSequentialGroup()
							.addComponent(btnMarkDone)
							.addGap(6)
							.addComponent(btnDelete))
						.addComponent(infoPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_infoBorderPanel.setVerticalGroup(
			gl_infoBorderPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_infoBorderPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(infoPanel, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_infoBorderPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnMarkDone)
						.addComponent(btnDelete))
					.addContainerGap())
		);
		
		infoBorderPanel.setLayout(gl_infoBorderPanel);
		
		datePanel = new JPanel();
		datePanel.setBackground(SystemColor.activeCaption);
		datePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		datePanel.setBounds(10, 77, 318, 483);
		frmMain.getContentPane().add(datePanel);
		
		calendarTable = new JTable(modelCalendarTable);
		calendarTable.clearSelection();

		scrollCalendarTable = new JScrollPane(calendarTable);
		calendarPanel = new JPanel(null);
		calendarPanel.setBackground(SystemColor.activeCaption);
        calendarPanel.setBorder(null);
        
        calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background
        
                calendarTable.getTableHeader().setResizingAllowed(false);
                calendarTable.getTableHeader().setReorderingAllowed(false);
                
                        calendarTable.setColumnSelectionAllowed(true);
                        calendarTable.setRowSelectionAllowed(true);
                        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        
                                calendarTable.setRowHeight(29);
                                
                                buttonPanel = new JPanel();
                                buttonPanel.setBackground(SystemColor.activeCaption);
                                
                                yearPanel = new JPanel();
                                yearPanel.setBackground(SystemColor.activeCaption);
                                GroupLayout gl_calendarPanel = new GroupLayout(calendarPanel);
                                gl_calendarPanel.setHorizontalGroup(
                                	gl_calendarPanel.createParallelGroup(Alignment.TRAILING)
                                		.addGroup(gl_calendarPanel.createSequentialGroup()
                                			.addContainerGap()
                                			.addGroup(gl_calendarPanel.createParallelGroup(Alignment.TRAILING)
                                				.addComponent(scrollCalendarTable, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                                				.addGroup(gl_calendarPanel.createSequentialGroup()
                                					.addComponent(yearPanel, GroupLayout.PREFERRED_SIZE, 143, Short.MAX_VALUE)
                                					.addPreferredGap(ComponentPlacement.RELATED)
                                					.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)))
                                			.addGap(21))
                                );
                                gl_calendarPanel.setVerticalGroup(
                                	gl_calendarPanel.createParallelGroup(Alignment.LEADING)
                                		.addGroup(gl_calendarPanel.createSequentialGroup()
                                			.addContainerGap()
                                			.addGroup(gl_calendarPanel.createParallelGroup(Alignment.LEADING, false)
                                				.addComponent(buttonPanel, 0, 0, Short.MAX_VALUE)
                                				.addComponent(yearPanel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
                                			.addPreferredGap(ComponentPlacement.UNRELATED)
                                			.addComponent(scrollCalendarTable, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
                                			.addContainerGap(59, Short.MAX_VALUE))
                                );
                                cmbYear = new JComboBox();
                                cmbYear.setBackground(SystemColor.activeCaption);
                                
                                JPanel monthPanel = new JPanel();
                                monthPanel.setBackground(SystemColor.activeCaption);
                                
                                        monthLabel = new JLabel ("January");
                                        monthLabel.setBackground(SystemColor.activeCaption);
                                        GroupLayout gl_monthPanel = new GroupLayout(monthPanel);
                                        gl_monthPanel.setHorizontalGroup(
                                            gl_monthPanel.createParallelGroup(Alignment.LEADING)
                                                .addGroup(gl_monthPanel.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(monthLabel, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                                        );
                                        gl_monthPanel.setVerticalGroup(
                                            gl_monthPanel.createParallelGroup(Alignment.LEADING)
                                                .addGroup(gl_monthPanel.createSequentialGroup()
                                                    .addComponent(monthLabel)
                                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        );
                                        monthPanel.setLayout(gl_monthPanel);
                                        GroupLayout gl_yearPanel = new GroupLayout(yearPanel);
                                        gl_yearPanel.setHorizontalGroup(
                                            gl_yearPanel.createParallelGroup(Alignment.LEADING)
                                                .addGroup(gl_yearPanel.createSequentialGroup()
                                                    .addComponent(monthPanel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(ComponentPlacement.RELATED)
                                                    .addComponent(cmbYear, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                                                    .addContainerGap(32, Short.MAX_VALUE))
                                        );
                                        gl_yearPanel.setVerticalGroup(
                                            gl_yearPanel.createParallelGroup(Alignment.LEADING)
                                                .addGroup(gl_yearPanel.createSequentialGroup()
                                                    .addGroup(gl_yearPanel.createParallelGroup(Alignment.TRAILING, false)
                                                        .addComponent(monthPanel, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                                        .addComponent(cmbYear, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 17, Short.MAX_VALUE))
                                                    .addContainerGap())
                                        );
                                        yearPanel.setLayout(gl_yearPanel);
                                        cmbYear.addActionListener(new cmbYear_Action());
                                        btnPrev = new JButton ("<");
                                        btnPrev.setBackground(new Color(255, 255, 255));
                                        btnPrev.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
                                        
                                        btnPrev.addActionListener(new btnPrev_Action());
                                        btnNext = new JButton (">");
                                        btnNext.setBackground(Color.WHITE);
                                        btnNext.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
                                        GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
                                        gl_buttonPanel.setHorizontalGroup(
                                        	gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
                                        		.addGroup(gl_buttonPanel.createSequentialGroup()
                                        			.addContainerGap()
                                        			.addComponent(btnPrev, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        			.addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                                        			.addGap(19))
                                        );
                                        gl_buttonPanel.setVerticalGroup(
                                        	gl_buttonPanel.createParallelGroup(Alignment.LEADING)
                                        		.addGroup(gl_buttonPanel.createSequentialGroup()
                                        			.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
                                        				.addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                        				.addComponent(btnPrev, GroupLayout.PREFERRED_SIZE, 15, Short.MAX_VALUE))
                                        			.addContainerGap())
                                        );
                                        buttonPanel.setLayout(gl_buttonPanel);
                                        btnNext.addActionListener(new btnNext_Action());
                                        calendarPanel.setLayout(gl_calendarPanel);
		
		filterPanel = new JPanel();
		filterPanel.setBackground(SystemColor.activeCaption);
		filterPanel.setBorder(null);
		
		JLabel lblView = new JLabel("View");
		lblView.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		eventFilter = new JCheckBox("Event");
		eventFilter.setBackground(SystemColor.activeCaption);
		eventFilter.setSelected(true);
		
				eventFilter.setIconTextGap(10);
				
				taskFilter = new JCheckBox("Task");
				taskFilter.setBackground(SystemColor.activeCaption);
				taskFilter.setSelected(true);
				
						taskFilter.setIconTextGap(10);
						GroupLayout gl_filterPanel = new GroupLayout(filterPanel);
						gl_filterPanel.setHorizontalGroup(
							gl_filterPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_filterPanel.createSequentialGroup()
									.addGap(20)
									.addGroup(gl_filterPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_filterPanel.createSequentialGroup()
											.addComponent(eventFilter)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(taskFilter))
										.addComponent(lblView))
									.addContainerGap(153, Short.MAX_VALUE))
						);
						gl_filterPanel.setVerticalGroup(
							gl_filterPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_filterPanel.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblView)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_filterPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(eventFilter)
										.addComponent(taskFilter))
									.addGap(34))
						);
						filterPanel.setLayout(gl_filterPanel);
		
		JButton btnCreate = new JButton("CREATE");
		btnCreate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showEventAdder();
			}
		});
		btnCreate.setForeground(SystemColor.textHighlight);
		btnCreate.setBackground(SystemColor.textHighlight);
		GroupLayout gl_datePanel = new GroupLayout(datePanel);
		gl_datePanel.setHorizontalGroup(
			gl_datePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_datePanel.createSequentialGroup()
					.addGroup(gl_datePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_datePanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(filterPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_datePanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_datePanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_datePanel.createSequentialGroup()
									.addGap(10)
									.addComponent(btnCreate, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE))
								.addComponent(calendarPanel, GroupLayout.PREFERRED_SIZE, 301, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_datePanel.setVerticalGroup(
			gl_datePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_datePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(filterPanel, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(calendarPanel, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCreate, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		datePanel.setLayout(gl_datePanel);
		
		modelCalendarTable.setColumnCount(7);
		modelCalendarTable.setRowCount(6);
		
	}
	
	private void disableSelectButtons() {
		btnMarkDone.setEnabled(false);
		btnDelete.setEnabled(false);
	}

	public void enableSelectButtons() {
		btnMarkDone.setEnabled(true);
		btnDelete.setEnabled(true);
	}	
	
	public void attachDayView(DayView dv) {
       	this.dv = dv;
	}

	public void attachWeekView(WeekView wv) {
       	this.wv = wv;
	}	
	
	public void attachAgendaView(AgendaView av) {
       	this.av = av;
	}	
	
	class btnPrev_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (monthToday == 0)
                        {
				monthToday = 11;
				yearToday -= 1;
			}
			else
                        {
				monthToday -= 1;
			}				
			refreshCalendar();
			cc.refreshCalendarDays();
			refreshCalendar();
		}
	}
	class btnNext_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (monthToday == 11)
                        {
				monthToday = 0;
				yearToday += 1;
			}
			else
                        {
				monthToday += 1;
			}				
			refreshCalendar();
			cc.refreshCalendarDays();
			refreshCalendar();
		}
	}
	class cmbYear_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (cmbYear.getSelectedItem() != null)
                        {
				String b = cmbYear.getSelectedItem().toString();
				yearToday = Integer.parseInt(b);
				refreshCalendar();
				cc.refreshCalendarDays();
				refreshCalendar();
			}
		}
	}
}
