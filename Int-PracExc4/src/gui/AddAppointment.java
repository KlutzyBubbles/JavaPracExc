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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import classes.AppointmentFactory;
import program.MeetingCalendar;
import utils.SpringUtilities;

/**
 * File: AddAppointment.java
 * Date: 08/09/2017
 * Notes: There are no Object variables that define the components inside the frame because the
 * components are only referenced during initialization and don't need to be used by external
 * classes or methods
 *
 * @version 1.0.0
 * @author Lee Tzilantonis
 */
public class AddAppointment extends JFrame implements ActionListener {

	/**
	 * Dimensions for the frame and layout calculation
	 * Border is the margin between the edge of the frame and the components
	 */
	private static final int FRAME_WIDTH = 180;
	private static final int FRAME_HEIGHT = 180;
	private static final int BORDER = 5;

	/**
	 * Button Strings used to identify which button is clicked
	 */
	public static final String CANCEL = "cancel";
	public static final String SUBMIT = "submit";

	/**
	 * inputLabels are the labels to be paired against a JTextField, mainly referenced for length
	 */
	private final String[] inputLabels = {"Name: ", "Day: ", "Hour: "};

	/**
	 * inputs are the JTextField objects corresponding to the inputLabels referenced for their
	 * values
	 */
	private final JTextField[] inputs = new JTextField[this.inputLabels.length];

	/**
	 * Height of the frame above and below the container used for layout calculation
	 */
	private final int frameAdjust;

	/**
	 * Factory Object used to create, list, load, save and delete Appointments
	 */
	private final AppointmentFactory factory;

	/**
	 * MeetingCalendar and Default constructor to initialize the AddAppointment GUI
	 */
	public AddAppointment() {
		super();
		super.pack();
		this.frameAdjust = super.getInsets().top + super.getInsets().bottom;
		this.factory = new AppointmentFactory();
		super.setBounds(450, 300, AddAppointment.FRAME_WIDTH, AddAppointment.FRAME_HEIGHT);
		super.setResizable(false);
		super.setTitle("Add Appointment for " + (this.factory.getMonth() + 1) + "/" + this.factory.getYear());
		super.addWindowListener(new MeetingCalendar.CloseHandler());
		super.setLayout(new FlowLayout());
		this.initComponents();
		super.setVisible(true);
	}

	/**
	 * Initializes all components inside the frame
	 */
	private void initComponents() {
		JButton submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension((AddAppointment.FRAME_WIDTH / 2) - (AddAppointment.BORDER * 2),
											  submit.getPreferredSize().height));
		submit.setMnemonic(KeyEvent.VK_S);
		submit.setName(AddAppointment.SUBMIT);
		submit.addActionListener(this);
		JButton cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension((AddAppointment.FRAME_WIDTH / 2) - (AddAppointment.BORDER * 2),
											  cancel.getPreferredSize().height));
		cancel.setMnemonic(KeyEvent.VK_C);
		cancel.setName(AddAppointment.CANCEL);
		cancel.addActionListener(this);
		JPanel form = new JPanel(new SpringLayout());
		form.setPreferredSize(new Dimension(AddAppointment.FRAME_WIDTH,
											AddAppointment.FRAME_HEIGHT
											- this.frameAdjust
											- AddAppointment.BORDER
											- submit.getPreferredSize().height));
		for (int i = 0; i < this.inputLabels.length; i++) {
			JLabel l = new JLabel(this.inputLabels[i], JLabel.TRAILING);
			form.add(l);
			this.inputs[i] = new JTextField();
			l.setLabelFor(this.inputs[i]);
			form.add(this.inputs[i]);
		}
		SpringUtilities.makeCompactGrid(form, this.inputLabels.length, 2, 10, 0, 5, 5);
		super.add(form);
		super.add(cancel);
		super.add(submit);
		super.getRootPane().setDefaultButton(submit);
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
		if (b.getName().equals(AddAppointment.CANCEL))
			MeetingCalendar.openMenu();
		else if (b.getName().equals(AddAppointment.SUBMIT))
			if (!this.factory.validateName(this.inputs[0].getText()))
				JOptionPane.showMessageDialog(null, "Please enter a valid Name (Unique name that is 1-20 characters)", "Invalid Name", JOptionPane.ERROR_MESSAGE);
			else if (!this.factory.validateDay(this.inputs[1].getText()))
				JOptionPane.showMessageDialog(null, "Please enter a valid Day (Integer between 1 and " + this.factory.getDays() + ")", "Invalid Day", JOptionPane.ERROR_MESSAGE);
			else if (!this.factory.validateHour(this.inputs[2].getText()))
				JOptionPane.showMessageDialog(null, "Please enter a valid Hour (Integer between 1 and 24)", "Invalid Hour", JOptionPane.ERROR_MESSAGE);
			else
				try {
					boolean r = this.factory.addAppointment(this.inputs[0].getText(),
															Integer.parseInt(this.inputs[1].getText()),
															Integer.parseInt(this.inputs[2].getText()));
					if (!r)
						JOptionPane.showMessageDialog(null, "Unknown Error Occured", "Invalid Appointment", JOptionPane.ERROR_MESSAGE);
					else {
						this.resetInputs();
						JOptionPane.showMessageDialog(null, "Successfully added appointment", "Appointment Created", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Unknown Error Occured", "Invalid Appointment", JOptionPane.ERROR_MESSAGE);
				}
	}

	/**
	 * Resets all the inputs in the inputs array to their default value which in this case is "" and
	 * focuses on the first JTextField in the array
	 */
	public void resetInputs() {
		for (JTextField i : this.inputs)
			i.setText("");
		this.inputs[0].grabFocus();
	}

}
