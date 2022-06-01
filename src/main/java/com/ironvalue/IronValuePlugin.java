package com.ironvalue;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Iron Value"
)
public class IronValuePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private IronValueConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Iron Value started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Iron Value stopped!");
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
	IronValueConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(IronValueConfig.class);
	}
}
