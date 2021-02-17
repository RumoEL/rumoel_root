package com.github.rumoel.rumosploit.client.gui.manage.bots;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.github.rumoel.rumosploit.client.gui.status.InternalFrameApi;

public class BotManagerFrame extends InternalFrameApi {
	public BotManagerFrame() {

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle("BotManager");
		setSize(674, 375);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		TableModel model = new BotsTableModel();
		tableBots = new JTable(model);
		scrollPane.setViewportView(tableBots);
	}

	private static final long serialVersionUID = -2148690316316207764L;
	private JTable tableBots;

}
