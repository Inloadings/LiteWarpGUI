package com.inloadings.liteWarpGUI.command;

import com.inloadings.liteWarpGUI.LiteWarpGUI;
import com.inloadings.liteWarpGUI.util.commandImpl.Super.AbstractSuperCommand;
import com.inloadings.liteWarpGUI.util.commandImpl.Super.ICommandInfo;
import com.inloadings.liteWarpGUI.util.commandImpl.sub.AbstractSubCommand;
import com.inloadings.liteWarpGUI.warp.WarpMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@ICommandInfo(name = "warp", description = "you got warped obvi.", usage = {"/warp"})
public class WarpCommand extends AbstractSuperCommand {
    public WarpCommand(@NotNull String name, LiteWarpGUI plugin) {
        super(name, plugin);
    }

    @Override
    protected List<AbstractSubCommand> initializeSubCommands() {
        return List.of();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String string, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to execute this command!");
            return true;
        }
        new WarpMenu(getPlugin(), player.getUniqueId()).openInventory();
        return true;
    }
}
