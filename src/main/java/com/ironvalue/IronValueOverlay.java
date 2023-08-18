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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.QuantityFormatter;

class IronValueOverlay extends Overlay
{
    private static final int INVENTORY_ITEM_WIDGETID = WidgetInfo.INVENTORY.getPackedId();
    private static final int BANK_INVENTORY_ITEM_WIDGETID = WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER.getPackedId();
    private static final int BANK_ITEM_WIDGETID = WidgetInfo.BANK_ITEM_CONTAINER.getPackedId();
    private static final int EXPLORERS_RING_ITEM_WIDGETID = WidgetInfo.EXPLORERS_RING_ALCH_INVENTORY.getPackedId();
    private static final int SEED_VAULT_ITEM_WIDGETID = WidgetInfo.SEED_VAULT_ITEM_CONTAINER.getPackedId();
    private static final int SEED_VAULT_INVENTORY_ITEM_WIDGETID = WidgetInfo.SEED_VAULT_INVENTORY_ITEMS_CONTAINER.getPackedId();
    private static final int POH_TREASURE_CHEST_INVENTORY_ITEM_WIDGETID = WidgetInfo.POH_TREASURE_CHEST_INVENTORY_CONTAINER.getPackedId();

    private static final int BLOOD_RUNE_GP_SALE_PRICE = 200;
    private static final int DEATH_RUNE_GP_SALE_PRICE = 90;
    private static final int CHAOS_RUNE_TOKKUL_SALE_PRICE = 9;
    private static final int DEATH_RUNE_TOKKUL_SALE_PRICE = 18;
    private static final int MINNOWS_PER_SHARK = 40;

    private static final int STAMINAS_PER_GRACE = 10;
    private static final int GRACE_PER_EXCHANGE = 10;
    private static final int AMYLASE_PER_EXCHANGE = 100;


    private final Client client;
    private final IronValueConfig config;
    private final TooltipManager tooltipManager;
    private final StringBuilder stringBuilder = new StringBuilder();

    @Inject
    ItemManager itemManager;

    @Inject
    IronValueOverlay(Client client, IronValueConfig config, TooltipManager tooltipManager)
    {
        setPosition(OverlayPosition.DYNAMIC);
        this.client = client;
        this.config = config;
        this.tooltipManager = tooltipManager;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (client.isMenuOpen())
        {
            return null;
        }

        final MenuEntry[] menuEntries = client.getMenuEntries();
        final int last = menuEntries.length - 1;

        if (last < 0)
        {
            return null;
        }

        final MenuEntry menuEntry = menuEntries[last];
        final MenuAction action = menuEntry.getType();
        final int widgetId = menuEntry.getParam1();
        final int groupId = WidgetInfo.TO_GROUP(widgetId);

        switch (action)
        {
            case WIDGET_TARGET_ON_WIDGET:
                // Check target widget is the inventory
                if (menuEntry.getWidget().getId() != WidgetInfo.INVENTORY.getId())
                {
                    break;
                }
                // FALLTHROUGH
            case WIDGET_USE_ON_ITEM:
            case CC_OP:
            case ITEM_USE:
            case ITEM_FIRST_OPTION:
            case ITEM_SECOND_OPTION:
            case ITEM_THIRD_OPTION:
            case ITEM_FOURTH_OPTION:
            case ITEM_FIFTH_OPTION:
                addTooltip(menuEntry, groupId);
                break;
            case WIDGET_TARGET:
                // Check that this is the inventory
                if (menuEntry.getWidget().getId() == WidgetInfo.INVENTORY.getId())
                {
                    addTooltip(menuEntry, groupId);
                }
        }

        return null;
    }

    private void addTooltip(MenuEntry menuEntry, int groupId)
    {
        // Item tooltip values
        switch (groupId)
        {
            case WidgetID.INVENTORY_GROUP_ID:
            case WidgetID.POH_TREASURE_CHEST_INVENTORY_GROUP_ID:
            case WidgetID.BANK_GROUP_ID:
            case WidgetID.BANK_INVENTORY_GROUP_ID:
            case WidgetID.SEED_VAULT_GROUP_ID:
            case WidgetID.SEED_VAULT_INVENTORY_GROUP_ID:
                final String text = makeValueTooltip(menuEntry);
                if (text != null)
                {
                    tooltipManager.add(new Tooltip(ColorUtil.prependColorTag(text, new Color(238, 238, 238))));
                }
        }
    }

    private String makeValueTooltip(MenuEntry menuEntry)
    {
        final int widgetId = menuEntry.getParam1();
        ItemContainer container = null;

        // Inventory item
        if (widgetId == INVENTORY_ITEM_WIDGETID ||
                widgetId == BANK_INVENTORY_ITEM_WIDGETID ||
                widgetId == EXPLORERS_RING_ITEM_WIDGETID ||
                widgetId == SEED_VAULT_INVENTORY_ITEM_WIDGETID ||
                widgetId == POH_TREASURE_CHEST_INVENTORY_ITEM_WIDGETID)
        {
            container = client.getItemContainer(InventoryID.INVENTORY);
        }
        // Bank item
        else if (widgetId == BANK_ITEM_WIDGETID)
        {
            container = client.getItemContainer(InventoryID.BANK);
        }
        // Seed vault item
        else if (widgetId == SEED_VAULT_ITEM_WIDGETID)
        {
            container = client.getItemContainer(InventoryID.SEED_VAULT);
        }

        if (container == null)
        {
            return null;
        }

        // Find the item in the container to get stack size
        final int index = menuEntry.getParam0();
        final Item item = container.getItem(index);
        if (item != null)
        {
            return getItemStackValueText(item);
        }

        return null;
    }

    private void addLineToOutput(String line) {
        if (stringBuilder.length() > 0)
        {
            stringBuilder.append("</br>");
        }

        stringBuilder.append(line);
    }

    private String getItemStackValueText(Item item)
    {
        int id = itemManager.canonicalize(item.getId());
        int qty = item.getQuantity();

        stringBuilder.setLength(0);

        if (id == ItemID.BLOOD_RUNE && config.showBloodRuneShopPrice())
        {
            addLineToOutput("Ali: " + QuantityFormatter.quantityToStackSize((long) qty * BLOOD_RUNE_GP_SALE_PRICE) + " gp");
        }
        if (id == ItemID.DEATH_RUNE && config.showDeathRuneShopPrice())
        {
            addLineToOutput("Ali: " + QuantityFormatter.quantityToStackSize((long) qty * DEATH_RUNE_GP_SALE_PRICE) + " gp");
        }
        if (id == ItemID.MINNOW && config.showMinnowSharkConversion())
        {
            addLineToOutput("Kylie: " + QuantityFormatter.quantityToStackSize((long) qty / MINNOWS_PER_SHARK) + " sharks");
        }
        if (id == ItemID.MARK_OF_GRACE && config.showMarkOfGraceStaminaConversion()) {
            long true_amylase = (long) (Math.floor((double) qty / GRACE_PER_EXCHANGE) * AMYLASE_PER_EXCHANGE);
            double approximate_amylase = qty * STAMINAS_PER_GRACE;
            addLineToOutput("Grace: " + QuantityFormatter.quantityToStackSize((long) approximate_amylase) + " (" + QuantityFormatter.quantityToStackSize(true_amylase) + ") stamina doses");
        }
        if (id == ItemID.CHAOS_RUNE && config.showChaosRuneTokkulPrice())
        {
            addLineToOutput("Mej-Roh: " + QuantityFormatter.quantityToStackSize((long) qty * CHAOS_RUNE_TOKKUL_SALE_PRICE) + " tokkul");
        }
        if (id == ItemID.DEATH_RUNE && config.showDeathRuneTokkulPrice())
        {
            addLineToOutput("Mej-Roh: " + QuantityFormatter.quantityToStackSize((long) qty * DEATH_RUNE_TOKKUL_SALE_PRICE) + " tokkul");
        }

        if (stringBuilder.length() > 0)
        {
            final String result = stringBuilder.toString();
            stringBuilder.setLength(0);
            return result;
        }

        return null;
    }
}
