package com.github.rumoel.rumosploit.configurator.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import lombok.Getter;

public class Window extends JFrame {
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO save configs
			}
		});
		setBounds(new Rectangle(1000, 700));
		getContentPane().setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel panelRoot = new JPanel();
		scrollPane.setViewportView(panelRoot);
		GridBagLayout gbl_panelRoot = new GridBagLayout();
		gbl_panelRoot.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelRoot.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelRoot.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelRoot.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelRoot.setLayout(gbl_panelRoot);

		JLabel lblBot = new JLabel("sploitBOT");
		GridBagConstraints gbc_lblBot = new GridBagConstraints();
		gbc_lblBot.insets = new Insets(0, 0, 5, 5);
		gbc_lblBot.gridx = 1;
		gbc_lblBot.gridy = 0;
		panelRoot.add(lblBot, gbc_lblBot);

		JLabel lblNewLabel = new JLabel("sploitServer");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 0;
		panelRoot.add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("sploitClient");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 0;
		panelRoot.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JLabel lblPingDelay = new JLabel("PingDelay");
		lblPingDelay.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblPingDelay = new GridBagConstraints();
		gbc_lblPingDelay.anchor = GridBagConstraints.EAST;
		gbc_lblPingDelay.insets = new Insets(0, 0, 5, 5);
		gbc_lblPingDelay.gridx = 0;
		gbc_lblPingDelay.gridy = 1;
		panelRoot.add(lblPingDelay, gbc_lblPingDelay);

		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 1;
		panelRoot.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);

		JLabel lblSploitBotHost = new JLabel("SploitHost");
		lblSploitBotHost.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblSploitBotHost = new GridBagConstraints();
		gbc_lblSploitBotHost.anchor = GridBagConstraints.EAST;
		gbc_lblSploitBotHost.insets = new Insets(0, 0, 5, 5);
		gbc_lblSploitBotHost.gridx = 0;
		gbc_lblSploitBotHost.gridy = 2;
		panelRoot.add(lblSploitBotHost, gbc_lblSploitBotHost);

		tf_sploitHost_sb = new JTextField();
		GridBagConstraints gbc_tf_sploitHost_sb = new GridBagConstraints();
		gbc_tf_sploitHost_sb.insets = new Insets(0, 0, 5, 5);
		gbc_tf_sploitHost_sb.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_sploitHost_sb.gridx = 1;
		gbc_tf_sploitHost_sb.gridy = 2;
		panelRoot.add(tf_sploitHost_sb, gbc_tf_sploitHost_sb);
		tf_sploitHost_sb.setColumns(10);

		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 2;
		panelRoot.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);

		JLabel lblSploitclientport = new JLabel("SploitBotPort");
		GridBagConstraints gbc_lblSploitclientport = new GridBagConstraints();
		gbc_lblSploitclientport.insets = new Insets(0, 0, 5, 5);
		gbc_lblSploitclientport.anchor = GridBagConstraints.EAST;
		gbc_lblSploitclientport.gridx = 0;
		gbc_lblSploitclientport.gridy = 3;
		panelRoot.add(lblSploitclientport, gbc_lblSploitclientport);

		tf_sploitPort_sb = new JTextField();
		GridBagConstraints gbc_tf_sploitPort_sb = new GridBagConstraints();
		gbc_tf_sploitPort_sb.insets = new Insets(0, 0, 5, 5);
		gbc_tf_sploitPort_sb.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_sploitPort_sb.gridx = 1;
		gbc_tf_sploitPort_sb.gridy = 3;
		panelRoot.add(tf_sploitPort_sb, gbc_tf_sploitPort_sb);
		tf_sploitPort_sb.setColumns(10);

		textField_6 = new JTextField();
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 5, 5);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 2;
		gbc_textField_6.gridy = 3;
		panelRoot.add(textField_6, gbc_textField_6);
		textField_6.setColumns(10);

		JLabel lblSploitport = new JLabel("SploitClientPort");
		lblSploitport.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblSploitport = new GridBagConstraints();
		gbc_lblSploitport.anchor = GridBagConstraints.EAST;
		gbc_lblSploitport.insets = new Insets(0, 0, 5, 5);
		gbc_lblSploitport.gridx = 0;
		gbc_lblSploitport.gridy = 4;
		panelRoot.add(lblSploitport, gbc_lblSploitport);

		textField_7 = new JTextField();
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 5);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 2;
		gbc_textField_7.gridy = 4;
		panelRoot.add(textField_7, gbc_textField_7);
		textField_7.setColumns(10);

		textField_8 = new JTextField();
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.insets = new Insets(0, 0, 5, 0);
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.gridx = 3;
		gbc_textField_8.gridy = 4;
		panelRoot.add(textField_8, gbc_textField_8);
		textField_8.setColumns(10);

		JLabel lblSploittimeout = new JLabel("sploitTimeOut");
		lblSploittimeout.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblSploittimeout = new GridBagConstraints();
		gbc_lblSploittimeout.anchor = GridBagConstraints.EAST;
		gbc_lblSploittimeout.insets = new Insets(0, 0, 0, 5);
		gbc_lblSploittimeout.gridx = 0;
		gbc_lblSploittimeout.gridy = 5;
		panelRoot.add(lblSploittimeout, gbc_lblSploittimeout);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 5;
		panelRoot.add(textField, gbc_textField);
		textField.setColumns(10);

		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 0, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 5;
		panelRoot.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 5;
		panelRoot.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);

		JPanel panelBtn = new JPanel();
		getContentPane().add(panelBtn, BorderLayout.SOUTH);
		panelBtn.setLayout(new BorderLayout(0, 0));

		JButton btnSaveAllConfigs = new JButton("SaveConfigs");
		panelBtn.add(btnSaveAllConfigs, BorderLayout.CENTER);
	}

	private static final long serialVersionUID = 6841764999078468895L;
	private JTextField textField_1;
	@Getter
	private JTextField tf_sploitHost_sb;
	@Getter
	private JTextField tf_sploitPort_sb;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
}
