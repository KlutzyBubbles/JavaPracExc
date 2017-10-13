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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import classes.Appointment;
import classes.AppointmentFactory;
import program.MeetingCalendar;

/**
 * File: SearchAppointments.java
 * Date: 08/09/2017
 * Notes: N/A
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class SearchAppointments extends JFrame implements ActionListener, DocumentListener, ListSelectionListener {

	/**
	 * Dimensions for the frame and layout calculations
	 * Border is the margin between the edge of the frame and the components
	 */
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 500;
	private static final int BORDER = 5;

	/**
	 * Button Strings used to identify which button is clicked
	 */
	public static final String DELETE = "delete";
	public static final String BACK = "back";

	/**
	 * Height of the frame above and below the container used for layout calculation
	 */
	private final int frameAdjust;

	/**
	 * Text Field defined for entering search terms
	 */
	private JTextField search;

	/**
	 * The JScrollPane container that the JTable will be added to
	 */
	private JScrollPane data;

	/**
	 * The table of Appointments loaded from AppointmentFactory
	 */
	private JTable table;

	/**
	 * The delete button object variable to allow updateButtons() to change its state
	 */
	private JButton delete;

	/**
	 * Factory Object used to create, list, load, save and delete Appointments
	 */
	private final AppointmentFactory factory;

	/**
	 * MeetingCalendar SearchAppointments constructor to initialize the SearchAppointments frame
	 * and display data loaded by the provided AppointmentFactory
	 *
	 * @param factory - AppointmentFactory object to be used for loading and displaying data
	 */
	public SearchAppointments(AppointmentFactory factory) {
		super();
		super.pack();
		this.frameAdjust = super.getInsets().top + super.getInsets().bottom;
		super.setBounds(450, 300, SearchAppointments.FRAME_WIDTH, SearchAppointments.FRAME_HEIGHT);
		super.setResizable(false);
		this.factory = factory;
		super.setTitle("Appointments for " + (this.factory.getMonth() + 1) + "/" + this.factory.getYear());
		super.addWindowListener(new MeetingCalendar.CloseHandler());
		super.setLayout(new FlowLayout());
		this.initComponents();
		super.setVisible(true);
	}

	/**
	 * Default SearchAppointments constructor to initialize the SearchAppointments frame and
	 * display data loaded by the default AppointmentFactory Object
	 */
	public SearchAppointments() {
		this(new AppointmentFactory());
	}

	/**
	 * Initializes all components inside the frame, resets search and sort
	 */
	private void initComponents() {
		this.delete = new JButton("Delete Selected");
		this.delete.setPreferredSize(new Dimension((SearchAppointments.FRAME_WIDTH / 2) - (SearchAppointments.BORDER * 2),
												   this.delete.getPreferredSize().height));
		this.delete.setMnemonic(KeyEvent.VK_D);
		this.delete.setName(SearchAppointments.DELETE);
		this.delete.addActionListener(this);
		this.delete.setEnabled(false);
		JButton back = new JButton("Back");
		back.setPreferredSize(new Dimension((SearchAppointments.FRAME_WIDTH / 2) - (SearchAppointments.BORDER * 2),
											back.getPreferredSize().height));
		back.setMnemonic(KeyEvent.VK_B);
		back.setName(SearchAppointments.BACK);
		back.addActionListener(this);
		this.search = new JTextField();
		this.search.setPreferredSize(new Dimension(SearchAppointments.FRAME_WIDTH - (SearchAppointments.BORDER * 2),
												   this.search.getPreferredSize().height));
		this.search.getDocument().addDocumentListener(this);
		JLabel lblSearch = new JLabel("Search For Name");
		lblSearch.setPreferredSize(new Dimension(SearchAppointments.FRAME_WIDTH - (SearchAppointments.BORDER * 2), 25));
		this.redrawData(this.factory.getAppointments());
		int tableHeight = SearchAppointments.FRAME_HEIGHT - back.getPreferredSize().height
						  - this.search.getPreferredSize().height
						  - 25 - this.frameAdjust - this.table.getTableHeader().getPreferredSize().height;
		this.data.setPreferredSize(new Dimension(SearchAppointments.FRAME_WIDTH - (SearchAppointments.BORDER * 2), tableHeight));
		super.add(lblSearch);
		super.add(this.search);
		super.add(this.data);
		super.add(back);
		super.add(this.delete);
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
		if (b.getName().equals(SearchAppointments.BACK))
			MeetingCalendar.openMenu();
		else if (b.getName().equals(SearchAppointments.DELETE)) {
			Component c = this.data.getViewport().getComponent(0);
			if (c instanceof JTable) {
				JTable t = (JTable) c;
				if (t.getSelectedRowCount() > 0) {
					int amount = t.getSelectedRowCount();
					for (int i : t.getSelectedRows())
						this.factory.delete(t.getValueAt(i, 0).toString());
					JOptionPane.showMessageDialog(null, "Deleted " + amount + " Appointment(s) :)", "Delete Appointments", JOptionPane.INFORMATION_MESSAGE);
					this.redrawData(this.factory.getAppointments());
				} else
					JOptionPane.showMessageDialog(null, "You need to select appointments before deleting them", "Delete Appointments", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Invoked when an object is inserted into a document, in this case a JTextField's document
	 *
	 * @param e - The DocumentEvent associated with the JTextField insert
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		this.search(this.search.getText());
	}

	/**
	 * Invoked when an object is removed from a document, in this case a JTextField's document
	 *
	 * @param e - The DocumentEvent associated with the JTextField remove
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		this.search(this.search.getText());
	}

	/**
	 * Invoked when an object is changed in a document, in this case a JTextField's document
	 *
	 * @param e - The DocumentEvent associated with the JTextField change
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		this.search(this.search.getText());
	}

	/**
	 * Invoked when rows in a SelectionModel are changed, in this case the JTables SelectionModel
	 *
	 * @param e - The ListSelectionEvent associated with the JTables selection change
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		this.updateButtons();
	}

	/**
	 * Updates the state of buttons
	 */
	public void updateButtons() {
		if (this.table.getSelectedRowCount() == 0)
			this.delete.setEnabled(false);
		else
			this.delete.setEnabled(true);
	}

	/**
	 * Searches the array for names containing the term (case-insensitive) and updates the JTable
	 * to include the filtered data
	 *
	 * @param term - The term to be used for searching
	 */
	public void search(String term) {
		if (term == null || term.equals(""))
			this.redrawData(this.factory.getAppointments());
		else
			this.redrawData(this.factory.search(term, false));
		this.updateButtons();
	}

	/**
	 * Re-initializes and redraws the data supplied, resets sort but is used for dynamically
	 * changing the data displayed based on search term
	 *
	 * @param set - The List of Appointments to be used as the data set
	 */
	private void redrawData(List<Appointment> set) {
		this.table = new JTable(AppointmentFactory.formatData(set), new String[] {"Name", "Day", "Hour"}) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		if (this.data == null)
			this.data = new JScrollPane();
		if (this.data.getViewport().getComponentCount() != 0)
			this.data.getViewport().remove(0);
		this.data.getViewport().add(this.table);
		this.table.getSelectionModel().addListSelectionListener(this);
		this.table.setFillsViewportHeight(true);
		this.table.setDragEnabled(false);
		this.table.setCellEditor(null);
		this.table.setAutoCreateRowSorter(true);
		super.revalidate();
		super.repaint();
	}

}
