package com.inloadings.liteWarpGUI.util.commandImpl.Super;

import com.inloadings.liteWarpGUI.LiteWarpGUI;
import com.inloadings.liteWarpGUI.util.commandImpl.sub.AbstractSubCommand;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractSuperCommand extends Command {
    private final List<AbstractSubCommand> subCommands;
    ICommandInfo commandInfo = getClass().getAnnotation(ICommandInfo.class);
    String[] usage = commandInfo.usage();
    @Getter
    LiteWarpGUI plugin;

    protected AbstractSuperCommand(@NotNull String name, LiteWarpGUI plugin) {
        super(name);
        this.plugin = plugin;
        this.setName(commandInfo.name());
        this.setDescription(commandInfo.description());
        this.setAliases(Arrays.asList(commandInfo.aliases()));
        this.setPermission(commandInfo.perm());
        this.subCommands = initializeSubCommands();
    }

    protected abstract List<AbstractSubCommand> initializeSubCommands();

    @Override
    public @Nullable String getPermissionMessage() {
        return commandInfo.permMessage();
    }

    public void overrideUsageMessage(List<String> usageMessage) {
        usage = usageMessage.toArray(new String[0]);
    }

    public String[] getUsageMessage() {
        return usage;
    }

    public void executeSubCommand(CommandSender sender, String[] args) {
        String inputtedSubCommand = args[0];
        for (AbstractSubCommand subCommand : subCommands) {
            if (Arrays.asList(subCommand.getSubCommandNames()).contains(inputtedSubCommand)) {
                subCommand.attemptCommand(sender, this, args);
                return;
            }
        }
        sender.sendMessage("Unknown subcommand: " + inputtedSubCommand);
        sender.sendMessage(getUsage());
    }

    public boolean attemptCommand(@NotNull CommandSender sender, @NotNull String string, @NotNull String @NotNull [] args) {
        if (sender.hasPermission(Objects.requireNonNull(getPermission()))) return execute(sender, string, args);
        else sender.sendMessage(Objects.requireNonNull(getPermissionMessage()));
        return true;
    }

    @Override
    public abstract boolean execute(@NotNull CommandSender sender, @NotNull String string, @NotNull String @NotNull [] args);


}
