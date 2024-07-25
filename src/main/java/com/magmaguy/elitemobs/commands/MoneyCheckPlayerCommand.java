package com.magmaguy.elitemobs.commands;

import com.magmaguy.magmacore.command.AdvancedCommand;

import java.util.ArrayList;
import java.util.List;

public class MoneyCheckPlayerCommand extends AdvancedCommand {
    public MoneyCheckPlayerCommand() {
        super(List.of("money"));
        addLiteral("check");
        addArgument("player", new ArrayList<>());
        setUsage("/em check <player>");
        setPermission("elitemobs.money.check.player");
        setDescription("Checks the currency of the specified player.");
    }

    @Override
    public void execute() {
        CurrencyCommandsHandler.checkCommand(
                getCurrentCommandSender(),
                getStringArgument("player"));
    }
}