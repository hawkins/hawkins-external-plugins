package com.ironvalue;

import com.ironvalue.IronValueOverlay;
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
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Iron Value"
)
public class IronValuePlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private IronValueOverlay overlay;

	@Provides
	IronValueConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(IronValueConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		log.info("Iron Value starting...");
		overlayManager.add(overlay);
		log.info("Iron Value started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Iron Value stopping...");
		overlayManager.remove(overlay);
		log.info("Iron Value stopped!");
	}
}
