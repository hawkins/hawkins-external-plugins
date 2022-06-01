package com.ironvalue;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ironvalue")
public interface IronValueConfig extends Config
{
	@ConfigItem(
		keyName = "showBloodRuneShopPrice",
		name = "Show Blood Rune Shop Price",
		description = "Show the Blood Rune shop price when selling to Ali Morrisane on a tooltip"
	)
	default boolean showBloodRuneShopPrice()
	{
		return true;
	}
}


