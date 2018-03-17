
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AgendaView extends JPanel implements ObserverView{
	
	CalendarView cv;
	
	List<Integer> rowAssignment;
	int currentRow;
	
	JScrollPane scrollCalendarTable;    
	
        /**** Calendar Table Components ***/
	public JTable calendarTable;
    public DefaultTableModel modelCalendarTable;
    
    public void deselect() {
    	currentRow = -1;
		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new InfoTableRenderer(calendarTable.getSelectedRow()));    	
    }
    
    public void update(List<Occasion>occasions) {
		
		rowAssignment = new ArrayList<>();
		
		modelCalendarTable.setColumnCount(2);
		modelCalendarTable.setRowCount(occasions.size());
    	
		for(int i = 0 ; i < occasions.size() ; i++) {
			
			rowAssignment.add(occasions.get(i).getID());
			
			if(occasions.get(i) instanceof Event) {
				if(((Event) occasions.get(i)).getIsDone()) {
					modelCalendarTable.setValueAt("<html><s>"+((Event)occasions.get(i)).getDurationFrom().substring(0,16)+" to "+((Event)occasions.get(i)).getDurationTo().substring(0,16)+"</s></html>", i, 0);	
				}else {
					modelCalendarTable.setValueAt("<html><font color=\""+occasions.get(i).getColorString()+"\">"+((Event)occasions.get(i)).getDurationFrom().substring(0,16)+" to "+((Event)occasions.get(i)).getDurationTo().substring(0,16), i, 0);
				}
				
				if(((Event) occasions.get(i)).getIsDone()) {
					modelCalendarTable.setValueAt("<html><s>"+((Event)occasions.get(i)).getInfo()+"</s></html>", i, 1);					
				}else {
					modelCalendarTable.setValueAt("<html><font color=\""+occasions.get(i).getColorString()+"\">"+((Event)occasions.get(i)).getInfo(), i, 1);					
				}
			}else if (occasions.get(i) instanceof Task) {
				if(((Task) occasions.get(i)).getIsDone()) {
					modelCalendarTable.setValueAt("<html><s>"+((Task)occasions.get(i)).getDurationFrom().substring(0,16)+"</s></html>", i, 0);
				}else {
					modelCalendarTable.setValueAt("<html><font color=\""+occasions.get(i).getColorString()+"\">"+((Task)occasions.get(i)).getDurationFrom().substring(0,16), i, 0);
				}
				
				if(((Task) occasions.get(i)).getIsDone()) {
					modelCalendarTable.setValueAt("<html><s>"+((Task)occasions.get(i)).getInfo()+"</s></html>", i, 1);
				}else {
					modelCalendarTable.setValueAt("<html><font color=\""+occasions.get(i).getColorString()+"\">"+((Task)occasions.get(i)).getInfo(), i, 1);	
				}
			}
		}    	

    }
    
    public int getSelectedOccasion() {
    	return rowAssignment.get(currentRow);
    }
    
	public AgendaView(CalendarView cv){
		setBackground(SystemColor.activeCaption);
		
		setBounds(0,0,620,440);
		
		this.cv =  cv;
		
		try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
		catch (Exception e) {}
        
		modelCalendarTable = new DefaultTableModel()
                {
                    public boolean isCellEditable(int rowIndex, int mColIndex)
                    {
                        return false;
                    }
                };      
                
		calendarTable = new JTable(modelCalendarTable);
		calendarTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentRow = (int) calendarTable.getSelectedRow();
				calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new InfoTableRenderer(calendarTable.getSelectedRow()));
				cv.enableSelectButtons();
				calendarTable.clearSelection();
				calendarTable.repaint();
			}
		});
                
		scrollCalendarTable = new JScrollPane(calendarTable);
		scrollCalendarTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.setBorder(null);
		
		calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background

		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		calendarTable.setRowHeight(20);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollCalendarTable, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollCalendarTable, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);
		modelCalendarTable.addColumn("Time");
		modelCalendarTable.addColumn("Event / Task");

		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new InfoTableRenderer(calendarTable.getSelectedRow()));
	}
}
