package com.github.rumoel.rumosploit.event.bot;

import com.github.rumoel.rumosploit.bot.BotEntity;
import com.github.rumoel.rumosploit.event.leave.LeaveEvent;

import lombok.Getter;
import lombok.Setter;

public class BotLeaveEvent extends LeaveEvent {

	private static final long serialVersionUID = 2436319306279966974L;
	@Getter
	@Setter
	private BotEntity entity;
}
