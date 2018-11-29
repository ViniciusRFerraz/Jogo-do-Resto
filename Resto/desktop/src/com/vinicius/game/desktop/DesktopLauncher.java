package com.vinicius.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vinicius.game.Resto;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Resto.WIDTH;
		config.height = Resto.HEIGHT;
		config.title = Resto.TITLE;

		new LwjglApplication(new Resto(), config);
	}
}
