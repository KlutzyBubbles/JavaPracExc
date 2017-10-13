/*
 * The MIT License
 *
 * Copyright 2017 Lee Tzilantonis.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import classes.AppointmentFactory;
import program.MeetingCalendar;

/**
 * File: FrontEnd.java
 * Date: 08/09/2017
 * Notes: There are no Object variables that define the components inside the frame because the
 * components are only referenced during initialization and don't need to be used by external
 * classes or methods
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class FrontEnd extends JFrame implements ActionListener {

	/**
	 * Dimensions for the frame and layout calculation
	 * Border is the margin between the edge of the frame and the components
	 */
	private static final int FRAME_WIDTH = 300;
	private static final int FRAME_HEIGHT = 200;
	private static final int BORDER = 10;

	/**
	 * Button Strings used to identify which button is clicked
	 */
	public static final String HELP = "help";
	public static final String ADD = "add";
	public static final String SEARCH = "search";
	public static final String LIST = "list";

	/**
	 * Text that is displayed to the user when the Help button is clicked
	 */
	private static final String HELP_TEXT = "HELP INFORMATION\n\n"
											+ "The meeting calendar app saves your appointments to file for the current month.\n\n"
											+ "Click \"Add Appointment\" to add the clients name, day and hour of the appointment\n\n"
											+ "Click \"Search Appointments\" to see a list of all the appointments for the month\n\n"
											+ "Additional functionalities include monthly files and sorting and searching or listing appointments\n\n"
											+ "Click column headers to sort by their values when searching appointments";

	/**
	 * MeetingCalendar and Default constructor used to initialize the FrontEnd or Menu GUI
	 */
	public FrontEnd() {
		super();
		super.setBounds(450, 300, FrontEnd.FRAME_WIDTH, FrontEnd.FRAME_HEIGHT);
		super.setResizable(false);
		super.setTitle("Main Menu");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(new FlowLayout());
		this.initComponents();
		super.setVisible(true);
	}

	/**
	 * Initializes all components inside the frame
	 */
	private void initComponents() {
		JLabel title = new JLabel("Meeting Calendar");
		title.setFont(new Font("Arial", Font.BOLD, 30));
		JLabel sub = new JLabel("Enter details for or search for an Appointment");
		sub.setFont(new Font("Arial", Font.PLAIN, 12));
		JButton help = new JButton("Help");
		help.setPreferredSize(new Dimension(FrontEnd.FRAME_WIDTH - (FrontEnd.BORDER * 2),
											help.getPreferredSize().height + 4));
		help.setName(FrontEnd.HELP);
		help.addActionListener(this);
		JButton add = new JButton("Add Appointment");
		add.setPreferredSize(new Dimension(FrontEnd.FRAME_WIDTH - (FrontEnd.BORDER * 2),
										   add.getPreferredSize().height + 4));
		add.setName(FrontEnd.ADD);
		add.addActionListener(this);
		// Half size button widths are defined by
		// (FrontEnd.FRAME_WIDTH - (FrontEnd.BORDER * 2)) / 2 - (FrontEnd.BORDER / 4)
		// As it was the closest to perfect as i could get while still retaining scalability
		JButton search = new JButton("Search (Adv)");
		search.setPreferredSize(new Dimension((FrontEnd.FRAME_WIDTH - (FrontEnd.BORDER * 2)) / 2 - (FrontEnd.BORDER / 4),
											  search.getPreferredSize().height + 4));
		search.setName(FrontEnd.SEARCH);
		search.addActionListener(this);
		JButton list = new JButton("Search (Simple)");
		list.setPreferredSize(new Dimension((FrontEnd.FRAME_WIDTH - (FrontEnd.BORDER * 2)) / 2 - (FrontEnd.BORDER / 4),
											list.getPreferredSize().height + 4));
		list.setName(FrontEnd.LIST);
		list.addActionListener(this);
		super.add(title);
		super.add(sub);
		super.add(help);
		super.add(add);
		super.add(search);
		super.add(list);
	}

	/**
	 * Invoked when an action has occurred, for this class it is only invoked on button actions
	 *
	 * @param e - The ActionEvent associated with the button action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!(e.getSource() instanceof JButton))
			return;
		JButton b = (JButton) e.getSource();
		switch (b.getName()) {
			case FrontEnd.HELP:
				JOptionPane.showMessageDialog(this,
											  FrontEnd.HELP_TEXT,
											  "Help", JOptionPane.INFORMATION_MESSAGE);
				break;
			case FrontEnd.ADD:
				MeetingCalendar.openAdd();
				break;
			case FrontEnd.SEARCH:
				MeetingCalendar.openSearch();
				break;
			case FrontEnd.LIST:
				AppointmentFactory f = new AppointmentFactory();
				if (f.countAppointments() > 0)
					JOptionPane.showMessageDialog(this,
												  "All Appointments for " + (f.getMonth() + 1) + "/" + f.getYear() + "\n\n" + f.toString(),
												  "All Appointments", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(this,
												  "There are no appointments for " + (f.getMonth() + 1) + "/" + f.getYear(),
												  "All Appointments", JOptionPane.WARNING_MESSAGE);
				break;
		}
	}

}
