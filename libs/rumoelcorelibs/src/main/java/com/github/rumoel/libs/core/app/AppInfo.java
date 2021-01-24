package com.github.rumoel.libs.core.app;

import java.util.UUID;

import lombok.Getter;

public class AppInfo {
	@Getter
	private String appId = UUID.randomUUID().toString();
}