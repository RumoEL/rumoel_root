package com.github.rumoel.rumosploit.client.gui.manage.bots;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.TableModel;

import com.github.rumoel.rumosploit.client.gui.status.InternalFrameApi;

import lombok.Getter;

public class BotManagerFrame extends InternalFrameApi {
	public BotManagerFrame() {

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle("BotManager");
		setSize(674, 375);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		model = new BotsTableModel();
		tableBots = new JTable(model);
		scrollPane.setViewportView(tableBots);

		Timer timer = new Timer(1000, new TimerListener());
		timer.start();
	}

	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			tableBots.updateUI();
		}
	}

	private static final long serialVersionUID = -2148690316316207764L;
	@Getter
	private transient TableModel model;
	@Getter
	private JTable tableBots;

}
