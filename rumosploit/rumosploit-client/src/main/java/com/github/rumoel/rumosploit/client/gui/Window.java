package com.github.rumoel.rumosploit.client.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.github.rumoel.rumosploit.client.gui.manage.bots.BotManagerFrame;
import com.github.rumoel.rumosploit.client.gui.status.ServerStatusFrame;

import lombok.Getter;

public class Window extends JFrame {

	private static final long serialVersionUID = 8275620618586051674L;
	private JDesktopPane jdpDesktop;
	@Getter
	private ServerStatusFrame serverStatusFrame;
	@Getter
	private BotManagerFrame botManagerFrame;

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

		botManagerFrame = new BotManagerFrame();
		botManagerFrame.setLocation(188, 36);
		botManagerFrame.setVisible(true);
		jdpDesktop.add(botManagerFrame);
	}

}
