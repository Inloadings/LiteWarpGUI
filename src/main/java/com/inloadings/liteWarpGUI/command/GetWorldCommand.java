package com.inloadings.liteWarpGUI.command;

import com.inloadings.liteWarpGUI.LiteWarpGUI;
import com.inloadings.liteWarpGUI.util.commandImpl.Super.AbstractSuperCommand;
import com.inloadings.liteWarpGUI.util.commandImpl.Super.ICommandInfo;
import com.inloadings.liteWarpGUI.util.commandImpl.sub.AbstractSubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@ICommandInfo(name = "getworld", description = "gets the world", usage = {"you", "suck"})
public class GetWorldCommand extends AbstractSuperCommand {
    public GetWorldCommand(@NotNull String name, LiteWarpGUI plugin) {
        super(name, plugin);
    }

    @Override
    protected List<AbstractSubCommand> initializeSubCommands() {
        return List.of();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String string, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) return true;
        player.sendMessage(player.getWorld().getName());
        return true;
    }
}
