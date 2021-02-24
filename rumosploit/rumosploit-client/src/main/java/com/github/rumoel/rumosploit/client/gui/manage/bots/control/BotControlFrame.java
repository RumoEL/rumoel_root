package com.github.rumoel.rumosploit.client.gui.manage.bots.control;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.github.rumoel.rumosploit.bot.BotEntity;
import com.github.rumoel.rumosploit.client.header.Header;
import com.github.rumoel.rumosploit.client.network.NetworkUtils;
import com.github.rumoel.rumosploit.tasks.BotTask;
import com.github.rumoel.rumosploit.tasks.BotTask.type;

import lombok.Getter;

public class BotControlFrame extends JInternalFrame {

	public BotControlFrame() {
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("Bot control");
		setSize(674, 375);

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		txtId = new JTextField("ID");
		txtId.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, txtId, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtId, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(txtId);
		txtId.setColumns(10);

		//
		txtCmd = new JTextField("ls");
		txtCmd.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, txtCmd, 40, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtCmd, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(txtCmd);
		txtCmd.setColumns(10);

		jbtnCmd = new JButton("run");
		springLayout.putConstraint(SpringLayout.NORTH, jbtnCmd, 40, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, jbtnCmd, 150, SpringLayout.WEST, getContentPane());
		jbtnCmd.addActionListener(e -> {

			int[] selectedRows = Header.getWindow().getBotManagerFrame().getTableBots().getSelectedRows();
			selectedBots.clear();
			for (int i : selectedRows) {
				selectedBots.add(Header.getBotEntities().get(i));
			}
			BotTask commandExecuteTask = new BotTask();
			commandExecuteTask.getBots().addAll(selectedBots);
			commandExecuteTask.setTasktype(type.EXECUTE);
			commandExecuteTask.setInput(txtCmd.getText());
			NetworkUtils.sendToAuthedServers(commandExecuteTask);
		});
		getContentPane().add(jbtnCmd);
	}

	CopyOnWriteArrayList<BotEntity> selectedBots = new CopyOnWriteArrayList<>();

	private static final long serialVersionUID = -8772595806550157923L;
	@Getter
	private JTextField txtId;

	@Getter
	private JTextField txtCmd;
	@Getter
	private JButton jbtnCmd;
}
