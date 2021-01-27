package com.github.rumoel.games.space.client.online.game.header;

import java.io.File;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import com.github.rumoel.games.space.client.online.game.configs.ClientConfig;
import com.github.rumoel.games.space.client.online.game.engine.ClientGameEngine;
import com.github.rumoel.games.space.client.online.game.header.control.ControlHeader;
import com.github.rumoel.games.space.client.online.game.header.window.WindowHeader;
import com.github.rumoel.games.space.client.online.game.objects.space.entity.SpaceCamera;

import lombok.Getter;
import lombok.Setter;

public class GameHeader {
	private GameHeader() {
	}

	@Getter
	private static File rootDir = new File("rumoel_games_space_client");
	@Getter
	private static File configFile = new File(getRootDir(), "ClientConfig.yml");
	@Getter
	@Setter
	public static ClientConfig clientConfig = new ClientConfig();

	// ENGINE
	public static ClientGameEngine onlinegame = new ClientGameEngine();

	// ENGINE

	// WINDOW HEADER
	WindowHeader windowHeader = new WindowHeader();
	// WINDOW HEADER

	// CONTROL HEADER
	@Getter
	private static ControlHeader controlHeader = new ControlHeader();
	// CONTROL HEADER

	public static SpaceCamera camera = new SpaceCamera();

	@Getter
	private static boolean[] keyDown = new boolean[GLFW.GLFW_KEY_LAST];

	// УСКОРЕНИЕ ВПЕРЕД/назад
	@Getter
	private static float mainThrusterAccFactor = 50.0F;
	@Getter
	private static float straveThrusterAccFactor = 20.0F;
	@Getter

	// максимальная скорость
	private static float maxLinearVel = 200.0F;

	@Getter
	@Setter
	private static GLFWKeyCallback keyCallback;
	@Getter
	@Setter
	private static GLFWFramebufferSizeCallback fbCallback;

}
