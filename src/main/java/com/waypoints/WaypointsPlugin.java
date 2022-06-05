package com.waypoints;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.worldmap.WorldMapPoint;
import net.runelite.client.ui.overlay.worldmap.WorldMapPointManager;

import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class WaypointsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private WaypointsConfig config;

	@Inject
	private WorldMapPointManager worldMapPointManager;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Waypoints starting...");
		WorldPoint worldPoint = new WorldPoint(1765, 3847, 0);
		BufferedImage bufferedImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
		WorldMapPoint worldMapPoint = new WorldMapPoint(worldPoint, bufferedImage);
		worldMapPointManager.add(worldMapPoint);
		log.info("Waypoints started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Waypoints stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	WaypointsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(WaypointsConfig.class);
	}
}
