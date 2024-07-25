package com.magmaguy.elitemobs.commands;

import com.magmaguy.elitemobs.menus.GetLootMenu;
import com.magmaguy.magmacore.command.AdvancedCommand;
import com.magmaguy.magmacore.command.SenderType;

import java.util.List;

public class LootMenuCommand extends AdvancedCommand {
    public LootMenuCommand() {
        super(List.of("loot"));
        addLiteral("menu");
        setUsage("/em loot menu");
        setPermission("elitemobs.loot.menu");
        setSenderType(SenderType.PLAYER);
        setDescription("Opens the loot menu.");
    }

    @Override
    public void execute() {
        new GetLootMenu(getCurrentPlayerSender());
    }
}