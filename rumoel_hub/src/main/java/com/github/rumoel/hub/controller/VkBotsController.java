package com.github.rumoel.hub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VkBotsController {

	@RequestMapping("/vkBots")
	public String page() {
		return "vkBots";
	}
}
