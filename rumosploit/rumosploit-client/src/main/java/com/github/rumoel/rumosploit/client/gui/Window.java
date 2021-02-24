package com.github.rumoel.rumosploit.client.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.github.rumoel.rumosploit.client.gui.manage.bots.BotManagerFrame;
import com.github.rumoel.rumosploit.client.gui.manage.bots.control.BotControlFrame;
import com.github.rumoel.rumosploit.client.gui.status.ServerStatusFrame;
import com.github.rumoel.rumosploit.client.gui.task.TaskFrame;

import lombok.Getter;

public class Window extends JFrame {

	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(1000, 500);

		jdpDesktop = new JDesktopPane();
		getContentPane().add(jdpDesktop, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		JMenu mnWindows = new JMenu("Windows");
		mnWindows.setMnemonic(KeyEvent.VK_N);
		menuBar.add(mnWindows);

		JMenuItem mntmBotmanager = new JMenuItem("BotManager");
		mntmBotmanager.addActionListener(e -> botManagerFrame.setVisible(!botManagerFrame.isVisible()));
		mnWindows.add(mntmBotmanager);
		JMenuItem mntmServerstatus = new JMenuItem("ServerStatus");
		mntmServerstatus.setMnemonic(KeyEvent.VK_N);
		mntmServerstatus.addActionListener(e -> serverStatusFrame.setVisible(!serverStatusFrame.isVisible()));
		mnWindows.add(mntmServerstatus);
		JMenu mnActions = new JMenu("actions");
		menuBar.add(mnActions);
		JMenuItem mntmExit = new JMenuItem("exit");
		mnActions.add(mntmExit);
		setJMenuBar(menuBar);

		serverStatusFrame = new ServerStatusFrame();
		serverStatusFrame.setLocation(59, 190);
		serverStatusFrame.setVisible(true);
		jdpDesktop.add(serverStatusFrame);

		botControlFrame = new BotControlFrame();
		this.botControlFrame.getTxtCmd().setEditable(true);
		botControlFrame.setLocation(374, 26);
		botControlFrame.setVisible(true);
		jdpDesktop.add(botControlFrame);

		botManagerFrame = new BotManagerFrame();
		this.botManagerFrame.setBounds(135, 107, 463, 454);
		this.jdpDesktop.add(this.botManagerFrame);
		botManagerFrame.setVisible(true);

		taskFrame = new TaskFrame();
		taskFrame.setLocation(100, 100);
		this.jdpDesktop.add(this.taskFrame);
		taskFrame.setVisible(true);
	}

	private static final long serialVersionUID = 8275620618586051674L;
	private JDesktopPane jdpDesktop;
	@Getter
	private ServerStatusFrame serverStatusFrame;
	@Getter
	private BotManagerFrame botManagerFrame;
	@Getter
	private BotControlFrame botControlFrame;

	@Getter
	private TaskFrame taskFrame;
}
