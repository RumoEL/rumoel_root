package com.github.rumoel.rumosploit.client.gui.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.Timer;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

public class TaskFrame extends JInternalFrame {
	Logger logger = LoggerFactory.getLogger(getClass());

	public TaskFrame() {
		setResizable(true);
		setTitle("TaskFrame");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setSize(800, 628);

		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JScrollPane scrollPaneTableTaskPair = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPaneTableTaskPair, 10, SpringLayout.NORTH,
				getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPaneTableTaskPair, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneTableTaskPair, 336, SpringLayout.NORTH,
				getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPaneTableTaskPair, -120, SpringLayout.EAST,
				getContentPane());
		getContentPane().add(scrollPaneTableTaskPair);

		JScrollPane scrollPaneOutput = new JScrollPane();

		springLayout.putConstraint(SpringLayout.NORTH, scrollPaneOutput, 34, SpringLayout.SOUTH,
				scrollPaneTableTaskPair);
		springLayout.putConstraint(SpringLayout.WEST, scrollPaneOutput, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneOutput, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPaneOutput, -120, SpringLayout.EAST, getContentPane());

		// textFieldCellShow = new JTextField();

		// springLayout.putConstraint(SpringLayout.NORTH, textFieldCellShow, 34,
		// SpringLayout.SOUTH, scrollPaneTableTaskPair);
		// springLayout.putConstraint(SpringLayout.WEST, textFieldCellShow, 10,
		// SpringLayout.WEST, scrollPaneOutput);
		// springLayout.putConstraint(SpringLayout.SOUTH, textFieldCellShow, -10,
		// SpringLayout.SOUTH, scrollPaneOutput);
		// springLayout.putConstraint(SpringLayout.EAST, textFieldCellShow, -120,
		// SpringLayout.EAST, scrollPaneOutput);

		this.model = new TasksTableModel();
		this.table = new JTable(model);
		scrollPaneTableTaskPair.setViewportView(this.table);
		getContentPane().add(scrollPaneOutput);

		txtPaneOutput = new JTextPane();
		scrollPaneOutput.setViewportView(txtPaneOutput);

		Timer timer = new Timer(1000, new TimerListener());
		timer.start();
	}

	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (table.getSelectedRowCount() > 0) {

					String data = model.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
					txtPaneOutput.setText(data);
				}
			} catch (Exception e2) {
				logger.warn("timer:txtPaneOutput", e2);
			}
			table.updateUI();
		}
	}

	private static final long serialVersionUID = -5198828567008280296L;
	@Getter
	private JTable table;
	@Getter
	private transient TableModel model;
	@Getter
	private JTextPane txtPaneOutput;
//	@Getter
//	private JTextField textFieldCellShow;
}
