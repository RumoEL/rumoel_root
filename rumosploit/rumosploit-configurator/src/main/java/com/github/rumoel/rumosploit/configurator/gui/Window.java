package com.github.rumoel.rumosploit.configurator.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Window extends JFrame {
	public Window() {
		setResizable(false);
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
		getContentPane().add(scrollPane);

		JPanel panelRoot = new JPanel();
		scrollPane.setViewportView(panelRoot);
		GridBagLayout gbl_panelRoot = new GridBagLayout();
		gbl_panelRoot.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelRoot.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelRoot.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelRoot.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
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
		GridBagConstraints gbc_lblPingDelay = new GridBagConstraints();
		gbc_lblPingDelay.anchor = GridBagConstraints.EAST;
		gbc_lblPingDelay.insets = new Insets(0, 0, 0, 5);
		gbc_lblPingDelay.gridx = 0;
		gbc_lblPingDelay.gridy = 1;
		panelRoot.add(lblPingDelay, gbc_lblPingDelay);

		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 0, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 1;
		panelRoot.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);

		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 1;
		panelRoot.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		panelRoot.add(textField, gbc_textField);
		textField.setColumns(10);
	}

	private static final long serialVersionUID = 6841764999078468895L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
}
