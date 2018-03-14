
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

public class EventAdderView extends JPanel{
	
	private CalendarControl cc;
	
	private JTextField info;
	private JTextField durationFrom;
	private JTextField durationTo;
	private JRadioButton rdbtnEvent;
	private JRadioButton rdbtnTask;
	private JComboBox<?> smonth;
	private JComboBox<?> sday;
	private JComboBox<?> syear;
	private JComboBox<?> emonth;
	private JComboBox<?> eday;
	private JComboBox<?> eyear;

	public EventAdderView(CalendarControl cc) {
		setBackground(SystemColor.activeCaption);
		this.cc = cc;
		setBorder(new LineBorder(new Color(0, 0, 0)));
		initialize();
		clearInputs();
	}
	
	public void clearInputs() {
		info.setText(null);
		durationFrom.setText(null);
		durationTo.setText(null);
	}
	
	private void initialize() {
		setBounds(100, 100, 449, 192);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc.addOccasion(info.getText(), smonth.getSelectedItem().toString(), sday.getSelectedItem().toString(), syear.getSelectedItem().toString(), emonth.getSelectedItem().toString(), eday.getSelectedItem().toString(), eyear.getSelectedItem().toString(), durationFrom.getText(), durationTo.getText(), rdbtnEvent.isSelected(), rdbtnTask.isSelected());
			}
		});
		
		JButton btnDiscard = new JButton("Cancel");
		btnDiscard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(SystemColor.activeCaption);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(inputPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSave)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDiscard)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDiscard, Alignment.TRAILING)
						.addComponent(btnSave, Alignment.TRAILING))
					.addContainerGap())
		);
		
		JLabel lblInfo = new JLabel("Info:");
		
		JLabel lblDate = new JLabel("MM/DD/YYYY:");
		
		JLabel lblTime = new JLabel("Time:");
		
		info = new JTextField();
		info.setColumns(10);
		
		durationFrom = new JTextField();
		durationFrom.setColumns(10);
		
		JLabel lblTo = new JLabel("To");
		
		durationTo = new JTextField();
		durationTo.setColumns(10);
		
		ButtonGroup type = new ButtonGroup();
		
		rdbtnEvent = new JRadioButton("Event");
		rdbtnEvent.setBackground(SystemColor.activeCaption);
		rdbtnTask = new JRadioButton("Task");
		rdbtnTask.setBackground(SystemColor.activeCaption);
		
		type.add(rdbtnEvent);
		type.add(rdbtnTask);

		rdbtnEvent.setSelected(true);
		
		JComboBox<?> smonth = new JComboBox<Object>();
		smonth.setMaximumRowCount(12);
		
		JComboBox<?> sday = new JComboBox<Object>();
		sday.setMaximumRowCount(31);
		
		JComboBox<?> syear = new JComboBox<Object>();
		syear.setMaximumRowCount(9999);
		
		JLabel lblNewLabel = new JLabel("To");
		
		JComboBox<?> emonth = new JComboBox<Object>();
		emonth.setMaximumRowCount(12);
		
		JComboBox<?> eday = new JComboBox<Object>();
		eday.setMaximumRowCount(31);
		
		JComboBox<?> eyear = new JComboBox<Object>();
		eyear.setMaximumRowCount(9999);
		
		GroupLayout gl_inputPanel = new GroupLayout(inputPanel);
		gl_inputPanel.setHorizontalGroup(
			gl_inputPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_inputPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_inputPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_inputPanel.createSequentialGroup()
							.addComponent(lblDate)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(smonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(sday, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(syear, 0, 57, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(emonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(eday, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(eyear, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addGap(24))
						.addGroup(gl_inputPanel.createSequentialGroup()
							.addGroup(gl_inputPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_inputPanel.createSequentialGroup()
									.addComponent(lblInfo)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(info, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_inputPanel.createSequentialGroup()
									.addGroup(gl_inputPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_inputPanel.createSequentialGroup()
											.addComponent(lblTime)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(durationFrom, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_inputPanel.createSequentialGroup()
											.addGap(25)
											.addComponent(rdbtnEvent)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblTo)
									.addGap(12)
									.addGroup(gl_inputPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_inputPanel.createSequentialGroup()
											.addGap(10)
											.addComponent(rdbtnTask))
										.addComponent(durationTo, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))))
							.addContainerGap(180, Short.MAX_VALUE))))
		);
		gl_inputPanel.setVerticalGroup(
			gl_inputPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_inputPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_inputPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInfo)
						.addComponent(info, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_inputPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDate)
						.addComponent(smonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(sday, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(syear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(eyear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(eday, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(emonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_inputPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTime)
						.addComponent(durationFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(durationTo, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTo))
					.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
					.addGroup(gl_inputPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnTask)
						.addComponent(rdbtnEvent)))
		);
		inputPanel.setLayout(gl_inputPanel);
		this.setLayout(groupLayout);
		setVisible(true);
	}
}
