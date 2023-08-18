/*
 * Copyright (c) 2018, Charlie Waters
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ironvalue;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("ironvalue")
public interface IronValueConfig extends Config
{

	@ConfigSection(
			name = "Gold Prices",
			description = "Item prices when selling for gold",
			position = 0
	)
	String goldPrices = "goldPrices";

	@ConfigItem(
		keyName = "showBloodRuneShopPrice",
		name = "Show Blood Rune Shop Price",
		description = "Show the Blood Rune shop price when selling to Ali Morrisane on a tooltip",
		section = goldPrices
	)
	default boolean showBloodRuneShopPrice()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showDeathRuneShopPrice",
			name = "Show Death Rune Shop Price",
			description = "Show the Death Rune shop price when selling to Ali Morrisane on a tooltip",
			section = goldPrices
	)
	default boolean showDeathRuneShopPrice()
	{
		return true;
	}

	@ConfigSection(
			name = "Commodity Exchanges",
			description = "Item prices when exchanging for other commodities",
			position = 1
	)
	String commodityExchanges = "commodityExchanges";

	@ConfigItem(
			keyName = "showMinnowSharkConversion",
			name = "Show Minnow to Shark Exchange",
			description = "Show the Minnow to Shark exchange price when trading Kylie Minnow on a tooltip",
			section = commodityExchanges
	)
	default boolean showMinnowSharkConversion()
	{
		return true;
	}

	@ConfigItem(
			keyName = "showMarkOfGraceStaminaConversion",
			name = "Show Marks of Grace to Stamina Doses",
			description = "Show the Mark of Grace to Stamina Potion dose exchange rate on a tooltip",
			section = commodityExchanges
	)
	default boolean showMarkOfGraceStaminaConversion()
	{
		return true;
	}

	@ConfigSection(
			name = "Tokkul Prices",
			description = "Item prices and sale settings when selling for Tokkul",
			position = 2
	)
	String tokkulPrices = "tokkulPrices";

	@ConfigItem(
			keyName = "showChaosRuneTokkulPrice",
			name = "Show Chaos Rune to Tokkul Price",
			description = "Show the Chaos Rune to Tokkul shop price when selling to TzHaar-Mej-Roh on a tooltip",
			section = tokkulPrices
	)
	default boolean showChaosRuneTokkulPrice()
	{
		return true;
	}
	@ConfigItem(
			keyName = "showDeathRunePrice",
			name = "Show Death Rune to Tokkul Price",
			description = "Show the Death Rune to Tokkul shop price when selling to TzHaar-Mej-Roh on a tooltip",
			section = tokkulPrices
	)
	default boolean showDeathRuneTokkulPrice()
	{
		return true;
	}
}


