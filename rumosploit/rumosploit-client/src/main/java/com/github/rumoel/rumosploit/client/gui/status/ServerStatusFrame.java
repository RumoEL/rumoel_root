package com.github.rumoel.rumosploit.client.gui.status;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import lombok.Getter;

public class ServerStatusFrame extends InternalFrameApi {
	public ServerStatusFrame() {
		super();
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle("ServerStatus");
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setSize(387, 267);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JLabel lblBotsConnected = new JLabel("bots connected");
		springLayout.putConstraint(SpringLayout.NORTH, lblBotsConnected, 32, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblBotsConnected, 24, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblBotsConnected);

		tfBotsConnected = new JTextField();
		tfBotsConnected.setEditable(false);
		tfBotsConnected.setText("0..Integer.max");
		springLayout.putConstraint(SpringLayout.WEST, tfBotsConnected, 19, SpringLayout.EAST, lblBotsConnected);
		springLayout.putConstraint(SpringLayout.SOUTH, tfBotsConnected, 0, SpringLayout.SOUTH, lblBotsConnected);
		getContentPane().add(tfBotsConnected);
		tfBotsConnected.setColumns(10);

		JLabel lblClientsConnected_1 = new JLabel("clients connected");
		springLayout.putConstraint(SpringLayout.WEST, lblClientsConnected_1, 0, SpringLayout.WEST, lblBotsConnected);
		getContentPane().add(lblClientsConnected_1);

		tfCliConnected = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, lblClientsConnected_1, 2, SpringLayout.NORTH, tfCliConnected);
		springLayout.putConstraint(SpringLayout.NORTH, tfCliConnected, 14, SpringLayout.SOUTH, lblBotsConnected);
		springLayout.putConstraint(SpringLayout.EAST, tfCliConnected, 0, SpringLayout.EAST, tfBotsConnected);
		tfCliConnected.setText("0..Integer.max");
		tfCliConnected.setEditable(false);
		tfCliConnected.setColumns(10);
		getContentPane().add(tfCliConnected);
	}

	private static final long serialVersionUID = -5619236607835983237L;
	@Getter
	private JTextField tfBotsConnected;
	@Getter
	private JTextField tfCliConnected;

}
